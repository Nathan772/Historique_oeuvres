package nate.company.history_work.controller.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.thoughtworks.qdox.parser.impl.Parser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nate.company.history_work.handle_connection_spring.ActiveUserStore;
import nate.company.history_work.service.*;
import nate.company.history_work.siteTools.authentication.LoginRequest;
import nate.company.history_work.siteTools.dtos.MovieDto;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.dtos.WatchedMovieDto;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.timeHandler.TimeConverter;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.watchedMovie.MovieStatus;
import nate.company.history_work.siteTools.watchedMovie.WatchedMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.main.java.nate.company.history_work.siteTools.user.UserCategory;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;
import static nate.company.history_work.siteTools.json.JsonTools.parseKeyValueString;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * User controller.
 */

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    /*
   necessary constructor for REST API
     */
    //standard constructors
    /**
     * The repository that stores users
     */
    //deprecated approach
    //private final UserRepository userRepository;
    @Autowired
    private final UserService userService;

    @Autowired
    private ActiveUserStore activeUserStore;

    private final MovieService movieService;

    private  final WatchMovieService watchedMovieService;

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final MyUserDetailsService userDetailsService;

    private final ObjectWriter jsonConverter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private static final ObjectMapper fromJsonConverter = new ObjectMapper();

    /**
     * constructor selfmade
     *
     * @param userService the service that enables to handle repositories and treatment
     */
    @Autowired
    public UserController(UserService userService, MovieService movieService, AuthenticationService authenticationService,
                          MyUserDetailsService userDetailsService, WatchMovieService watchedMovieService){
        Objects.requireNonNull(userService);
        Objects.requireNonNull(userDetailsService);
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.movieService = movieService;
        this.authenticationService= authenticationService;
        this.watchedMovieService = watchedMovieService;

    }



    /**
     * this method retrieves all the users from the database
     * (linked to "findAll" from user.service)
     *
     * @return the list containing all the users
     */
    //@RequestMapping("/users")
    @GetMapping("/users")
    public List<UserDto> getUsers(){
        return userService.getAllUsers();
    }

    /**
     * this method retrieve a specific user if exists in database
     * @param userId user's id
     * @param userPseudo pseudo of the user
     * @param email the email from this user
     * @param password the password for this account the user you want to retrieve
     * @return the user searched if found, else null
     */
    /*
    since we use get with parameters,
    we have to retrieve each parameter one by one.
    You must match the parameters
    used in the url with a
    "(name ="paremeter1InTheUrlName") JavaTypeParameter1 param1NameForJava,
    etc..."
    You can check the "right-click + console" to retrieve parameter's names but these are just the names of the fields of the
    class you defined in your angular class file.
    You can prevent your api from explictly saying "RequestParam(name='...')" if the java parameters' method name matches, the name of the
    field of your class (here the class is user). Here it purpesofuly, matches for email and password (for the sake of the example)

     */
    // rather post than get mapping in order to hide user data
    //overkill
//    @GetMapping("/userSearch")
//    @ResponseBody
//    public ResponseEntity<?> getUser(@RequestParam(name="id") String userId, @RequestParam(name="pseudo") String userPseudo,
//                                     @RequestParam(name="email") String email, @RequestParam(name="password") String password
//                                     ){
//        var userByEmail = userService.getUserByMail(email);
//
//        //the user already exists
//        if(userByEmail.isPresent()){
//            return ResponseEntity.ok(userByEmail.get());
//        }
//        var userByPseudo = userService.getUserByPseudo(userPseudo);
//        if(userByPseudo.isPresent()){
//            return ResponseEntity.ok(userByPseudo.get());
//        }
//
//        //they don't exist
//        return ResponseEntity.ok().build();
//    }







    /**
     * this method retrieve a specific user if exists in database
     * @param userPseudo pseudo of the user
     * @param email the email from this user
     * @return the user searched if found, else null
     */
    /*
    since we use get with parameters,
    we have to retrieve each parameter one by one.
    You must match the parameters
    used in the url with a
    "(name ="paremeter1InTheUrlName") JavaTypeParameter1 param1NameForJava,
    etc..."
    You can check the "right-click + console" to retrieve parameter's names but these are just the names of the fields of the
    class you defined in your angular class file.
    You can prevent your api from explictly saying "RequestParam(name='...')" if the java parameters' method name matches, the name of the
    field of your class (here the class is user). Here it purpesofuly, matches for email and password (for the sake of the example)

     */
    @GetMapping("/userSearch")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="email") String email){
        var userByEmail = userService.getUserByMail(email);
        System.out.println("on va chercher un user par pseudo");
        LOGGER.log(Level.INFO, "On va chercher un user");
        //the user already exists
        if(userByEmail.isPresent()){
            return ResponseEntity.ok(userByEmail.get());
        }
        var userByPseudo = userService.getUserByPseudo(userPseudo);
        if(userByPseudo.isPresent()){
            return ResponseEntity.ok(userByPseudo.get());
        }


        LOGGER.log(Level.INFO, " le user n'existe pas en bdd");
        //they don't exist
        return ResponseEntity.badRequest().build();
    }

    /**
     * this method retrieve a specific user if exists in database
     * @param userPseudo pseudo of the user
     * @param email the email from this user
     * @return the user searched if found, else null
     */
    /*
    since we use get with parameters,
    we have to retrieve each parameter one by one.
    You must match the parameters
    used in the url with a
    "(name ="paremeter1InTheUrlName") JavaTypeParameter1 param1NameForJava,
    etc..."
    You can check the "right-click + console" to retrieve parameter's names but these are just the names of the fields of the
    class you defined in your angular class file.
    You can prevent your api from explictly saying "RequestParam(name='...')" if the java parameters' method name matches, the name of the
    field of your class (here the class is user). Here it purpesofuly, matches for email and password (for the sake of the example)

     */
    @GetMapping("/userSearch/boolean")
    @ResponseBody
    public ResponseEntity<Boolean> checkUserExists(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="email") String email){
        var userByEmail = userService.getUserByMail(email);
        System.out.println("on va chercher un user par pseudo");
        LOGGER.log(Level.INFO, "On va chercher un user");
        //the user already exists
        if(userByEmail.isPresent()){
            return ResponseEntity.ok(true);
        }
        var userByPseudo = userService.getUserByPseudo(userPseudo);
        if(userByPseudo.isPresent()){
            return ResponseEntity.ok(true);
        }


        LOGGER.log(Level.INFO, " le user n'existe pas en bdd");
        //they don't exist
        return ResponseEntity.ok(false);
    }


    /**
     * this method parses a complex json
     * @param complexJson
     * the complex json one wants to parse
     * @return
     * the data of the complelx json as map of string string
     */

    public static Map<String, String> parseComplexJson(String complexJson){
        // Convert JSON string to Map
        Map<String, Object> map = null;
        try {
            map = fromJsonConverter.readValue(complexJson, new TypeReference<Map<String, Object>>() {});

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


//                try {
//                    //known as string object
//                    fromJsonConverter.readValue((String)entry.getValue(), new TypeReference<>() {});
//
//                    //add json data
//                    copyNestedEntries.putAll(parseComplexJson((String)entry.getValue()));
//                } catch (JsonProcessingException e) {
//                    throw new IllegalArgumentException("Error : it's not a json "+e);
//                }

                System.out.println(entry.getKey() + " = ");
                nestedMap = (HashMap<String, String>) entry.getValue();
                for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
                    System.out.println("    " + nestedEntry.getKey() + " = " + nestedEntry.getValue());
                    copyNestedEntries.put(nestedEntry.getKey(), nestedEntry.getValue());
                }
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
            map = fromJsonConverter.readValue(complexJson, new TypeReference<Map<String, Object>>() {});

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
                System.out.println("le contenu des values de entry : "+entry.getValue());
                //System.out.println("peut-on le convertir en map que l'on parcours ensuite : "+fromJsonConverter(entry.getValue()));
                //System.out.println("le contenu des values de entry : "+entry.getValue());

                //parse a second time for the nested objects : watchedMovie, userSimple, movieStatus, time
                String watchedNestedDataAsJson = entry.getValue().toString();
//                String movieStatusAsJson = entry.getValue()).get("movieStatus").toString();
//                String timeAsJson = ((Map<?, ?>) entry.getValue()).get("time").toString();
//                String userSimpleAsJson = ((Map<?, ?>) entry.getValue()).get("userSimple").toString();

                System.out.println("on affiche le simple élément récupéré dans le entry récupéré : "+watchedNestedDataAsJson);

                mapTmp.forEach((key,value)->
                        //add quotes because they are necessary for json parsing aftewards
                        copyNestedEntries.put(""+key.toString()+"", value.toString()));
                        //.stream().forEach(
                        //(key, value)->

                System.out.println("le nouvel état de copy nested : "+copyNestedEntries);


















                //fromJsonConverter.readValue(userJson, new TypeReference<>() {}
//                try {
//                    //known as string object
//                    fromJsonConverter.readValue((String)entry.getValue(), new TypeReference<>() {});
//
//                    //add json data
//                    copyNestedEntries.putAll(parseComplexJson((String)entry.getValue()));
//                } catch (JsonProcessingException e) {
//                    throw new IllegalArgumentException("Error : it's not a json "+e);
//                }

                  /*
                check if it's a nested JSON
                 */
//                for(var entry2:((Map<?, ?>) entry.getValue()).entrySet()){
//                    System.out.println("le contenu des clés de entry : "+entry2.getKey());
//                    System.out.println("le contenu des values de entry : "+entry2.getValue());
//                    nestedMap.putAll(parseComplexJson(entry2.getValue());
//                }
//                System.out.println(entry.getKey() + " = ");
//                nestedMap = (HashMap<String, String>) entry.getValue();
//
//                for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
//                    System.out.println("    " + nestedEntry.getKey() + " = " + nestedEntry.getValue());
//                    copyNestedEntries.put(nestedEntry.getKey(), nestedEntry.getValue());
//                }
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

        //System.out.println("on a trouvé le user principal");
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal();
        var nestedMap = parseComplexJson(movieDataUserJson);
        var userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));
        //user doesn't exists
        if (userOpt.isEmpty()) {
            System.out.println("on envoie le json classique 0");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }

        //user exists
        //but false password
        if (userOpt.get().getPassword().equals(nestedMap.get("password"))) {
            System.out.println("on envoie le json classique 1");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        Movie movie;
        TimeConverter.OnlyTime onlyTimeOfMovie;
        WatchedMovie watchedMovie;
        try {
            movie = new Movie(nestedMap.get("title"), Integer.parseInt(nestedMap.get("yearOfRelease")), nestedMap.get("imdbID"), nestedMap.get("director"), nestedMap.get("poster"));
//            var timeConverter = new TimeConverter();
//            onlyTimeOfMovie = new TimeConverter.OnlyTime(Long.parseLong(nestedMap.get("hours")),
//                    Long.parseLong(nestedMap.get("minutes")),Long.parseLong(nestedMap.get("seconds")));
//            watchedMovie = new WatchedMovie(actualUser,movie, MovieStatus.fromStringToMovieStatus(nestedMap.get("movieStatus")),
//                    TimeConverter.fromOnlyTimeToSeconds(onlyTimeOfMovie));

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

            //remove the watched movie line from database
            var watchedToRemoveOpt = watchedMovieService.findByUserAndMovie(actualUser, movieAlreadyExistsOpt.get());
            /*
            movie not found, but it's not a problem
             */
            if(watchedToRemoveOpt.isEmpty()){
                return ResponseEntity.ok("{}");
            }
            /*
            movie found, you remove this
             */
            System.out.println("le watch movie à supprimer est : "+watchedToRemoveOpt.get());
            watchedMovieService.removeByIdMovie(watchedToRemoveOpt.get().getId());
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            actualUser.removeFromWatchedMovie(watchedToRemoveOpt.get());
            movieChosen.removeUserFromWatcher(actualUser);


            //update with the removal of the movie in the lists
            movieService.saveMovie(movieChosen);
            userService.saveUser(actualUser);
            //send the movie as json
            try {
                System.out.println("on envoie le json classique 2");
                var jsonStr = fromJsonConverter.writeValueAsString(new WatchedMovieDto(watchedToRemoveOpt.get()));
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
     * The "add movie" add a new movie into the data base.
     *
     * @param userMovieJson
     * the user that will have the movie in their list as json. and
     * the movie that will be added as json.
     *
     * @return the retrieved movie
     */
    //Requestbody can be used only once,
    //thereby you use a wrapper class or two request param
    //you cannot
    @PostMapping("/user/movie/add")
    public ResponseEntity<String> addMovie(@RequestBody String userMovieJson){
        System.out.println("on ajoute le film et le user : "+userMovieJson);
        //regex searched : {({.*})\,({.*})}
        LOGGER.log(Level.INFO, " on ajoute le film : "+userMovieJson);
        //user not connected abnormal
        // doesn't work for now, use another approach
//        if(userService.getPrincipal().isEmpty()){
//            System.out.println("on ne trouve pas le user principal");
//            //EMPTY MOVIE RETURNED IF Not connected
//            return ResponseEntity.ok(new Movie());
//        }
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
        /*
        load movie data
         */
        //need to be parsed again
        nestedMap.get("movie");

        //need to be parsed again
        nestedMap.get("movieStatus");

        //need to be parsed again
        System.out.println("le contenu de time dans nested est : "+nestedMap.get("time"));

        //parse time as json object for test

        //parse time as json object for test
        //HashMap<String, Object > mapTimeAsJsonString;


        HashMap<String, String> fromStringToJsonMovieStatusMap;

        HashMap<String, String> fromStringToJsonMovieMap;

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
            fromStringToJsonMovieMap = parseKeyValueString(nestedMap.get("movie"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received movie doesn't respect the json format "+e);
        }
        System.out.println("le json du movie en string : "+fromStringToJsonMovieMap);

        try {
            fromStringToJsonMovieStatusMap = parseKeyValueString(nestedMap.get("movieStatus"));
        } catch (Exception e) {
            throw  new IllegalArgumentException("Error : the json received as \"movieStatus\" doesn't respect the json format "+e);
        }
        System.out.println("le json du movie en string : "+fromStringToJsonMovieStatusMap);




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
                    .body(new Movie());
        }

        //user exists
        //but false password
        if(userOpt.get().getPassword().equals(nestedMap.get("password"))){
            System.out.println("erreur le user au password: "+nestedMap.get("password")+" n'existe pas ");
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        Movie movie;
        try {
            movie = new Movie(fromStringToJsonMovieMap.get("title"), Integer.parseInt(fromStringToJsonMovieMap.get("yearOfRelease")), fromStringToJsonMovieMap.get("imdbID"),
                    fromStringToJsonMovieMap.get("director"),
                    fromStringToJsonMovieMap.get("poster"));
        }

        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            System.out.println("erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{}");
        }
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        System.out.println("avant time converter");
        var timeConverter = new TimeConverter();
        var onlyTimeOfMovie = new TimeConverter.OnlyTime(Long.parseLong(fromStringToJsonTimeMap.get("hours")),
                Long.parseLong(fromStringToJsonTimeMap.get("minutes")),Long.parseLong(fromStringToJsonTimeMap.get("seconds")));


        /*
        the movie is already registered in data base
         */

        if(movieAlreadyExistsOpt.isPresent()){
            var watchedMovie = new WatchedMovie(actualUser,movieAlreadyExistsOpt.get(), MovieStatus.fromStringToMovieStatus(nestedMap.get("movieStatus")),
                    TimeConverter.fromOnlyTimeToSeconds(onlyTimeOfMovie));
            System.out.println("l'état de watched movie avant la création du dto : "+watchedMovie);
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            movieChosen.addIsWatchedBy(actualUser);
            actualUser.addWatchedMovie(watchedMovie);
            //makes them persistent in db
            movieService.saveMovie(movieChosen);
            //this specific order is compulsory
            watchedMovieService.saveWatchMovie(watchedMovie);
            userService.saveUser(actualUser);
            System.out.println("l'état de watched movie avant la création du dto : "+watchedMovie);
            return ResponseEntity.ok("{}");
        }

        /*
        it's a new movie that is registered in data base
         */

        var watchedMovie = new WatchedMovie(actualUser,movie, MovieStatus.fromStringToMovieStatus(nestedMap.get("movieStatus")),
                TimeConverter.fromOnlyTimeToSeconds(onlyTimeOfMovie));
        System.out.println("l'état de watched movie avant la création du dto : "+watchedMovie);



        //it's a new movie that wasn't in db
        //we save it in db
        movie.addIsWatchedBy(actualUser);
        movieService.saveMovie(movie);
        userService.saveUser(actualUser);
        watchedMovieService.saveWatchMovie(watchedMovie);
        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);
        System.out.println("juste avant le responseEntity de watchedmovieDto");

        // Getting organisation object as a json string
        String jsonStr;
        try {
            jsonStr = fromJsonConverter.writeValueAsString(new WatchedMovieDto(watchedMovie));
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
    public ResponseEntity<?> getMoviesWatched(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="password") String password){
        //doesn't for now, use another approach
//        if(userService.getPrincipal().isEmpty()) {
//            LOGGER.log(Level.INFO, "l'utilisateur pour lequel on cherche les filmsf, est non connecté");
//            System.out.println("on a pas trouvé le user principal pour la listes des films vus");
//            return ResponseEntity.ok(List.of());
//        }

        var userOpt = userService.getUserByPseudo(userPseudo);
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
        System.out.println("les films du user trouvés sont : "+userOpt.get().getWatchMovies());



        return ResponseEntity.ok(userOpt.get().getWatchMovies().stream().map(movie->new WatchedMovieDto(movie)).toList());
    }

    /**
     * Adds a new user in the database.
     *
     * @param userJson the user as json you want to add.
     * @return the user insterd in the database
     *
     *
     * it needs to be changed by json object that you will parse later
     */
    @PostMapping("/save/user")
    public User addUser(@RequestBody String userJson){
        Objects.requireNonNull(userJson);
        /* https://stackoverflow.com/questions/7246157/how-to-parse-a-json-string-to-an-array-using-jackson : convert*/
        // source : https://stackoverflow.com/questions/29313687/trying-to-use-spring-boot-rest-to-read-json-string-from-post

        System.out.println("le json de sauvegarde du user ressemble à ça en string : "+userJson);
        // Convert JSON string to Map
        Map<String, String> map;
        try {
            map = fromJsonConverter.readValue(userJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format "+e);
        }
        LOGGER.info("json user's parsing succeed : "+map);
        var user = new User(map.get("pseudo"),map.get("email"),map.get("password"));
        user.setCategory(UserCategory.AVERAGE);
        var userByPseudo =  userService.getUserByPseudo(user.getPseudo());
        var userByMail = userService.getUserByMail(user.getEmail());

        //already exists
        if(userByPseudo.isPresent()){
            LOGGER.log(Level.INFO, " le user est déjà présent : "+userByPseudo.get());
            return userByPseudo.get();
        }
        if(userByMail.isPresent()){
            LOGGER.log(Level.INFO, " le user est déjà présent : "+userByMail.get());
            return userByMail.get();
        }
        LOGGER.log(Level.INFO, " le user n'existe pas en bdd on ajoute le user : "+user);
        var newUser = userService.saveUser(user);
        return newUser;
    }

    /**
     * a remove method for users.
     * Even if the mapping starts by delete, it starts implicitly by
     * You can try a request with postman to check if it actually works.
     *
     * it needs to bechanged by pseudo in order to prevent from id non secure leaks
     *
     * @param pseudoUser the id of the user
     * @return a response entity
     */    // TODO : Change implementation : 1 - Check whether the id is in the repo | 2 - If it returns a non null value, delete by id otherwise, return NotFOUND code
    @DeleteMapping("users/delete/{pseudo}")
    public ResponseEntity<String> removeUser(@PathVariable String pseudoUser){
        Objects.requireNonNull(pseudoUser);
        //var userIdLong = Long.parseLong(idUser);
        LOGGER.log(Level.INFO,"on supprime le user avec le pseudo : "+pseudoUser);
        userService.removeByPseudo(pseudoUser);
        /* a necessary rreturn to enable removal
         */
        return ResponseEntity.noContent().build();
    }





    /**
     * this method check/show the logged in users
     * @param locale
     * @param model
     * @return
     */
    @GetMapping("/loggedUsers")
    public String getLoggedUsers(Locale locale, Model model) {
        model.addAttribute("users", activeUserStore.getUsers());
        return "users";
    }

    /*
    necessary to track authentified user
    it doesn't work , it causes issues
     */
    /*
    @PostMapping("/loginPersistent")
    public ResponseEntity<?> login(@RequestBody String userPseudoAndPassword) {
        HashMap<String,String> map;
        System.out.println(" le user and password:  "+userPseudoAndPassword);
        //return ResponseEntity.ok("ok");
        try {
            try {
                map = fromJsonConverter.readValue(userPseudoAndPassword, HashMap.class);
                System.out.println("le map obtenu est : "+map);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format "+e);
            }
//            LOGGER.info("json user's parsing succeed : "+map);
//            //var user = new User(map.get("pseudo"),map.get("email"),map.get("password"));
            System.out.println(" avant le authentication service");
            var token = authenticationService.authenticate(map.get("pseudo"), map.get("password"));
            System.out.println(" après le authentication service");
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", e.getMessage()));
        }
    }*/


    private boolean passwordMatches(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}