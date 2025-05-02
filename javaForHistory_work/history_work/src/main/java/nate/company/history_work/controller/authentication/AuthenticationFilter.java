package nate.company.history_work.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

//public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    protected AuthenticationFilter() {
//        super("/login");
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//
//        if (!request.getMethod().equalsIgnoreCase(HttpMethod.POST.name())) {
//            return null;
//        }
//
//        // extract login and password
//        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(login, new UsernamePasswordAuthenticationToken()));
//
//    }
//
//}
