package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.TimeSheetContract;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TimeSheetService {
    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate, UUID projectId);

    public List<TimeSheet> GetTimeSheetFromPeriod(LocalDate startDate, LocalDate endDate);
    public TimeSheet SaveTimeSheet(TimeSheetContract timeSheet);
    public List<TimeSheet> GetTimeSheetsByDate(LocalDate date);
    public TimeSheet DeleteTimeSheet(UUID timeSheetId);
    public TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId);
}
