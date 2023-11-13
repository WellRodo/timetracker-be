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

    @JoinColumn(name="job_id")
    @ManyToOne(fetch= FetchType.EAGER)
    private Job job;
    public User() {}

    public User(UUID id, String name, Job job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}