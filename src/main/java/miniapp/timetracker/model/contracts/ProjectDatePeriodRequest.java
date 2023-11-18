package miniapp.timetracker.model.contracts;


import java.time.LocalDate;
import java.util.UUID;

public class ProjectDatePeriodRequest {
    LocalDate dateStart;
    LocalDate dateEnd;
    UUID projectId;

    public ProjectDatePeriodRequest() {
    }

    public ProjectDatePeriodRequest(LocalDate dateStart, LocalDate dateEnd, UUID projectId) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.projectId = projectId;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
