package miniapp.timetracker.model.contracts;


import miniapp.timetracker.model.UserAuth;

public class UserRegisterContract {

    private UserAuth userAuth;

    public UserRegisterContract() {
    }

    public UserRegisterContract(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }
}
