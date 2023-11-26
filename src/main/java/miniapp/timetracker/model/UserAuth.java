package miniapp.timetracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tt_user_auth")
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private Boolean managerRole;
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public UserAuth() {
    }

    public UserAuth(UUID id, String password, String login, Boolean managerRole, User user) {
        this.id = id;
        this.password = password;
        this.login = login;
        this.managerRole = managerRole;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getManagerRole() {
        return managerRole;
    }

    public void setManagerRole(Boolean managerRole) {
        this.managerRole = managerRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
