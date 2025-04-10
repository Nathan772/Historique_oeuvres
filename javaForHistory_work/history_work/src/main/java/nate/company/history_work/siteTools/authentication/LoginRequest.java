package nate.company.history_work.siteTools.authentication;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
