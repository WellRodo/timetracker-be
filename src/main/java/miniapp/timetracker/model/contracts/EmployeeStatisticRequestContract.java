package miniapp.timetracker.model.contracts;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EmployeeStatisticRequestContract {
    LocalDate monthDate;
    List<UUID> projectIDs;
    UUID employeeID;
    public EmployeeStatisticRequestContract() {

    }
    public EmployeeStatisticRequestContract(LocalDate monthDate, List<UUID> projectIDs, UUID employeeID) {
        this.monthDate = monthDate;
        this.projectIDs = projectIDs;
        this.employeeID = employeeID;
    }

    public LocalDate getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(LocalDate monthDate) {
        this.monthDate = monthDate;
    }

    public List<UUID> getProjectIDs() {
        return projectIDs;
    }

    public void setProjectIDs(List<UUID> projectIDs) {
        this.projectIDs = projectIDs;
    }

    public UUID getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(UUID employeeID) {
        this.employeeID = employeeID;
    }
}
