package miniapp.timetracker.model.contracts;
import java.util.Date;
import java.util.UUID;

public class TimeSheetContract {
    UUID projectId;
    UUID userId;
    Double workTime;
    String description;
    Date date;
    Boolean isFinished;

    public TimeSheetContract() {
    }

    public TimeSheetContract(UUID projectId, UUID userId, Double workTime, String description, Date date, Boolean isFinished) {
        this.projectId = projectId;
        this.userId = userId;
        this.workTime = workTime;
        this.description = description;
        this.date = date;
        this.isFinished = isFinished;
    }


    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Double workTime) {
        this.workTime = workTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }
}
