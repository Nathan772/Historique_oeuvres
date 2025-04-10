package nate.company.history_work.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nate.company.history_work.service.CustomUserDetails;
import nate.company.history_work.service.UserService;
import nate.company.history_work.siteTools.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

/*

this class is also necessary to generate token unique access

 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, UserService userService, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("header = " + header);

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        try {
            System.out.println("token = "+ token);
            String username = jwtUtil.extractUsername(token);
            System.out.println("username = " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var user = userRepository.findByPseudo(username);
                if (user.isEmpty()) {
                    throw new UsernameNotFoundException(username);
                }
                var userDetails = new CustomUserDetails(user.get());

                if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                    System.out.println("validate token");
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired JWT token");
            return;
        }

        chain.doFilter(request, response);
    }
}
