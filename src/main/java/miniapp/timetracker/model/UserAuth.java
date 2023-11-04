package miniapp.timetracker.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tt_user_auth")
public class UserAuth {
    private String Password;
    private String Login;
    @Id
    @JoinColumn(name="user_id")
    @OneToOne(fetch= FetchType.EAGER)
    private User User;
}
