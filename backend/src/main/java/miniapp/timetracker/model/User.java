package miniapp.timetracker.model;

import jakarta.persistence.*;
import lombok.Generated;

@Entity
@Table(name = "tt_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @JoinColumn(name="role_id", nullable = false)
    @ManyToOne(fetch=FetchType.EAGER)
    private Role role;

    public User() {

    }

    public User(Integer id, String name, Integer roleId) {
        this.id = id;
        this.name = name;
        this.role = new Role();
        this.role.setId(roleId);
    }

        public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Integer role) {
        this.role = new Role();
        this.role.setId(role);
    }
}