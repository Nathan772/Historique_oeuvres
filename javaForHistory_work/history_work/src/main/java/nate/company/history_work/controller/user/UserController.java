package nate.company.history_work.controller.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.thoughtworks.qdox.parser.impl.Parser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nate.company.history_work.handle_connection_spring.ActiveUserStore;
import nate.company.history_work.service.AuthenticationService;
import nate.company.history_work.service.MovieService;
import nate.company.history_work.service.MyUserDetailsService;
import nate.company.history_work.service.UserService;
import nate.company.history_work.siteTools.authentication.LoginRequest;
import nate.company.history_work.siteTools.dtos.MovieDto;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
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
                          MyUserDetailsService userDetailsService){
        Objects.requireNonNull(userService);
        Objects.requireNonNull(userDetailsService);
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.movieService = movieService;
        this.authenticationService= authenticationService;
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
            if (entry.getValue() instanceof Map) {
                System.out.println(entry.getKey() + " = ");
                nestedMap = (HashMap<String, String>) entry.getValue();
                for (Map.Entry<String, String> nestedEntry : nestedMap.entrySet()) {
                    System.out.println("    " + nestedEntry.getKey() + " = " + nestedEntry.getValue());
                    copyNestedEntries.put(nestedEntry.getKey(), nestedEntry.getValue());
                }
            } else if (entry.getValue() instanceof List) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            } else {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
        return  copyNestedEntries;
    }
    /**
     *
     * a remove method for an association between a movie and a user.
     *
     *
     * the user who watched the movie
     * @param imdbIDUserJson
     * the movie watch with its imdbID.
     *
     * the movie and the user who watches this movie.
     *
     * @return
     * a response that precises if everything end up properly.
     */
    //overpowerful deprecated

    @DeleteMapping("/user/movie/remove")
    public ResponseEntity<String> removeMovieFromList(@RequestBody String imdbIDUserJson){

        //System.out.println("on a trouvé le user principal");
        //user is connected
        // doesn't work use another approach
        //var currentUser = userService.getPrincipal();
        var nestedMap = parseComplexJson(imdbIDUserJson);
        var userOpt = userService.getUserByPseudo(nestedMap.get("pseudo"));
        //user doesn't exists
        if(userOpt.isEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists
        //but false password
        if(userOpt.get().getPassword().equals(nestedMap.get("password"))){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Movie());
        }

        //user exists and good password

        //use the actual user
        var actualUser = userOpt.get();//currentUser.get();

        //check if movie already exists in db
        Movie movie;
        try {
            movie = new Movie(nestedMap.get("title"), Integer.parseInt(nestedMap.get("yearOfRelease")), nestedMap.get("imdbID"), nestedMap.get("director"), nestedMap.get("poster"));
        }
        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error bad movie format");
        }
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        if(movieAlreadyExistsOpt.isPresent()){
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            movieChosen.removeUserFromWatcher(actualUser);
            actualUser.removeFromWatchedMovie(movieChosen);
            //makes them persistent in db
            movieService.saveMovie(movieChosen);
            userService.saveUser(actualUser);
            return ResponseEntity.ok("");
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
    public ResponseEntity<MovieDto> addMovie(@RequestBody String userMovieJson){
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
        var nestedMap = parseComplexJson(userMovieJson);
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
            movie = new Movie(nestedMap.get("title"), Integer.parseInt(nestedMap.get("yearOfRelease")), nestedMap.get("imdbID"), nestedMap.get("director"), nestedMap.get("poster"));
        }
        catch(Exception e){
            //inconsistent movie fields
            LOGGER.log(Level.INFO,"Error : the json received as movie doesn't respect the json format");
            System.out.println("erreur le film au titre: "+nestedMap.get("title")+" n'existe pas ");
            //EMPTY MOVIE RETURNED IF Not connected
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MovieDto(new Movie()));
        }
        var movieAlreadyExistsOpt = movieService.getMovieByImdb(movie.getImdbID());

        if(movieAlreadyExistsOpt.isPresent()){
            //the movie is already in the data base don't need to recreate with the same imdb
            var movieChosen = movieAlreadyExistsOpt.get();
            movieChosen.addIsWatchedBy(actualUser);
            actualUser.addWatchedMovie(movieChosen);
            //makes them persistent in db
            movieService.saveMovie(movieChosen);
            userService.saveUser(actualUser);
            return ResponseEntity.ok(new MovieDto(movieChosen));
        }

        //it's a new movie that wasn't in db
        //we save it in db
        movie.addIsWatchedBy(actualUser);
        actualUser.addWatchedMovie(movie);
        movieService.saveMovie(movie);
        userService.saveUser(actualUser);
        LOGGER.log(Level.INFO, " on sauvegardé le film dans la liste du user : "+actualUser);
        return ResponseEntity.ok(new MovieDto(movie));
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
        return ResponseEntity.ok(userOpt.get().getWatchMovies().stream().map(movie->new MovieDto(movie)).toList());
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