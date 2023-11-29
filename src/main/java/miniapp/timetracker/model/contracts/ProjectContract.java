package miniapp.timetracker.model.contracts;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;

import java.util.List;

public class ProjectContract {
    Project project;
    List<User> userList;

    public ProjectContract() {
    }

    public ProjectContract(Project project, List<User> userList) {
        this.project = project;
        this.userList = userList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
