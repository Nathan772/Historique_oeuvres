package nate.company.history_work.controller.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nate.company.history_work.service.AuthenticationService;
import nate.company.history_work.siteTools.authentication.LoginRequest;
import nate.company.history_work.siteTools.movie.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static nate.company.history_work.logger.LoggerInfo.LOGGER;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@CrossOrigin(origins = "*")
public class AuthRestController {

    private final ObjectMapper fromJsonConverter = new ObjectMapper();
    private final AuthenticationService authenticationService;

    public AuthRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/authenticated")
    public String home() {
        return "authentication successful";
    }



    /*
    necessary to track authentified user
     */
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody String pseudoPasswordJson) {
//        System.out.println("on connecte dans SpringSecurity avec un post mapping !! (authRestCOntroller.java)");
//
//        System.out.println(" juste avant la connexion avec token");
////            var token = authenticationService.authenticate(mapPseudoPassword.get("pseudo"), mapPseudoPassword.get("password"));
////            System.out.println(" on a réussi la connexion avec token");
//        return ResponseEntity.ok("Connection worked");
//        //check if movie already exists in db
//        /*
//        Map<String, String> mapPseudoPassword;
//        try {
//            System.out.println(" on parse le tuple de json user-password");
//            mapPseudoPassword = fromJsonConverter.readValue(pseudoPasswordJson, new TypeReference<>() {});
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException("Error : the json received as user doesn't respect the json format "+e);
//        }
//        LOGGER.info("json user's parsing succeed : "+mapPseudoPassword);
//        try {
//            System.out.println(" on lance l'authetification request");
//            UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(mapPseudoPassword.get("pseudo"),
//                    mapPseudoPassword.get("password"));
//
//            System.out.println(" juste avant la connexion avec token");
////            var token = authenticationService.authenticate(mapPseudoPassword.get("pseudo"), mapPseudoPassword.get("password"));
////            System.out.println(" on a réussi la connexion avec token");
//            return ResponseEntity.ok("Connection worked");
//        } catch (BadCredentialsException e) {
//            System.out.println("on a échoué la connexion avec token");
//            return ResponseEntity.ok("error identification failed");
//        }
//
//
////        UserDetails userDetails = userDetailsService.loadUserByUsername(userLogin);
////
////        UsernamePasswordAuthenticationToken authToken =
////                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////
////        Authentication authentication =
////                SecurityContextHolder.getContext().getAuthentication();
////        String currentPrincipalName = authentication.getName();
////
////        System.out.println("On est censé avoir logged le current user de façon persistante, donc si on regarde le current user on a :" +
////                " "+currentPrincipalName);*/
//    }



}
