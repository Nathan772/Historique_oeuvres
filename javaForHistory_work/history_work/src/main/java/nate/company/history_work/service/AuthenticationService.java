package nate.company.history_work.service;

import nate.company.history_work.config.JwtUtil;
import nate.company.history_work.entity.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/*
necessary to track user connected
 */
@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Failed to authenticate");
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(user.getUsername());
    }
}
