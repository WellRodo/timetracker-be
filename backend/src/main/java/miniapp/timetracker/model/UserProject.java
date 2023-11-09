package miniapp.timetracker.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tt_user_project")
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
}
