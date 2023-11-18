package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class TimeSheetImpl implements TimeSheetService{
    @Autowired
    private TimeSheetRepository timeSheetRepo;
    @Autowired ProjectsService projectsService;
    @Autowired UserService userService;

    @Override
    public TimeSheet SaveTimeSheet(TimeSheetContract timeSheetContract) {
        TimeSheet timeSheet = new TimeSheet(
                UUID.randomUUID(),
                projectsService.getProject(timeSheetContract.getProjectId()),
                userService.GetUser(timeSheetContract.getUserId()),
                timeSheetContract.getWorkTime(),
                timeSheetContract.getDescription(),
                timeSheetContract.getDate(),
                timeSheetContract.getFinished()
        );
        return timeSheetRepo.save(timeSheet);
    }

    @Override
    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate, UUID projectId) {
        return timeSheetRepo.searchTimeSheetsByDateBetweenAndProjectIdEquals(startDate, endDate, projectId);
    }

    @Override
    public List<TimeSheet> GetTimeSheetFromPeriod(LocalDate startDate, LocalDate endDate) {
        return timeSheetRepo.searchTimeSheetsByDateBetween(startDate, endDate);
    }

    @Override
    public List<TimeSheet> GetTimeSheetsByDate(LocalDate date) {
        return timeSheetRepo.searchTimeSheetsByDateEquals(date);
    }

    @Override
    public TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId) {
        TimeSheet updTimeSheet = new TimeSheet(
                timeSheetId,
                projectsService.getProject(timeSheet.getProjectId()),
                userService.GetUser(timeSheet.getUserId()),
                timeSheet.getWorkTime(),
                timeSheet.getDescription(),
                timeSheet.getDate(),
                timeSheet.getFinished()
        );
        return timeSheetRepo.save(updTimeSheet);
    }

    @Override
    public TimeSheet DeleteTimeSheet(UUID timeSheetId) {
        TimeSheet timeSheet = timeSheetRepo.findById(timeSheetId).get();
        timeSheetRepo.deleteById(timeSheetId);
        return timeSheet;
    }

//    @Override
//    public void FinishTimeSheets(FinishTimeSheetsContract timeSheets) {
//        timeSheetRepo.updateFinished(true, timeSheets.timesheetIDs);
//    }
}
