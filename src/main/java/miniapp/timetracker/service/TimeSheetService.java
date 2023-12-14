package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSheetService {
    List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate, UUID projectId);
    ProjectTime GetProjectWorkTimePeriod(LocalDate startDate, LocalDate endDate, UUID projectId);
    TimeSheet SaveTimeSheet(TimeSheetContract timeSheet, UUID userId);
    List<TimeSheet> GetTimeSheetsByDate(LocalDate date, UUID userID);
    TimeSheet DeleteTimeSheet(UUID timeSheetId);
    TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId, UUID userID);
    void FinishTimeSheets(FinishTimeSheetsContract timeSheets);
    List<UserStatistics> getUserStatisticsByProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId);
    List<UserStatistics> getUserStatisticsAllProjects(LocalDate dateStart, LocalDate dateEnd);
    List<JobTimeOnProject> getJobTimeOnProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId);
}
