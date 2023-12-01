package miniapp.timetracker.model.contracts;

import miniapp.timetracker.model.User;

import java.util.UUID;

public class JwtResponse {
    private String token;
    private UUID user;



    public JwtResponse() {
    }

    public JwtResponse(String token, UUID user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
