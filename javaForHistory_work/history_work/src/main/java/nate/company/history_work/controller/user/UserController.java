package nate.company.history_work.controller.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.swagger.v3.core.util.Json;
import nate.company.history_work.service.MovieService;
import nate.company.history_work.service.UserService;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserCategory;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;


/**
 * User controller.
 */
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

    private final UserService userService;

    private final MovieService movieService;

    private final ObjectWriter jsonConverter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final ObjectMapper fromJsonConverter = new ObjectMapper();

    /**
     * constructor selfmade
     *
     * @param userService the service that enables to handle repositories and treatment
     */
    @Autowired
    public UserController(UserService userService, MovieService movieService){
        Objects.requireNonNull(userService);
        this.userService = userService;
        this.movieService = movieService;
    }

    /**
     * this method retrieves all the users from the database
     * (linked to "findAll" from user.service)
     *
     * @return the list containing all the users
     */
    //@RequestMapping("/users")
    @GetMapping("/users")
    public List<User> getUsers(){
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
    // rather post than get mapping in order to hide user data
    //overkill
    @GetMapping("/userSearch")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="email") String email){
        var userByEmail = userService.getUserByMail(email);

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
     * @param userJsonAndmovieJson
     * the user that will have the movie in their list as json. and
     * the movie that will be added as json.
     *
     * @return the retrieved movie
     */
    //Requestbody can be used only once,
    //thereby you use a wrapper class or two request param
    //you cannot
    @PostMapping("/user/movie/add")
    public ResponseEntity<?> addMovie(@RequestBody String userJsonAndmovieJson){
        //regex searched : {({.*})\,({.*})}
        LOGGER.log(Level.INFO, " on ajoute le film : "+userJsonAndmovieJson);
        //save the movie in database
        Movie movieSaved;
        //check if movie already exists first :

        Objects.requireNonNull(userJsonAndmovieJson);
        /* https://stackoverflow.com/questions/7246157/how-to-parse-a-json-string-to-an-array-using-jackson : convert*/
        // source : https://stackoverflow.com/questions/29313687/trying-to-use-spring-boot-rest-to-read-json-string-from-post

        // Convert JSON string to Map
       // Map<String, String> mapMovieAndJson;

        // Convert JSON string to Map
        Map<String, Object> mapUser;

        //convert JSON to Map
        Map<String, String> mapMovie;

        // String to be scanned to find the pattern.
        String line = userJsonAndmovieJson;
        String pattern = "\\{(\\{.*\\})\\,(\\{.*\\})\\}";

        // Create a Pattern object
        Pattern regex = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher matcher = regex.matcher(line);

        if (!matcher.find( )) {
            //the string doesn't match the pattern expected for movie and user
            //as jsons
            return ResponseEntity.badRequest().build();
        }

        System.out.println("le groupe 1 récupéré est : "+matcher.group(1));
        //retrieve user's data
        try {
            mapUser = fromJsonConverter.readValue(matcher.group(1), new TypeReference<HashMap<String,Object>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format");
        }

        System.out.println("l'élément récupéré est : "+mapUser);

        //retrieve movie's data
        try {
            mapMovie = fromJsonConverter.readValue(matcher.group(2), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error : the json receveid as movie doesn't respect the json format");
        }
//
        LOGGER.info("json user's and movie's parsing succeed : "+mapUser);
        var user = new User(mapUser.get("pseudo").toString(),mapUser.get("email").toString(),mapUser.get("password").toString());
        var movie = new Movie(mapMovie.get("title"), Integer.parseInt(mapMovie.get("yearOfRelease")), mapMovie.get("imdbID"), mapMovie.get("director"));
        user.setCategory(UserCategory.AVERAGE);
        var userByPseudo =  userService.getUserByPseudo(user.getPseudo());



        //user not found
        if(userByPseudo.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        //use the actual user
        var actualUser = userByPseudo.get();
        //check if movie already exists in db
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getimdbID());

        if(movieAlreadyExistsOpt.isPresent()){
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            movieChosen.addIsWatchedBy(actualUser);
            actualUser.addWatchedMovie(movieChosen);
            //makes them persistent in db
            movieService.saveMovie(movieChosen);
            userService.saveUser(actualUser);
            return ResponseEntity.ok(movieChosen);
        }

        //it's a new movie that wasn't in db
        //we save it in db
        movie.addIsWatchedBy(actualUser);
        actualUser.addWatchedMovie(movie);
        movieService.saveMovie(movie);
        userService.saveUser(actualUser);
        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);
        return ResponseEntity.ok("it works");
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
            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format");
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
     * This method retrieves all the movies watched by a user from the database
     *
     * it needs to bechanged by pseudo in order to prevent from id non secure leaks
     *
     * @param userId id of the user
     * @return the list of movie possessed by the user
     */
    @GetMapping("user/movie")
    public List<Movie> getUserMovies(@RequestParam(name="id") long userId){
        var user = userService.getUserById(userId);
        if(user.isEmpty()){
            return new ArrayList<>();
        }
       return  user.get().getWatchMovies();
    }

    /**
     *
     * a remove method for an association between a movie and a user.
     *
     * @param pseudo
     * the user who watched the movie
     * @param imdbID
     * the movie watch with its imdbID.
     *
     * the movie and the user who watches this movie.
     *
     * @return
     * a response that precises if everything end up properly.
     */
    //overpowerful deprecated

    @DeleteMapping("/user/movie/remove/{pseudo}/{imdbID}")
    public ResponseEntity<String> removeMovieFromList(@PathVariable String pseudo, @PathVariable String imdbID){

        var userOpt = userService.getUserByPseudo(pseudo);
        var movieOpt = movieService.getMovieByImdb(imdbID);
        if(userOpt.isEmpty()){
            LOGGER.log(Level.INFO, "Error : the user that was supposed to be lose the movie from their list hasn't been found !!");
            return ResponseEntity.notFound().build();
        }
        if(movieOpt.isEmpty()){
            LOGGER.log(Level.INFO, "Error : the movie that was supposed to be removed hasn't been found !!");
            return ResponseEntity.notFound().build();
        }
        var user = userOpt.get();
        var movie = movieOpt.get();
        //no longer a watcher
        user.removeFromWatchedMovie(movie);
        movie.removeUserFromWatcher(user);

        //save update to make it persistent

        movieService.saveMovie(movie);
        userService.saveUser(user);
        LOGGER.log(Level.INFO, "Success : the movie has been correctly removed !!");
        return ResponseEntity.noContent().build();
    }



























    /**
     * Retrieve user from data based, based on their pseudo or their email.
     * Actually, it can be used to know if the identifier (pseudo and email) can
     * be used to create a unique User.
     *
     * @param userSearched the user few information (email/pseudo)
     * @return the user complete data
     */
    //deprecated overpowerful
//    @PostMapping("/userSearch")
//    public ResponseEntity<?> doesIdentifierExist(@RequestBody User userSearched){
//        var userPseudo = userSearched.getPseudo();
//        var email = userSearched.getEmail();
//        //System.out.println("on entre bien dans la méthode getUser de Java");
//        var allUsers = userRepository.findAll();
//        for(var user:allUsers){
//            //System.out.println("L'id du user : "+user.getId());
//            LOGGER.log(Level.INFO,"L'id du user recherché user : "+user.getId());
//            //user found
//            //pseudo already used or email already used
//            if(user.getPseudo().equals(userPseudo) || user.getEmail().equals(email) ) {
//                //System.out.println("Le user a bien été trouvé : ");
//                LOGGER.log(Level.INFO,"Le user "+userPseudo+" a bien été trouvé");
//                var userForAngular = new User(user.getId(),user.getPseudo(),user.getEmail(),user.getPassword(),user.getCategory() );
//                return ResponseEntity.ok(user);
//
//            }
//        }
//        //user not found (not necessarily an error
//        //depending on the usage)
//
//        LOGGER.log(Level.INFO,"Le user n'a pas été trouvé");
//        return ResponseEntity.notFound().build();
//    }

}