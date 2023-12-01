package miniapp.timetracker.model.contracts;

import miniapp.timetracker.model.User;

import java.util.UUID;

public class JwtResponse {
    private String token;
    private UUID user;
    private Boolean isManager;



    public JwtResponse() {
    }

    public JwtResponse(String token, UUID user, Boolean isManager) {
        this.token = token;
        this.user = user;
        this.isManager = isManager;
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

    public Boolean getManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }
}
