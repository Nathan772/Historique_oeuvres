package nate.company.history_work.controller.authentication;

import org.springframework.security.core.Authentication;

public interface IAuthenticationInterface {
    Authentication getAuthentication();
}
