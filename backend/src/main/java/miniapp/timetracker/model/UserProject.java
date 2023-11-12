package miniapp.timetracker.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tt_user_project")
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id")
    Project project;

}
