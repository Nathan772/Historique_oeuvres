package nate.company.history_work.service;

import nate.company.history_work.siteTools.dtos.MovieDto;
import nate.company.history_work.siteTools.dtos.UserDto;
import nate.company.history_work.siteTools.dtos.WatchedMovieDto;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;

    public static final UserForSession USER_CHOSEN = new UserForSession();

//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String currentPrincipalName = authentication.getName();

    public static class UserForSession {

        private UserDetails userDetails;

        /*
        set the user with its data for the session
         */
        public void setUserDetails(UserDetails userDetails) {
            Objects.requireNonNull(userDetails);
            this.userDetails = userDetails;
        }

        public String getPseudo(){
            return userDetails.getUsername();
        }


    }
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public List<UserDto> getAllUsers() {
        List<UserDto> users = new ArrayList<>();
        userRepository.findAll().forEach(user-> users.add(new UserDto(user)));
        return users;
    }

    public List<WatchedMovieDto>getMoviesWatchedDtoBasedOnUser(String pseudo, String password){
        var user = getUserByPseudo(pseudo);
        /*
        pseudo not found
         */
        if(user.isEmpty()){
            return new ArrayList<>();
        }
        /*
        password not found
         */
        if(!user.get().getPassword().equals(password)){
            return new ArrayList<>();
        }
        return user.get().getWatchMovies().stream().map(movie->new WatchedMovieDto(movie)).toList();

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

    /**
     * this method enables to know the current user who is running
     * @return
     * the user with their data
     */
    public Optional<User> getPrincipal() {
        /*CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();*/
        //Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails){
            return Optional.of(userDetails.getUser());
        }
        return Optional.empty();
    }
}
