package nate.company.history_work.service;

import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user-> users.add(user));
        return users;
    }

    public Optional<User> getUserByPseudo(String pseudo){
        var user = userRepository.findByPseudo(pseudo);
        //doesn't exist
        if(user.isEmpty()){
            return Optional.empty();
        }
        //actually exists
        return user;
    }

    public Optional<User> getUserByMail(String email){
        var user = userRepository.findByEmail(email);
        //doesn't exist
        if(user.isEmpty()){
            return Optional.empty();
        }
        //actually exists
        return user;
    }


    public Optional<User> getUserById(long id){
        var user = userRepository.getUserById(id);
        //not found
        if(user.isEmpty()){
            return Optional.empty();
        }
        //found
        return user;
    }

    public User saveUser(User user){
        Objects.requireNonNull(user);
        System.out.println("on sauvegarde : "+user);
        return userRepository.save(user);
    }

    public void removeById(long userId){
        userRepository.deleteById(userId);
    }

    public void removeByPseudo(String pseudo){
        userRepository.removeByPseudo(pseudo);
    }
}
