package nate.company.history_work.service;

import nate.company.history_work.siteTools.user.MyUserPrincipal;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*

source :

https://www.baeldung.com/spring-security-authentication-with-a-database
 */
@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByPseudo(username);
        if (user.isEmpty()) {
            System.out.println("user could not be found in MyUserDetailsService");
            throw new UsernameNotFoundException(username);
        }
        System.out.println("user found in MyUserDetailsService");
        return new MyUserPrincipal(user.get());
    }
}

