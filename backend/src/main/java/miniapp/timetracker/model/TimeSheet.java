package miniapp.timetracker.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tt_time_sheet")
public class TimeSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    UUID projectId;
    UUID userId;
    Time worktime;
    String description;
    Date date;
    Boolean isFinished;

    public TimeSheet() {
    }

    public TimeSheet(UUID id, UUID projectId, UUID userId, Time worktime, String description, Date date, Boolean isFinished) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.worktime = worktime;
        this.description = description;
        this.date = date;
        this.isFinished = isFinished;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Time getWorktime() {
        return worktime;
    }

    public void setWorktime(Time worktime) {
        this.worktime = worktime;
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
