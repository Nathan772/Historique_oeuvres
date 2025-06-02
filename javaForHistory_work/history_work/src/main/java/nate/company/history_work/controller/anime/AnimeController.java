package nate.company.history_work.controller.anime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import nate.company.history_work.service.*;
import nate.company.history_work.siteTools.anime.AnimeShort;
import nate.company.history_work.siteTools.anime.AnimeShortRepository;
import nate.company.history_work.siteTools.dtos.WatchedAnimeDto;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.status.VisualArtStatus;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedAnime.WatchedAnime;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;

import static nate.company.history_work.controller.ControllerResources.FROMJSONCONVERTER;
import static nate.company.history_work.logger.LoggerInfo.LOGGER;
import static nate.company.history_work.service.UserService.USER_CHOSEN;
import static nate.company.history_work.siteTools.json.JsonTools.parseKeyValueString;
import static nate.company.history_work.siteTools.timeHandler.TimeConverter.fromOnlyTimeToSeconds;

/**
 * Acts like a REST controller that manages the requests about movies.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */

@CrossOrigin("*")
@RestController
public class AnimeController {
    /**
     * Repository that stores movies.
     */
    @Autowired
    private final AnimeShortRepository animeRepository;

    @Autowired
    private final UserService userService;

    private final AnimeShortService animeShortService;

    private  final WatchAnimeService watchedAnimeService;

    //private final AnimeReactionService movieReactionService;

    @Autowired
    public AnimeController(AnimeShortRepository animeShortRepository, UserService userService, AnimeShortService animeService,

                           WatchAnimeService watchedAnimeService){
        Objects.requireNonNull(animeShortRepository);
        Objects.requireNonNull(userService);
        this.animeRepository = animeShortRepository;
        this.userService = userService;
        this.watchedAnimeService = watchedAnimeService;
        this.animeShortService = animeService;
        //this.movieReactionService = movieReactionService;
    }

    /**
     *
     * a remove method for an association between a movie and a user.
     *
     *
     * the user who watched the movie
     * @param animeDataUserJson
     * the movie watch with its data.
     *
     * the movie and the user who watches this movie.
     *
     * @return
     * a response that precises if everything end up properly.
     */
    //overpowerful deprecated

    @DeleteMapping("/user/anime/remove")
    public ResponseEntity<String> removeAnimeFromList(@RequestBody String animeDataUserJson) {
        System.out.println("les infos du json en brut sont : "+animeDataUserJson);
        //System.out.println("on a trouvé le user principal");
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal()

        Optional<User> userOpt;
        //get user thanks to back end save of persistent session
        if(USER_CHOSEN.getPseudo() != null && USER_CHOSEN.getPseudo().isEmpty() == false){
            System.out.println(" on récupère les infos du user grâce au back end et pas le front end");
            userOpt = userService.getUserByPseudo(USER_CHOSEN.getPseudo());
        }
        //use the request to retrieve user's information (password + pseudo)
        else {
            //deprecated
            //userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));

            //user doesn't exists
            System.out.println("on envoie le json classique 0 : user not connected");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");

            //deprecated
            //user exists
            //but false password
            /*if (userOpt.get().getPassword().equals(nestedMap.get("password"))) {
                System.out.println("on envoie le json classique 1");
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{}");
            }*/
        }


        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        AnimeShort anime;
        TimeConverter.OnlyTime onlyTimeOfMovie;
        WatchedMovie watchedMovie;

        var nestedMap = parseComplexJsonForWatchedAnime(animeDataUserJson);

        //map of movie data
        HashMap<String,String> fromStringToJsonAnimeMap;

        try {
            //retrieve movie data
            fromStringToJsonAnimeMap = parseKeyValueString(nestedMap.get("anime"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }
        try {
            //var personAsString = fromStringToJsonAnimeMap.get("writers").split(" ");
            LinkedHashSet<Person> writerPersons = new LinkedHashSet<>();
            LinkedHashSet<Person> actorPersons = new LinkedHashSet<>();
            for(var personStr:fromStringToJsonAnimeMap.get("Writer").split(",")){
                var firstNameAndLastName = personStr.split(" ");
                writerPersons.add(new Person(firstNameAndLastName[0], firstNameAndLastName[1]));
            }
            for(var personStr:fromStringToJsonAnimeMap.get("Actors").split(",")){
                var firstNameAndLastName= personStr.split(" ");
                actorPersons.add(new Person(firstNameAndLastName[0], firstNameAndLastName[1]));
            }
            anime = new AnimeShort(fromStringToJsonAnimeMap.get("title"), Integer.parseInt(fromStringToJsonAnimeMap.get("yearOfRelease")), fromStringToJsonAnimeMap.get("imdbID") ,
                    fromStringToJsonAnimeMap.get("poster"), actorPersons,writerPersons);
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            System.out.println("Error : the json received as movie doesn't respect the json format");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }
        System.out.println("l'état du movie récupéré grâce au json et au parsing est :"+anime);
        var animeAlreadyExistsOpt = animeShortService.getAnimeShortByImdb(anime.getImdbID());

        if(animeAlreadyExistsOpt.isPresent()){

            System.out.println("le film a bien été "+anime+" ( found)");
            //remove the watched movie line from database
            var watchedToRemoveOpt = watchedAnimeService.findByUserAndAnime(actualUser, animeAlreadyExistsOpt.get());
            /*
            movie not found, but it's not a problem
             */
            if(watchedToRemoveOpt.isEmpty()){
                System.out.println("le film à supprimer n'a pas été trouvé pour le user "+actualUser+ " "+anime+" (not found)");
                return ResponseEntity.ok("{}");
            }
            /*
            movie found, you remove this
             */
            System.out.println("le watch anime à supprimer est : "+watchedToRemoveOpt.get());
            watchedAnimeService.removeByIdAnime(watchedToRemoveOpt.get().getId());
            //the movie is already in the data base don't need to recreate with the same imdb
            var animeShortChosen = animeAlreadyExistsOpt.get();
            actualUser.removeFromWatchedAnime(watchedToRemoveOpt.get().getAnimeShort());
            animeShortChosen.removeUserFromWatcher(actualUser);


            //update with the removal of the movie in the lists
            animeShortService.saveAnimeShort(animeShortChosen);
            userService.saveUser(actualUser);
            //send the movie as json
            try {
                System.out.println("on envoie le json classique 2");
                var jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedAnimeDto(watchedToRemoveOpt.get()));
                System.out.println("l'état du json produit :"+jsonStr);
                return ResponseEntity.ok(jsonStr);
            } catch (JsonProcessingException e) {
                throw new AssertionError("not proper json format for watch movie : "+e);
            }
        }
        /*
        movie not found, but it's not a problem
         */
        System.out.println("on envoie le json classique 3");
        return ResponseEntity.ok("");
    }







    /**
     *
     * Retrieve a user based on their pseudo or email.
     * @param user the you user you want to retrieve
     * @return the user object.
     */
    //@RequestMapping("/users")
    //@GetMapping("/user")


    /**
     * This method add a reaction to a movie into the data base.
     *
     * @param userMovieReactJson
     * the user that will react to the movie
     * the movie that will receive the as json.
     * the reaction itself.
     *
     * @return the retrieved movie
     *
     */

//    @PostMapping("/user/anime/react")
//    public ResponseEntity<String> reactMovie(@RequestBody String userMovieReactJson){
//        System.out.println("on ajoute le film et le user en react: "+userMovieReactJson);
//        LOGGER.log(Level.INFO, " on ajoute le film: "+userMovieReactJson);
//        //user not connected abnormal
//        var nestedMap = parseComplexJsonForWatchedAnime(userMovieReactJson);
//
//        var mapForWatchedMovie = new HashMap<String,String>();
//        var mapForUserWatcher = new HashMap<String,String>();
//        /*
//        load user's data
//         */
//        /*
//        you need to add quotes in order to parse since
//         quotes are necessary for json recognition
//         */
//        mapForUserWatcher.put("password",nestedMap.get("password"));
//        mapForUserWatcher.put("pseudo", nestedMap.get("pseudo"));
//        /*
//        load movie data
//         */
//        //need to be parsed again
//        nestedMap.get("anime");
//
//        //need to be parsed again
//        System.out.println("le contenu de la reaction nested est : "+nestedMap.get("reactionChoice"));
//
//        //parse time as json object for test
//
//
//        HashMap<String, String> fromStringToJsonMovieMap;
//
//        HashMap<String, String> fromStringToJsonReactMap;
//        /*
//        retrieve movie data into map
//         */
//        try {
//            fromStringToJsonMovieMap = parseKeyValueString(nestedMap.get("movie"));
//        } catch (Exception e) {
//            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
//        }
//        System.out.println("le json du movie en string : "+fromStringToJsonMovieMap);
//
//
//        /*
//        retrieve reaction data into map
//         */
//        try {
//            fromStringToJsonReactMap = parseKeyValueString(nestedMap.get("reactionChoice"));
//        } catch (Exception e) {
//            throw  new IllegalArgumentException("Error : the json received as \"movieStatus\" doesn't respect the json format "+e);
//        }
//
//
//
//
//        //mapForWatchedMovie.put("");
//        //System.out.println("on a trouvé le user principal");
//        //user is connected
//        // doesn't work use another approach
//        //var currentUser = userService.getPrincipal();
//
//        var userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));
//        //user doesn't exists
//        if(userOpt.isEmpty()){
//            System.out.println("erreur le user au pseudo: "+nestedMap.get("pseudo")+" n'existe pas ");
//            ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new Movie());
//        }
//
//        //user exists
//        //but false password
//        if(userOpt.get().getPassword().equals(nestedMap.get("password"))){
//            System.out.println("erreur le user au password: "+nestedMap.get("password")+" n'existe pas ");
//            ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new Movie());
//        }
//
//        //user exists and good password
//
//        //use the actual user
//        var actualUser = userOpt.get();//currentUser.get();
//
//        //check if movie already exists in db
//        AnimeShort anime;
//        try {
//            var personAsString = fromStringToJsonMovieMap.get("director").split(" ");
//            anime = new AnimeShort(fromStringToJsonMovieMap.get("title"), Integer.parseInt(fromStringToJsonMovieMap.get("yearOfRelease")), fromStringToJsonMovieMap.get("imdbID"),
//                    new Person(personAsString[0], personAsString[1]),
//                    fromStringToJsonMovieMap.get("poster"));
//        }
//
//        catch(Exception e){
//            //inconsistent movie fields
//            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
//            System.out.println("erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
//            //EMPTY MOVIE RETURNED IF Not connected
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body("{}");
//        }
//        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());
//
//        System.out.println("avant time converter");
//
//        /*
//        the movie is already registered in data base
//         */
//
//        if(movieAlreadyExistsOpt.isPresent()){
//
//
//            //retrieve and update if it already exists
//            var alreadyReactedMovieOpt =movieReactionService.findByUserAndMovie(userOpt.get(),movieAlreadyExistsOpt.get());
//
//            //the movie is already in the data base don't need to recreate with the same imdb
//            var movieChosen = movieAlreadyExistsOpt.get();
//
//            //Already exists in database, just update data then
//            if(alreadyReactedMovieOpt.isPresent()){
//                System.out.println("le film est déjà dans la reactionList, on a juste mettre à jour son statut : "
//                        +alreadyReactedMovieOpt.get());
//                var alreadyReactedMovie = alreadyReactedMovieOpt.get();
//
//                //update "watched status"
//                alreadyReactedMovie.setReaction(fromStringToReactionStatus(nestedMap.get("reactionChoice")));
//
//                actualUser.reactMovie(alreadyReactedMovie);
//                movieChosen.reactUser(alreadyReactedMovie);
//
//                //makes them persistent in db
//                movieService.saveMovie(movieChosen);
//                //this specific order is compulsory
//                movieReactionService.save(alreadyReactedMovie);
//                userService.saveUser(actualUser);
//
//                // Getting organisation object as a json string
//                String jsonStr;
//                try {
//                    jsonStr = FROMJSONCONVERTER.writeValueAsString(new MovieDto(movieChosen));
//                } catch (JsonProcessingException e) {
//                    throw new AssertionError("not proper json format for watch movie :" +e);
//                }
//
//                return ResponseEntity.
//                        ok(jsonStr);
//
//
//            }
//
//
//
//
//
//
//            //new reaction object instantiate (there was nothing in db)
//
//            var reacted = new MovieReaction(actualUser,fromStringToReactionStatus(nestedMap.get("reactionChoice")),movieAlreadyExistsOpt.get());
//            System.out.println("l'état de watched movie avant la création du dto : "+reacted);
//
//
//
//
//            movieChosen.reactUser(reacted);
//            actualUser.reactMovie(reacted);
//            //makes them persistent in db
//            movieService.saveMovie(movieChosen);
//            //this specific order is compulsory
//            movieReactionService.save(reacted);
//            userService.saveUser(actualUser);
//            System.out.println("l'état de reaction movie avant la création du dto : "+reacted);
//            return ResponseEntity.ok("{}");
//        }
//
//        /*
//        it's a new watched movie that is registered in data base
//         */
//
//        var reacted = new MovieReaction(actualUser,fromStringToReactionStatus(nestedMap.get("reactionChoice")),movie);
//        System.out.println("l'état de watched movie avant la création du dto : "+reacted);
//
//
//
//        //it's a new movie that wasn't in db
//        //we save it in db
//        movie.reactUser(reacted);
//        animeShortService.saveAnimeShort(anime);
//        userService.saveUser(actualUser);
//        movieReactionService.save(reacted);
//        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);
//        System.out.println("juste avant le responseEntity de watchedmovieDto");
//
//        // Getting organisation object as a json string
//        String jsonStr;
//        try {
//            jsonStr = FROMJSONCONVERTER.writeValueAsString(new MovieDto(movie));
//        } catch (JsonProcessingException e) {
//            throw new AssertionError("not proper json format for watch movie :" +e);
//        }
//
//        return ResponseEntity.
//                ok(jsonStr);
//    }
























    /**
     *
     * Retrieve a user based on their pseudo or email.
     * @param user the you user you want to retrieve
     * @return the user object.
     */
    //@RequestMapping("/users")
    //@GetMapping("/user")


    /**
     * The "add movie" add a new movie into the data base.
     *
     * @param userAnimeJson
     * the user that will have the movie in their list as json. and
     * the movie that will be added as json.
     *
     * @return the retrieved movie
     */
    //Requestbody can be used only once,
    //thereby you use a wrapper class or two request param
    //you cannot
    @PostMapping("/user/anime/add")
    public ResponseEntity<String> addAnime(@RequestBody String userAnimeJson){
        System.out.println("on ajoute l'anime et le user dans movie/add: "+userAnimeJson);

        System.out.println("l'actuel user connecté est : "+USER_CHOSEN.getPseudo());

        //regex searched : {({.*})\,({.*})}
        LOGGER.log(Level.INFO, " on ajoute l'animé : "+userAnimeJson);
        //user not connected abnormal
        // doesn't work for now, use another approach
//        if(userService.getPrincipal().isEmpty()){
//            System.out.println("on ne trouve pas le user principal");
//            //EMPTY MOVIE RETURNED IF Not connected
//            return ResponseEntity.ok(new Movie());
//        }
        var nestedMap = parseComplexJsonForWatchedAnime(userAnimeJson);

        var mapForWatchedAnime = new HashMap<String,String>();
        var mapForUserWatcher = new HashMap<String,String>();
        /*
        load user's data
         */
        /*
        you need to add quotes in order to parse since
         quotes are necessary for json recognition
         */
        mapForUserWatcher.put("password",nestedMap.get("password"));
        mapForUserWatcher.put("pseudo", nestedMap.get("pseudo"));
        /*
        load movie data
         */
        //need to be parsed again
        //nestedMap.get("movie");

        //need to be parsed again
        //nestedMap.get("movieStatus");

        //need to be parsed again
        System.out.println("le contenu de time dans nested est : "+nestedMap.get("time"));

        //parse time as json object for test

        //parse time as json object for test
        //HashMap<String, Object > mapTimeAsJsonString;


        HashMap<String, String> fromStringToJsonAnimeStatusMap;

        HashMap<String, String> fromStringToJsonAnimeMap;

        HashMap<String, String> fromStringToJsonTimeMap;
        try {
            fromStringToJsonTimeMap = parseKeyValueString(nestedMap.get("time"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"time\" (movie duration) doesn't respect the json format "+e);
        }
        System.out.println("le json time en string : "+fromStringToJsonTimeMap);
        /*try {
            mapTimeAsJsonString = fromJsonConverter.readValue(fromStringToJsonTime, new TypeReference<HashMap<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format "+e);
        }*/

        try {
            fromStringToJsonAnimeMap = parseKeyValueString(nestedMap.get("movie"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }
        System.out.println("le json du movie en string : "+fromStringToJsonAnimeMap);

        try {
            fromStringToJsonAnimeStatusMap = parseKeyValueString(nestedMap.get("animeStatus"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"animeStatus\" doesn't respect the json format "+e);
        }
        System.out.println("le json du movie en string : "+fromStringToJsonAnimeStatusMap);




        //mapForWatchedMovie.put("");
        //System.out.println("on a trouvé le user principal");
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal();

        var userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));
        //user doesn't exists
        if(userOpt.isEmpty()){
            System.out.println("erreur le user au pseudo: "+nestedMap.get("pseudo")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AnimeShort());
        }

        //user exists
        //but false password
        if(userOpt.get().getPassword().equals(nestedMap.get("password"))){
            System.out.println("erreur le user au password: "+nestedMap.get("password")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AnimeShort());
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        AnimeShort anime;
        try {
            //var personAsString = fromStringToJsonAnimeMap.get("writers").split(" ");
            LinkedHashSet<Person> writerPersons = new LinkedHashSet<>();
            LinkedHashSet<Person> actorPersons = new LinkedHashSet<>();
            for(var personStr:fromStringToJsonAnimeMap.get("Writer").split(",")){
                var firstNameAndLastName = personStr.split(" ");
                writerPersons.add(new Person(firstNameAndLastName[0], firstNameAndLastName[1]));
            }
            for(var personStr:fromStringToJsonAnimeMap.get("Actors").split(",")){
                var firstNameAndLastName= personStr.split(" ");
                actorPersons.add(new Person(firstNameAndLastName[0], firstNameAndLastName[1]));
            }
            anime = new AnimeShort(fromStringToJsonAnimeMap.get("title"), Integer.parseInt(fromStringToJsonAnimeMap.get("yearOfRelease")), fromStringToJsonAnimeMap.get("imdbID") ,
                    fromStringToJsonAnimeMap.get("poster"), actorPersons,writerPersons);
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            System.out.println("erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }
        var animeAlreadyExistsOpt = animeShortService.getAnimeShortByImdb(anime.getImdbID());

        System.out.println("avant time converter");
        //var timeConverter = new TimeConverter();
        //convert string of time to actual time object
        var onlyTimeOfAnime = new TimeConverter.OnlyTime(Integer.parseInt(fromStringToJsonTimeMap.get("seconds")), Integer.parseInt(fromStringToJsonTimeMap.get("minutes")),
                Integer.parseInt(fromStringToJsonTimeMap.get("hours")));


        /*
        the movie is already registered in data base
         */

        if(animeAlreadyExistsOpt.isPresent()){


            //retrieve and update if it already exists
            var alreadyWatchedAnimeOpt =watchedAnimeService.findByUserAndAnime(userOpt.get(),animeAlreadyExistsOpt.get());

            //the movie is already in the data base don't need to recreate with the same imdb
            var animeChosen = animeAlreadyExistsOpt.get();

            //Already exists in database, just update data then
            if(alreadyWatchedAnimeOpt.isPresent()){
                System.out.println("le film est déjà dans la watchlist, on a juste mettre à jour son statut : "
                        +alreadyWatchedAnimeOpt.get());
                var alreadyWatchedAnime = alreadyWatchedAnimeOpt.get();

                //retrieve the new time of the movie (moment of pause for its user)
                alreadyWatchedAnime.setTimeAsLong(fromOnlyTimeToSeconds(onlyTimeOfAnime));

                //update "watched status"
                alreadyWatchedAnime.setArtStatus(VisualArtStatus.fromStringToArtStatus(nestedMap.get("movieStatus")));

                //not necessary due to propagation
                //actualUser.addWatchedAnime(alreadyWatchedAnime);
                animeChosen.addIsWatchedBy(actualUser);

                //makes them persistent in db
                animeShortService.saveAnimeShort(animeChosen);
                //this specific order is compulsory
                watchedAnimeService.saveWatchAnime(alreadyWatchedAnime);
                userService.saveUser(actualUser);

                // Getting organisation object as a json string
                String jsonStr;
                try {
                    jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedAnimeDto(alreadyWatchedAnime));
                } catch (JsonProcessingException e) {
                    throw new AssertionError("not proper json format for watch movie :" +e);
                }

                return ResponseEntity.
                        ok(jsonStr);


            }






            //new watchMovie object instantiate (there was nothing in db)

            var watchedAnime = new WatchedAnime(actualUser,animeAlreadyExistsOpt.get(), VisualArtStatus.fromStringToArtStatus(nestedMap.get("animeStatus")),
                    fromOnlyTimeToSeconds(onlyTimeOfAnime));
            System.out.println("l'état de watched anime avant la création du dto : "+watchedAnime);




            animeChosen.addIsWatchedBy(actualUser);
            //not necessary apparently
            //actualUser.addWatchedAnime(watchedAnime);
            //makes them persistent in db
            animeShortService.saveAnimeShort(animeChosen);
            //this specific order is compulsory
            watchedAnimeService.saveWatchAnime(watchedAnime);
            userService.saveUser(actualUser);
            System.out.println("l'état de watched movie avant la création du dto : "+watchedAnime);
            return ResponseEntity.ok("{}");
        }

        /*
        it's a new movie that is registered in data base
         */

        var watchedAnime = new WatchedAnime(actualUser,anime, VisualArtStatus.fromStringToArtStatus(nestedMap.get("movieStatus")),
                fromOnlyTimeToSeconds(onlyTimeOfAnime));
        System.out.println("l'état de watched movie avant la création du dto : "+watchedAnime);



        //it's a new anime that wasn't in db
        //we save it in db
        anime.addIsWatchedBy(actualUser);
        //not necessary due to propagation
        //actualUser.addWatchedAnime(watchedAnime.getAnimeShort());
        animeShortService.saveAnimeShort(anime);
        userService.saveUser(actualUser);
        watchedAnimeService.saveWatchAnime(watchedAnime);
        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);
        System.out.println("juste avant le responseEntity de watchedAnimeDto");

        // Getting organisation object as a json string
        String jsonStr;
        try {
            jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedAnimeDto(watchedAnime));
        } catch (JsonProcessingException e) {
            throw new AssertionError("not proper json format for watch anime :" +e);
        }

        return ResponseEntity.
                ok(jsonStr);
    }

    /*
    this method retrieves all the movies watched by a user, based on their pseudo
    and password
     */
    @GetMapping("/user/anime")

    public ResponseEntity<String> getAnimeWatched(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="password") String password){

        System.out.println("l'actuel user connecté est : "+USER_CHOSEN.getPseudo());
        Optional<User> userOpt;
        if(USER_CHOSEN.getPseudo() != null && !USER_CHOSEN.getPseudo().isEmpty()){
            userOpt = userService.getUserByPseudo(USER_CHOSEN.getPseudo());
        }
        else {
            userOpt = userService.getUserByPseudo(userPseudo);
        }
        //user not found
        if(userOpt.isEmpty() ){
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ArrayList());
        }
        /*
        wrong password
         */
        if(!userOpt.get().getPassword().equals(password)){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ArrayList());
        }
        //the user exists
        System.out.println("le user a bien été trouvé, on renvoie ses films trouvés en bdd");
        System.out.println("les films du user trouvés sont : "+userOpt.get().getWatchAnime());



        //return the list of movies as a string array
        var watchedAnimes =userOpt.get().getWatchAnime().stream().map(animeShort->new WatchedAnimeDto( animeShort)).toList();
        try {
            var jsonStr = FROMJSONCONVERTER.writeValueAsString(watchedAnimes);
            return ResponseEntity.ok(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }

    /**
     * this method is specific for parsing json of
     * user + watchedAnimeContent
     * @param complexJson
     * the complex json that possess a watchedMovie and a user
     * @return
     *
     * a map containing the data of the
     * user and the anime data.
     */
    public static Map<String, String> parseComplexJsonForWatchedAnime(String complexJson){
        // Convert JSON string to Map
        Map<String, Object> map = null;
        try {
            map = FROMJSONCONVERTER.readValue(complexJson, new TypeReference<Map<String, Object>>() {});

        } catch (JsonProcessingException ex) {
            throw new AssertionError("failure for parsing json anime-user probably inconsistent writting "+ex);
        }
        // Print the Map in an organized manner
        HashMap<String, String> nestedMap = new HashMap<>();
        HashMap<String,String> copyNestedEntries = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            /*
            direct key value
             */
            if (entry.getValue() instanceof Map) {
                var mapTmp = (Map)entry.getValue();
                /*
                check if it's a nested JSON
                 */
                System.out.println("le contenu des values de entry : "+entry.getValue());
                //System.out.println("peut-on le convertir en map que l'on parcours ensuite : "+fromJsonConverter(entry.getValue()));
                //System.out.println("le contenu des values de entry : "+entry.getValue());

                //parse a second time for the nested objects : watchedAnime, userSimple,animeStatus, time
                String watchedNestedDataAsJson = entry.getValue().toString();
//                String animeStatusAsJson = entry.getValue()).get("animeStatus").toString();
//                String timeAsJson = ((Map<?, ?>) entry.getValue()).get("time").toString();
//                String userSimpleAsJson = ((Map<?, ?>) entry.getValue()).get("userSimple").toString();

                System.out.println("on affiche le simple élément récupéré dans le entry récupéré : "+watchedNestedDataAsJson);

                mapTmp.forEach((key,value)->
                        //add quotes because they are necessary for json parsing aftewards
                        copyNestedEntries.put(""+key.toString()+"", value.toString()));
                //.stream().forEach(
                //(key, value)->

                System.out.println("le nouvel état de copy nested : "+copyNestedEntries);
            }

            /*
            direct key list
             */
            else if (entry.getValue() instanceof List) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            else {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }

            /*
            nested map into map
             */
        }
        return  copyNestedEntries;
    }


}
