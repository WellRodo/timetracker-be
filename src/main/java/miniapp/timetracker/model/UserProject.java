package miniapp.timetracker.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    Project project;

    public UserProject() {
    }
    public UserProject(User user, Project project) {
        this.user = user;
        this.project = project;
    }
    public UserProject(int id, User user, Project project) {
        Id = id;
        this.user = user;
        this.project = project;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
