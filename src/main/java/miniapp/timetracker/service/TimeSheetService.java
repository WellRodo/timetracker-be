package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.TimeSheetContract;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TimeSheetService {
    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(Date startDate, Date endDate, UUID projectId);

    public TimeSheet SaveTimeSheet(TimeSheetContract timeSheet);

    public List<TimeSheet> GetTimeSheetsByDate(Date date);
}
