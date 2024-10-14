package nate.company.history_work.controller;
/*
import nate.company.history_work.siteTools.User;
import nate.company.history_work.siteTools.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
*/
import nate.company.history_work.siteTools.User;
import nate.company.history_work.siteTools.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void addUser(@RequestBody User user){
        System.out.println(" on ajoute le user : "+user);
        userRepository.save(user);
    }

    /**
     * a remove method for users.
     * Even if the mapping starts by delete, it starts implicitly by
     * You can try a request with postman to check if it actually works.
     * @param id
     * @return
     */

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> removeUser(@PathVariable String id){
        var userIdLong = Long.parseLong(id);
        //var userIdLong = user.getId();
        System.out.println("on supprime le user avec l'id : "+userIdLong);
        userRepository.deleteById(userIdLong);
        /* a necessary rreturn to enable removal
         */
        return ResponseEntity.noContent().build();
    }


}
