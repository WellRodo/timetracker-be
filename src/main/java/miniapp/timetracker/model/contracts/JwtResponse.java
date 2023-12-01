package miniapp.timetracker.model.contracts;

import miniapp.timetracker.model.User;

import java.util.UUID;

public class JwtResponse {
    private String token;
    private User user;



    public JwtResponse() {
    }

    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
