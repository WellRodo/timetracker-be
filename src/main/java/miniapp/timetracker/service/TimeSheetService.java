package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.model.contracts.UserStatistics;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSheetService {
    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate, UUID projectId);
    public List<TimeSheet> GetTimeSheetFromPeriod(LocalDate startDate, LocalDate endDate);
    public TimeSheet SaveTimeSheet(TimeSheetContract timeSheet);
    public List<TimeSheet> GetTimeSheetsByDate(LocalDate date);
    public TimeSheet DeleteTimeSheet(UUID timeSheetId);
    public TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId);
    public void FinishTimeSheets(FinishTimeSheetsContract timeSheets);
    public List<UserStatistics> getUserStatisticsByProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId);
    public List<UserStatistics> getUserStatisticsAllProjects(LocalDate dateStart, LocalDate dateEnd);
}
