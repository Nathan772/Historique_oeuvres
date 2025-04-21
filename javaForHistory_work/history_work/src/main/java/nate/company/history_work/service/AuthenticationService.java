package nate.company.history_work.service;

import nate.company.history_work.config.JwtUtil;
import nate.company.history_work.entity.Token;
import nate.company.history_work.siteTools.user.User;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
necessary to track user connected
 */
/*
source:

https://www.baeldung.com/spring-security-authentication-with-a-database
 */
@Service
public class AuthenticationService {

    //@Autowired
    //private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /*public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Failed to authenticate");
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(user.getUsername());
    }

     */

    public Token authenticate(String username, String password) {
        System.out.println(" dans le authenticate");


        /* LE PB EST LAAAAA */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        /* LE PB EST LAAAAA  (FIN)*/
        System.out.println("après le new UserName password authentication token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Failed to authenticate");
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(user.getUsername());
    }

}
