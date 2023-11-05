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
    @JoinColumn(name="role_id", nullable = false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Role role;

    public User() {

    }

    public User(UUID id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public User(UUID id, String name, UUID roleId) {
        this.id = id;
        this.name = name;
        this.role = new Role();
        this.role.setId(roleId);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UUID role) {
        this.role = new Role();
        this.role.setId(role);
    }
}