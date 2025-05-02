package nate.company.history_work.controller.user;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static nate.company.history_work.service.UserService.USER_CHOSEN;
import nate.company.history_work.configuration.SecurityConfig;
import nate.company.history_work.handle_connection_spring.ActiveUserStore;
import nate.company.history_work.service.*;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.main.java.nate.company.history_work.siteTools.user.UserCategory;

import java.security.Principal;
import java.util.*;
import java.util.logging.Level;

import static nate.company.history_work.controller.ControllerResources.FROMJSONCONVERTER;
import static nate.company.history_work.logger.LoggerInfo.LOGGER;

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

    @Autowired
    private final AuthenticationManager authenticationManager;

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



    /**
     * constructor selfmade
     *
     * @param userService the service that enables to handle repositories and treatment
     */
    @Autowired
    public UserController(UserService userService, MovieService movieService, AuthenticationService authenticationService,
                          MyUserDetailsService userDetailsService, WatchMovieService watchedMovieService,
                          AuthenticationManager authenticationManager){
        Objects.requireNonNull(userService);
        Objects.requireNonNull(userDetailsService);
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.movieService = movieService;
        this.authenticationService= authenticationService;
        this.watchedMovieService = watchedMovieService;
        this.authenticationManager = authenticationManager;

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
     * this method retrieve a specific user if exists in database based only on pseudo
     * or email.
     * it let the front end choose if the user is valid based on the comparison
     * between the data related to this user data and the actual data.
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

    @GetMapping("/validAuthentication")
    @ResponseBody
    public ResponseEntity<Boolean>validAuthenticationUser(@RequestParam(name="pseudo") String userPseudo, @RequestParam(name="password") String password){
        System.out.println("on va chercher un user par pseudo");
        var userByPseudo = userService.getUserByPseudo(userPseudo);
        if(userByPseudo.isPresent()){
            //converter to compare raw password to encoded password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);

            System.out.println("le password en version encodé pour la récupération du compte est : "+password);
            if(encoder.matches(password,userByPseudo.get().getPassword())){
                System.out.println("bon mot dee passe pour le user : "+userPseudo);
                /*
                connection with spring security
                 */
                System.out.println("on essaye d'authentifier le user 1 ");
                //crypt the password
//                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
//                String passwordCrypted = encoder.encode(password);

                //set user and password in db
                UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userPseudo,
                        userByPseudo.get().getPassword());
//                Authentication authenticationRequest =
//                        UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
                System.out.println("on essaye d'authentifier le user 2 ");
                Authentication authenticationResponse =
                        this.authenticationManager.authenticate(authReq);
                System.out.println("le user a été authentifié correctmeent !! 3");
                SecurityContext sc = SecurityContextHolder.getContext();
                System.out.println("le user a été authentifié correctmeent !! 4");
                sc.setAuthentication(authenticationResponse);
                USER_CHOSEN.setUserDetails((UserDetails) sc.getAuthentication().getPrincipal());
                System.out.println("le user a été authentifié correctmeent !! 5 il est enregistré comme : "+USER_CHOSEN.getPseudo());
                ResponseEntity.ok(true);
            }
            System.out.println("mauvais mot de passe pour le user : "+userPseudo);
            //wrong password
            return ResponseEntity.ok(false);
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
            System.out.println("le user "+userByEmail.get()+" existe déjà");
            return ResponseEntity.ok(true);
        }
        var userByPseudo = userService.getUserByPseudo(userPseudo);
        if(userByPseudo.isPresent()){
            System.out.println("le user "+userByPseudo.get()+" existe déjà");
            return ResponseEntity.ok(true);
        }


        LOGGER.log(Level.INFO, " le user n'existe pas en bdd");
        System.out.println("le user n' existe pas en bdd");

        //handle spring security authentication :
        /*
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user, pass);
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);*/

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
        System.out.println("on entre dans adduser");
        Objects.requireNonNull(userJson);
        /* https://stackoverflow.com/questions/7246157/how-to-parse-a-json-string-to-an-array-using-jackson : convert*/
        // source : https://stackoverflow.com/questions/29313687/trying-to-use-spring-boot-rest-to-read-json-string-from-post

        System.out.println("le json de sauvegarde du user ressemble à ça en string : "+userJson);
        // Convert JSON string to Map
        Map<String, String> map;
        try {
            map = FROMJSONCONVERTER.readValue(userJson, new TypeReference<>() {});
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
        System.out.println("on arrive à la fin dans adduser");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        //change password to encoded password
        //encoder.encode(user.getPassword()
        //user.setPassword();

        var userCopy = new User(user.getPseudo(), user.getEmail(), user.getPassword());

        //encode password
        userCopy.setPassword(encoder.encode(userCopy.getPassword()));
        //save
        var newUser = userService.saveUser(userCopy);



        //perform a spring session for a user session
        //use the non encoded password for the test
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user.getPseudo(),
                user.getPassword());
        System.out.println("on essaye d'authentifier après avoir ajouté le user 2 ");
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authReq);
        System.out.println("on essaye d'authentifier après avoir ajouté le user !! 3");
        SecurityContext sc = SecurityContextHolder.getContext();
        System.out.println("on essaye d'authentifier après avoir ajouté le user !! 4");
        sc.setAuthentication(authenticationResponse);
        USER_CHOSEN.setUserDetails((UserDetails) sc.getAuthentication().getPrincipal());
        System.out.println("le user a été authentifié correctmeent !! 5 il est enregistré comme : "+USER_CHOSEN.getPseudo());
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


    /*
    get the current connected user
     */
//    @GetMapping("/username")
//    public String currentUserName(Principal principal) {
//        return principal.getName();
//    }





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