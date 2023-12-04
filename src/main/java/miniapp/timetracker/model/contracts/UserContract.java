package miniapp.timetracker.model.contracts;


import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserAuth;

import java.util.UUID;

public class UserContract {

    private UserAuth userAuth;

    public UserContract() {
    }

    public UserContract(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }
}
