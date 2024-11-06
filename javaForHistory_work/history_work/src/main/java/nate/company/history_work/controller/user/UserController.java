package nate.company.history_work.controller.user;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;

@RestController

/*
permet de résoudre le problème de
"No Access-control-allow-origin"
cors policy error
 */
@CrossOrigin("*")
public class UserController {

    /*
   necessary constructor for REST API
     */
    //standard constructors

    private final UserRepository userRepository;

    /*
    constructeur créé par moi-même qui se remplit avec un paramètre de type UserRepo...
    C'est Spring qui gérera lui-même l'ajout/la création de l'argument lors de l'appel
     */
    public UserController(UserRepository userRepository){
        Objects.requireNonNull(userRepository);
        this.userRepository = userRepository;
    }

    /**
     * this method retrieves all the users from the database
     * (linked to "findAll" from user.service)
     * @return
     */
    //@RequestMapping("/users")
    @GetMapping("/users")
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }
    /**
     * this method retrieve a specific user if exists in database
     * @param userId
     * user's id
     * @param userPseudo
     * pseudo of the user
     * @param email
     * the email from this user
     * @param password
     * the password for this account
     * the user you want to retrieve
     * @return
     * the user searched if found, else null
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
    /*@GetMapping("/userSearch")
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam(name="id") String userId, @RequestParam(name="pseudo") String userPseudo,
                                     @RequestParam(name="email") String email, @RequestParam(name="password") String password
                                     ){
        System.out.println("on entre bien dans la méthode getUser de Java");
        var allUsers = userRepository.findAll();
        for(var user:allUsers){
            System.out.println("L'id du user : "+user.getId());
            //user found
            //pseudo already used or email already used
            if(user.getPseudo().equals(userPseudo) || user.getEmail().equals(email) ) {
                System.out.println("Le user a bien été trouvé : ");
                var userForAngular = new User(user.getId(),user.getPseudo(),user.getEmail(),user.getPassword(),user.getCategory() );
                return ResponseEntity.ok(user);

            }
        }
        //user not found (not necessarily an error
        //depending on the usage)

        System.out.println("le user n'a pas été trouvé ");
        return ResponseEntity.ok().build();
    }*/

    /**
     * Retrieve user from data based, based on their pseudo or their email
     * @param userSearched
     * the user few information (email/pseudo)
     * @return
     * the user complete data
     */

    @PostMapping("/userSearch")
    public ResponseEntity<?> getUser(@RequestBody User userSearched){
        var userPseudo = userSearched.getPseudo();
        var email = userSearched.getEmail();
        //System.out.println("on entre bien dans la méthode getUser de Java");
        var allUsers = userRepository.findAll();
        for(var user:allUsers){
            //System.out.println("L'id du user : "+user.getId());
            LOGGER.log(Level.INFO,"L'id du user recherché user : "+user.getId());
            //user found
            //pseudo already used or email already used
            if(user.getPseudo().equals(userPseudo) || user.getEmail().equals(email) ) {
                //System.out.println("Le user a bien été trouvé : ");
                LOGGER.log(Level.INFO,"Le user "+userPseudo+" a bien été trouvé");
                var userForAngular = new User(user.getId(),user.getPseudo(),user.getEmail(),user.getPassword(),user.getCategory() );
                return ResponseEntity.ok(user);

            }
        }
        //user not found (not necessarily an error
        //depending on the usage)

        LOGGER.log(Level.INFO,"Le user n'a pas été trouvé");
        return ResponseEntity.ok().build();
    }







    /**
     *
     * Retrieve a user based on their pseudo or email.
     * @param user
     * the you user you want to retrieve
     * @return
     * the user object.
     */
    //@RequestMapping("/users")
    //@GetMapping("/user")
    /*public User getUser(@RequestBody User user){

        userRepository.find

    }*/









    /**
     *
     * the "add user" add a new user in the data base.
     *
     * @param user
     */
    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        LOGGER.log(Level.INFO, " on ajoute le user : "+user);
        /*by default every user is average
        if the add been add through the website
         */
        user.setCategory("average");
        userRepository.save(user);
        User savedUser = null;
        //retrieve actual id in database
        for(var userInDB:userRepository.findAll()){
            if(userInDB.getPseudo().equals(user.getPseudo())){
                LOGGER.log(Level.INFO,"user copié en db");
                savedUser = userInDB;
            }
        }
        if(savedUser == null) savedUser = user;
        LOGGER.log(Level.INFO,"l'id du user sauvegardé est : "+savedUser.getId());
        return savedUser;
    }

    /**
     * a remove method for users.
     * Even if the mapping starts by delete, it starts implicitly by
     * You can try a request with postman to check if it actually works.
     * @param idUser
     * @return
     */

    @DeleteMapping("users/delete/{idUser}")
    public ResponseEntity<String> removeUser(@PathVariable String idUser){
        var userIdLong = Long.parseLong(idUser);
        LOGGER.log(Level.INFO,"on supprime le user avec l'id : "+userIdLong);
        userRepository.deleteById(userIdLong);
        /* a necessary rreturn to enable removal
         */
        return ResponseEntity.noContent().build();
    }


}
