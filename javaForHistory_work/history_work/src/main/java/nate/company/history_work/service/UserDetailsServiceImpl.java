package nate.company.history_work.service;

import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*

source :

https://stackoverflow.com/questions/41770156/spring-add-custom-user-details-to-spring-security-user
+

https://wankhedeshubham.medium.com/spring-boot-security-with-userdetailsservice-and-custom-authentication-provider-3df3a188993f
 */


/*
useless overkill
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByPseudo(pseudo);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }
}*/
