package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;

import java.util.Date;
import java.util.UUID;

public interface TimeSheetService {
    public TimeSheet[] GetTimeSheetFromPeriodAndProject(Date startDate, Date endDate, UUID projectId);

    public TimeSheet SaveTimeSheet(TimeSheet tm);

    public TimeSheet[] GetTimeSheetsByDate(Date date);
}
