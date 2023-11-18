package miniapp.timetracker.model.contracts;

import java.util.List;
import java.util.UUID;

public class FinishTimeSheetsContract {
    public List<UUID> timesheetIDs;

    public FinishTimeSheetsContract() {

    }
    public FinishTimeSheetsContract(List<UUID> timesheetIDs) {
        this.timesheetIDs = timesheetIDs;
    }

    public List<UUID> getTimesheetIDs() {
        return timesheetIDs;
    }

    public void setTimesheetIDs(List<UUID> timesheetIDs) {
        this.timesheetIDs = timesheetIDs;
    }
}
