package miniapp.timetracker.model.contracts;

public interface WeekWorkTimeInterface {
    public String getProjectName();

    public String getWeekDate() ;

    public Double getWorkTime() ;

    public void setProjectName(String projectName);

    public void setWeekDate(String weekDate);

    public void setWorkTime(Double workTime);
}
