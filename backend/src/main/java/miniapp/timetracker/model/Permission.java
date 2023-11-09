package miniapp.timetracker.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tt_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String url;
}
