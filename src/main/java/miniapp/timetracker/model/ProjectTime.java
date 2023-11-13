package miniapp.timetracker.model;

public class ProjectTime {

    String projectName;
    Double time;

    public ProjectTime() {
    }

    public ProjectTime(String projectName, Double time) {
        this.projectName = projectName;
        this.time = time;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
