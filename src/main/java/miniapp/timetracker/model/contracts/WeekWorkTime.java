package miniapp.timetracker.model.contracts;


import java.time.LocalDate;


public class WeekWorkTime {
    String projectName;
    String weekDate;
    Double workTime;

    public WeekWorkTime() {
    }

    public WeekWorkTime(String projectName, String weekDate, Double workTime) {
        this.projectName = projectName;
        this.weekDate = weekDate;
        this.workTime = workTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }
}
