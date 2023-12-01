package miniapp.timetracker.model.contracts;


import miniapp.timetracker.model.User;

import java.util.UUID;

public class UserContract {

    private User user;

    public UserContract() {
    }

    public UserContract(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
