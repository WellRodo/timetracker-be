package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.ProjectTime;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.model.contracts.UserStatistics;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimeSheetService {
    List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate, UUID projectId);
    ProjectTime GetProjectWorkTimePeriod(LocalDate startDate, LocalDate endDate, UUID projectId);
    TimeSheet SaveTimeSheet(TimeSheetContract timeSheet);
    List<TimeSheet> GetTimeSheetsByDate(LocalDate date);
    TimeSheet DeleteTimeSheet(UUID timeSheetId);
    TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId);
    void FinishTimeSheets(FinishTimeSheetsContract timeSheets);
    List<UserStatistics> getUserStatisticsByProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId);
    List<UserStatistics> getUserStatisticsAllProjects(LocalDate dateStart, LocalDate dateEnd);
}
