package miniapp.timetracker.model.contracts;

import jakarta.persistence.Entity;

public class JobTimeOnProject {
    String job;
    Double time;

    public JobTimeOnProject() {
    }

    public JobTimeOnProject(String job, Double time) {
        this.job = job;
        this.time = time;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
