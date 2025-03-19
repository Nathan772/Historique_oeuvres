package nate.company.history_work.controller.user;
import nate.company.history_work.service.MovieService;
import nate.company.history_work.service.UserService;
import nate.company.history_work.siteTools.movie.Movie;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;


/**
 * User controller.
 */
@RestController
@CrossOrigin("*")
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
    @GetMapping("/userSearch")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam(name="id") String userId, @RequestParam(name="pseudo") String userPseudo,
                                     @RequestParam(name="email") String email, @RequestParam(name="password") String password
                                     ){
        var userByEmail = userService.getUserByMail(email);

        //the user already exists
        if(userByEmail.isPresent()){
            return ResponseEntity.ok(userByEmail.get());
        }
        var userByPseudo = userService.getUserByPseudo(userPseudo);
        if(userByPseudo.isPresent()){
            return ResponseEntity.ok(userByPseudo.get());
        }

        //they don't exist
        return ResponseEntity.ok().build();
    }



    /**
     *
     * Retrieve a user based on their pseudo or email.
     * @param user the you user you want to retrieve
     * @return the user object.
     */
    //@RequestMapping("/users")
    //@GetMapping("/user")
    /*public User getUser(@RequestBody User user){

        userRepository.find

    }*/


    /**
     * Adds a new user in the database.
     *
     * @param user the request body that contains data for a new user to add
     * @return the user insterd in the database
     *
     *
     * it needs to be changed by json object that you will parse later
     */
    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        Objects.requireNonNull(user);
//        LOGGER.log(Level.INFO, " on ajoute le user : "+user);
//        /*by default every user is average
//        if the add been add through the website
//         */
//        user.setCategory("average");
//        userRepository.save(user);
//        User savedUser = null;
//        //retrieve actual id in database
//        for(var userInDB:userRepository.findAll()){
//            if(userInDB.getPseudo().equals(user.getPseudo())){
//                LOGGER.log(Level.INFO,"user copié en db");
//                savedUser = userInDB;
//            }
//        }
//        if(savedUser == null) savedUser = user;
//        LOGGER.log(Level.INFO,"l'id du user sauvegardé est : "+savedUser.getId());
//        return savedUser;
        user.setCategory("average");
        var userByPseudo =  userService.getUserByPseudo(user.getPseudo());
        var userByMail = userService.getUserByMail(user.getEmail());

        //already exists
        if(userByPseudo.isPresent()){
            return userByPseudo.get();
        }
        if(userByMail.isPresent()){
            return userByMail.get();
        }
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
     * @param idUser the id of the user
     * @return a response entity
     */    // TODO : Change implementation : 1 - Check whether the id is in the repo | 2 - If it returns a non null value, delete by id otherwise, return NotFOUND code
    @DeleteMapping("users/delete/{idUser}")
    public ResponseEntity<String> removeUser(@PathVariable String idUser){
        var userIdLong = Long.parseLong(idUser);
        LOGGER.log(Level.INFO,"on supprime le user avec l'id : "+userIdLong);
        userService.removeById(userIdLong);
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
    public Set<Movie> getUserMovies(@RequestParam(name="id") long userId){
        var user = userService.getUserById(userId);
        if(user.isEmpty()){
            return new HashSet<>();
        }
       return  user.get().getWatchMovies();
    }

    /**
     *
     * a remove method for an association between a movie and a user.
     *
     * @param id
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

    @DeleteMapping("/user/movie/remove/{id}/{imdbID}")
    public ResponseEntity<String> removeMovieFromList(@PathVariable String id, @PathVariable String imdbID){

        var userOpt = userService.getUserById(Long.parseLong(id));
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