package nate.company.history_work.controller.movie;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import nate.company.history_work.logger.LoggerInfo;
import nate.company.history_work.service.*;
import nate.company.history_work.siteTools.dtos.MovieDto;
import nate.company.history_work.siteTools.dtos.WatchedMovieDto;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.movie.MovieRepository;
import nate.company.history_work.siteTools.person.Person;
import nate.company.history_work.siteTools.reaction.MovieReaction;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.status.VisualArtStatus;
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
import static nate.company.history_work.siteTools.reaction.ReactionChoices.fromStringToReactionStatus;
import static nate.company.history_work.siteTools.timeHandler.TimeConverter.fromOnlyTimeToSeconds;

/**
 * Acts like a REST controller that manages the requests about movies.
 *
 * @author Nathan BILINGI
 * @author Dylan DE JESUS
 */

@CrossOrigin("*")
// permet de résoudre le problème de "No Access-control-allow-origin" cors policy error
@RestController
public class MovieController {
     /*
   necessary constructor for REST API
     */
    //standard constructors

    /**
     * Repository that stores movies.
     */
    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    private final UserService userService;

    private final MovieService movieService;

    private  final WatchMovieService watchedMovieService;

    private final MovieReactionService movieReactionService;

    private PersonService personService;

    /**
     * Repository that stores movies watched by a user.
     * deprecated
     */
//    private final WatchMovieRepository watchMovieRepository;

    /**
     * Constructs (by myself) filled with a parameter of type UserRepo. Spring will generate
     * the injection of the argument itself when the constructor is called by a third part.
     *
     * @param movieRepository the repository that stores all the movies
     *
     */
    @Autowired
    public MovieController(MovieRepository movieRepository, UserService userService, MovieService movieService,

                           WatchMovieService watchedMovieService,
                           MovieReactionService movieReactionService,
                           PersonService personService){
        Objects.requireNonNull(movieRepository);
        Objects.requireNonNull(userService);
        this.movieRepository = movieRepository;
        this.userService = userService;
        this.watchedMovieService = watchedMovieService;
        this.movieService = movieService;
        this.movieReactionService = movieReactionService;
        this.personService = personService;
    }

    /**
     *
     * a remove method for an association between a movie and a user.
     *
     *
     * the user who watched the movie
     * @param movieDataUserJson
     * the movie watch with its data.
     *
     * the movie and the user who watches this movie.
     *
     * @return
     * a response that precises if everything end up properly.
     */
    //overpowerful deprecated

    @DeleteMapping("/user/movie/remove")
    public ResponseEntity<String> removeMovieFromList(@RequestBody String movieDataUserJson) {
        LOGGER.log(Level.INFO,"les infos du json en brut sont : "+movieDataUserJson);
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal()

        Optional<User> userOpt;
        //get user thanks to back end save of persistent session
        if(USER_CHOSEN.getPseudo() != null && USER_CHOSEN.getPseudo().isEmpty() == false){
            LOGGER.log(Level.INFO," on récupère les infos du user grâce au back end et pas le front end");
            userOpt = userService.getUserByPseudo(USER_CHOSEN.getPseudo());
        }
        //use the request to retrieve user's information (password + pseudo)
        else {
            //deprecated
             //userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));

            //user doesn't exists
            LOGGER.log(Level.INFO,"on envoie le json classique 0 : user not connected");
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
        Movie movie;
        TimeConverter.OnlyTime onlyTimeOfMovie;
        WatchedMovie watchedMovie;

        var nestedMap = parseComplexJsonForWatchedMovie(movieDataUserJson);

        //map of movie data
        HashMap<String,String> fromStringToJsonMovieMap;

        try {
            //retrieve movie data
            fromStringToJsonMovieMap = parseKeyValueString(nestedMap.get("movie"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }
        try {
            var personAsString = fromStringToJsonMovieMap.get("director").split(" ");
            movie = new Movie(fromStringToJsonMovieMap.get("title"), Integer.parseInt(fromStringToJsonMovieMap.get("yearOfRelease")), fromStringToJsonMovieMap.get("imdbID"),
                    new Person(personAsString[0], personAsString[1]),
                    fromStringToJsonMovieMap.get("poster"));
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        if(movieAlreadyExistsOpt.isPresent()){

            LOGGER.log(Level.INFO,"le film a bien été "+movie+" ( found)");
            //remove the watched movie line from database
            var watchedToRemoveOpt = watchedMovieService.findByUserAndMovie(actualUser, movieAlreadyExistsOpt.get());
            /*
            movie not found, but it's not a problem
             */
            if(watchedToRemoveOpt.isEmpty()){
                LOGGER.log(Level.INFO,"le film à supprimer n'a pas été trouvé pour le user "+actualUser+ " "+movie+" (not found)");
                return ResponseEntity.ok("{}");
            }
            /*
            movie found, you remove this
             */
            LOGGER.log(Level.INFO,"le watch movie à supprimer est : "+watchedToRemoveOpt.get());
            watchedMovieService.removeByIdMovie(watchedToRemoveOpt.get().getId());
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            actualUser.removeFromWatchedMovie(watchedToRemoveOpt.get().getMovie());
            movieChosen.removeUserFromWatcher(actualUser);


            //update with the removal of the movie in the lists
            movieService.saveMovie(movieChosen);
            userService.saveUser(actualUser);
            //send the movie as json
            try {
                var jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedMovieDto(watchedToRemoveOpt.get()));
                return ResponseEntity.ok(jsonStr);
            } catch (JsonProcessingException e) {
                throw new AssertionError("not proper json format for watch movie : "+e);
            }
        }
        /*
        movie not found, but it's not a problem
         */
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

    @PostMapping("/user/movie/react")
    public ResponseEntity<String> reactMovie(@RequestBody String userMovieReactJson){
        LOGGER.log(Level.INFO, " on ajoute le film: "+userMovieReactJson);
        //user not connected abnormal
        var nestedMap = parseComplexJsonForWatchedMovie(userMovieReactJson);

        var mapForWatchedMovie = new HashMap<String,String>();
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
        nestedMap.get("movie");



        //parse time as json object for test


        HashMap<String, String> fromStringToJsonMovieMap;

        HashMap<String, String> fromStringToJsonReactMap;
        /*
        retrieve movie data into map
         */
        try {
            fromStringToJsonMovieMap = parseKeyValueString(nestedMap.get("movie"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }

        /*
        retrieve reaction data into map
         */
        try {
            fromStringToJsonReactMap = parseKeyValueString(nestedMap.get("reactionChoice"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"movieStatus\" doesn't respect the json format "+e);
        }




        //mapForWatchedMovie.put("");
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal();

        var userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));
        //user doesn't exists
        if(userOpt.isEmpty()){
            LOGGER.log(Level.INFO,"erreur le user au pseudo: "+nestedMap.get("pseudo")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists
        //but false password
        if(userOpt.get().getPassword().equals(nestedMap.get("password"))){
            LOGGER.log(Level.INFO,"erreur le user au password: "+nestedMap.get("password")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        Movie movie;
        try {
            var personAsString = fromStringToJsonMovieMap.get("director").split(" ");
            movie = new Movie(fromStringToJsonMovieMap.get("title"), Integer.parseInt(fromStringToJsonMovieMap.get("yearOfRelease")), fromStringToJsonMovieMap.get("imdbID"),
                    new Person(personAsString[0], personAsString[1]),
                    fromStringToJsonMovieMap.get("poster"));
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            LOGGER.log(Level.INFO,"erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        /*
        the movie is already registered in data base
         */

        if(movieAlreadyExistsOpt.isPresent()){


            //retrieve and update if it already exists
            var alreadyReactedMovieOpt =movieReactionService.findByUserAndMovie(userOpt.get(),movieAlreadyExistsOpt.get());

            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();

            //Already exists in database, just update data then
            if(alreadyReactedMovieOpt.isPresent()){
                LOGGER.log(Level.INFO,"le film est déjà dans la reactionList, on a juste mettre à jour son statut : "
                        +alreadyReactedMovieOpt.get());
                var alreadyReactedMovie = alreadyReactedMovieOpt.get();

                //update "watched status"
                alreadyReactedMovie.setReaction(fromStringToReactionStatus(nestedMap.get("reactionChoice")));

                actualUser.reactMovie(alreadyReactedMovie);
                movieChosen.reactUser(alreadyReactedMovie);

                //makes them persistent in db
                movieService.saveMovie(movieChosen);
                //this specific order is compulsory
                movieReactionService.save(alreadyReactedMovie);
                userService.saveUser(actualUser);

                // Getting organisation object as a json string
                String jsonStr;
                try {
                    jsonStr = FROMJSONCONVERTER.writeValueAsString(new MovieDto(movieChosen));
                } catch (JsonProcessingException e) {
                    throw new AssertionError("not proper json format for watch movie :" +e);
                }

                return ResponseEntity.
                        ok(jsonStr);


            }






            //new reaction object instantiate (there was nothing in db)

            var reacted = new MovieReaction(actualUser,fromStringToReactionStatus(nestedMap.get("reactionChoice")),movieAlreadyExistsOpt.get());

            movieChosen.reactUser(reacted);
            actualUser.reactMovie(reacted);
            //makes them persistent in db
            movieService.saveMovie(movieChosen);
            //this specific order is compulsory
            movieReactionService.save(reacted);
            userService.saveUser(actualUser);
            return ResponseEntity.ok("{}");
        }

        /*
        it's a new watched movie that is registered in data base
         */

        var reacted = new MovieReaction(actualUser,fromStringToReactionStatus(nestedMap.get("reactionChoice")),movie);
        //it's a new movie that wasn't in db
        //we save it in db
        movie.reactUser(reacted);
        movieService.saveMovie(movie);
        userService.saveUser(actualUser);
        movieReactionService.save(reacted);
        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);

        // Getting organisation object as a json string
        String jsonStr;
        try {
            jsonStr = FROMJSONCONVERTER.writeValueAsString(new MovieDto(movie));
        } catch (JsonProcessingException e) {
            throw new AssertionError("not proper json format for watch movie :" +e);
        }

        return ResponseEntity.
                ok(jsonStr);
    }

























    /**
     * The "add movie" add a new movie into the data base.
     *
     * @param userMovieJson
     * the user that will have the movie in their list as json. and
     * the movie that will be added as json.
     *
     * @return the retrieved movie
     */
    @PostMapping("/user/movie/add")
    public ResponseEntity<String> addMovie(@RequestBody String userMovieJson){
        LOGGER.log(Level.INFO,"on ajoute le film et le user dans movie/add: "+userMovieJson);

        LOGGER.log(Level.INFO,("l'actuel user connecté est : "+USER_CHOSEN.getPseudo()));

        //regex searched : {({.*})\,({.*})}
        LOGGER.log(Level.INFO, " on ajoute le film : "+userMovieJson);
        //user not connected abnormal
        var nestedMap = parseComplexJsonForWatchedMovie(userMovieJson);

        var mapForWatchedMovie = new HashMap<String,String>();
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

        HashMap<String, String> fromStringToJsonMovieStatusMap;

        HashMap<String, String> fromStringToJsonMovieMap;

        HashMap<String, String> fromStringToJsonTimeMap;
        try {
            fromStringToJsonTimeMap = parseKeyValueString(nestedMap.get("time"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"time\" (movie duration) doesn't respect the json format "+e);
        }
        /*try {
            mapTimeAsJsonString = fromJsonConverter.readValue(fromStringToJsonTime, new TypeReference<HashMap<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format "+e);
        }*/

        try {
            fromStringToJsonMovieMap = parseKeyValueString(nestedMap.get("movie"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }

        try {
            fromStringToJsonMovieStatusMap = parseKeyValueString(nestedMap.get("movieStatus"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"movieStatus\" doesn't respect the json format "+e);
        }

        var userOpt = userService.getUserByPseudo(USER_CHOSEN.getPseudo());
        //user doesn't exists
        if(userOpt.isEmpty()){
            LOGGER.log(Level.INFO,"erreur le user au pseudo: "+nestedMap.get("pseudo")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        Movie movie;
        Person director;

        try {
            var personAsString = fromStringToJsonMovieMap.get("director").split(" ");
            director = new Person(personAsString[0], personAsString[1]);
            //necessary to prevent from getId issue since movie will be set aftewards
            //save if person doesn't exist

            //find person in db if already exist
            if(personService.findByFirstNameAndLastName(director.getFirstName(), director.getLastName()).isPresent()) {
                director = personService.findByFirstNameAndLastName(director.getFirstName(), director.getLastName()).get();
            }
            //new person
            else {
                personService.savePerson(director);
                director = personService.findByFirstNameAndLastName(director.getFirstName(), director.getLastName()).get();
            }

                //movie already exists
            if(movieService.getMovieByImdb(fromStringToJsonMovieMap.get("imdbID")).isPresent()) {
                movie = movieService.getMovieByImdb(fromStringToJsonMovieMap.get("imdbID")).get();
            }
            //doesn't exist
            else{
                movie = new Movie(fromStringToJsonMovieMap.get("title"), Integer.parseInt(fromStringToJsonMovieMap.get("yearOfRelease")), fromStringToJsonMovieMap.get("imdbID"),
                        director, fromStringToJsonMovieMap.get("poster"));

            }
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");

            LOGGER.log(Level.INFO,"erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }


        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        //var timeConverter = new TimeConverter();
        //convert string of time to actual time object
        var onlyTimeOfMovie = new TimeConverter.OnlyTime(Integer.parseInt(fromStringToJsonTimeMap.get("seconds")), Integer.parseInt(fromStringToJsonTimeMap.get("minutes")),
                Integer.parseInt(fromStringToJsonTimeMap.get("hours")));


        /*
        the movie is already registered in data base
         */

        if(movieAlreadyExistsOpt.isPresent()){

            //retrieve and update if it already exists
            var alreadyWatchedMovieOpt =watchedMovieService.findByUserAndMovie(userOpt.get(),movieAlreadyExistsOpt.get());
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();

            //Already exists in database, just update data then
            if(alreadyWatchedMovieOpt.isPresent()){
                LOGGER.log(Level.INFO,"le film est déjà dans la watchlist, on a juste mettre à jour son statut : "
                        +alreadyWatchedMovieOpt.get());
                var alreadyWatchedMovie = alreadyWatchedMovieOpt.get();

                //retrieve the new time of the movie (moment of pause for its user)
                alreadyWatchedMovie.setTimeAsLong(fromOnlyTimeToSeconds(onlyTimeOfMovie));

                //update "watched status"
                alreadyWatchedMovie.setArtStatus(VisualArtStatus.fromStringToArtStatus(nestedMap.get("movieStatus")));

                actualUser.addWatchedMovie(alreadyWatchedMovie);
                movieChosen.addIsWatchedBy(actualUser);

                //makes them persistent in db
                movieService.saveMovie(movieChosen);
                //this specific order is compulsory
                watchedMovieService.saveWatchMovie(alreadyWatchedMovie);
                userService.saveUser(actualUser);

                // Getting organisation object as a json string
                String jsonStr;
                try {
                    jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedMovieDto(alreadyWatchedMovie));
                } catch (JsonProcessingException e) {
                    throw new AssertionError("not proper json format for watch movie :" +e);
                }

                return ResponseEntity.
                        ok(jsonStr);


            }





            //new watchMovie object instantiate (there was nothing in db)

            var watchedMovie = new WatchedMovie(actualUser,movieAlreadyExistsOpt.get(), VisualArtStatus.fromStringToArtStatus(nestedMap.get("movieStatus")),
                    fromOnlyTimeToSeconds(onlyTimeOfMovie));

            movieChosen.addIsWatchedBy(actualUser);
            actualUser.addWatchedMovie(watchedMovie);
            //makes them persistent in db
            personService.savePerson(director);

            movieService.saveMovie(movieChosen);
            //find with the movie's id
            movieChosen = movieService.getMovieByImdb(movieChosen.getImdbID()).get();
            //this specific order is compulsory
            watchedMovieService.saveWatchMovie(watchedMovie);
            userService.saveUser(actualUser);
            return ResponseEntity.ok("{}");
        }

        /*
        it's a new movie that is registered in data base
         */



        //it's a new movie that wasn't in db
        //we save it in db
        //movie.addIsWatchedBy(actualUser);


        //director.addMovieDirected(movie);
        //makes them persistent in db
        //retrieve director with the actual id
        //director = personService.findByFirstNameAndLastName(director.getFirstName(),director.getLastName()).get();

        //update with the suppose propagate movie's id --> it doesn't work
        //personService.savePerson(director);

        //necessary to prevent from get issue with watchMovie because the id of the direct won't be set (0 is not a proper id except
        // if you want to save a new instance)
        //director = personService.findByFirstNameAndLastName(director.getFirstName(), director.getLastName()).get();

        if(movieService.getMovieByImdb(movie.getImdbID()).isPresent()){
            //update due to propagation
            movie = movieService.getMovieByImdb(movie.getImdbID()).get();
            LOGGER.log(Level.INFO,"il n' y a PAS un double save");

        }else {
            LOGGER.log(Level.INFO,"il y a un double save");

            LOGGER.log(Level.INFO," ON teste les logs !!");
            //movie.setDirector(director);
            //need its director because director is not null otherwisee cause error
            movieService.saveMovie(movie);
            //update the movie with its actual id
//            director.addMovieDirected(movie);
            personService.savePerson(director);
            //update movie with new data
            movie = movieService.getMovieByImdb(movie.getImdbID()).get();
        }
        var watchedMovie = new WatchedMovie(actualUser,movie, VisualArtStatus.fromStringToArtStatus(nestedMap.get("movieStatus")),fromOnlyTimeToSeconds(onlyTimeOfMovie));
        watchedMovieService.saveWatchMovie(watchedMovie);
        watchedMovie = watchedMovieService.findByUserAndMovie(actualUser, movie).get();
        //add the movie as watched : not necessary
        //actualUser.addWatchedMovie(watchedMovie);
        movieService.saveMovie(movie);
        LOGGER.log(Level.INFO," ON REGARDE user SA LISTE DE WATCHEDMOVIES DE SON pdv : "+ actualUser.getWatchMovies());
        LOGGER.log(Level.INFO, " on a sauvegardé le film dans la liste du user : "+actualUser);
        LOGGER.log(Level.INFO,"juste avant le responseEntity de watchedmovieDto");

        // Getting organisation object as a json string
        String jsonStr;
        try {
            jsonStr = FROMJSONCONVERTER.writeValueAsString(new WatchedMovieDto(watchedMovie));
        } catch (JsonProcessingException e) {
            throw new AssertionError("not proper json format for watch movie :" +e);
        }

        return ResponseEntity.
                ok(jsonStr);
    }

    /*
    this method retrieves all the movies watched by a user, based on their pseudo
    and password
     */
    @GetMapping("/user/movie")

    public ResponseEntity<String> getMoviesWatched(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="password") String password){
        //doesn't work for now, use another approach
//        if(userService.getPrincipal().isEmpty()) {
//            LOGGER.log(Level.INFO, "l'utilisateur pour lequel on cherche les filmsf, est non connecté");
//            System.out.println("on a pas trouvé le user principal pour la listes des films vus");
//            return ResponseEntity.ok(List.of());
//        }

        LOGGER.log(Level.INFO,"l'actuel user connecté est : "+USER_CHOSEN.getPseudo());
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
        LOGGER.log(Level.INFO,"le user a bien été trouvé, on renvoie ses films trouvés en bdd");
        LOGGER.log(Level.INFO,"les films du user trouvés sont : "+userOpt.get().getWatchMovies());

        //return the list of movies as a string array
        var watchedMovies = userOpt.get().getWatchMovies().stream().map(movie->new WatchedMovieDto(movie)).toList();
        try {
            var jsonStr = FROMJSONCONVERTER.writeValueAsString(watchedMovies);
            return ResponseEntity.ok(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }

    /**
     * this method is specific for parsing json of
     * user + watchedMovieContent
     * @param complexJson
     * the complex json that possess a watchedMovie and a user
     * @return
     *
     * a map containing the data of the
     * user and the movie data.
     */
    public static Map<String, String> parseComplexJsonForWatchedMovie(String complexJson){
        // Convert JSON string to Map
        Map<String, Object> map = null;
        try {
            map = FROMJSONCONVERTER.readValue(complexJson, new TypeReference<Map<String, Object>>() {});

        } catch (JsonProcessingException ex) {
            throw new AssertionError("failure for parsing json movie-user probably inconsistent writting "+ex);
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


                mapTmp.forEach((key,value)->
                        //add quotes because they are necessary for json parsing aftewards
                        copyNestedEntries.put(""+key.toString()+"", value.toString()));
            }

            /*
            direct key list
             */
            else if (entry.getValue() instanceof List) {
                LOGGER.log(Level.INFO,entry.getKey() + " = " + entry.getValue());
            }

            else {
                LOGGER.log(Level.INFO,entry.getKey() + " = " + entry.getValue());
            }

            /*
            nested map into map
             */
        }
        return  copyNestedEntries;
    }


}







