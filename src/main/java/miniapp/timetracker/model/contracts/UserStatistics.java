package miniapp.timetracker.model.contracts;

import miniapp.timetracker.model.Job;
import miniapp.timetracker.model.User;

import java.util.List;

public class UserStatistics {
    private User user;
    private Job job;
    private List<ProjectTime> projectTimeList;

    public UserStatistics() {
    }

    public UserStatistics(User user, Job job, List<ProjectTime> projectTimeList) {
        this.user = user;
        this.job = job;
        this.projectTimeList = projectTimeList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public List<ProjectTime> getProjectTimeList() {
        return projectTimeList;
    }

    public void setProjectTimeList(List<ProjectTime> projectTimeList) {
        this.projectTimeList = projectTimeList;
    }
}
