package miniapp.timetracker.model;

import jakarta.persistence.*;
import lombok.Generated;

import java.util.UUID;

@Entity
@Table(name = "tt_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;

    public User() {}
    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
    public User(UUID id, String name, UUID roleId) {
        this.id = id;
        this.name = name;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}