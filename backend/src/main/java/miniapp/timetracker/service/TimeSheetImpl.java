package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.TimeSheetContract;
import miniapp.timetracker.repository.TimeSheetRepository;
import miniapp.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(Date startDate, Date endDate, UUID projectId) {
        return timeSheetRepo.searchTimeSheetsByDateBetweenAndProjectIdEquals(startDate, endDate, projectId);
    }

    @Override
    public List<TimeSheet> GetTimeSheetsByDate(Date date) {
        return timeSheetRepo.searchTimeSheetsByDateEquals(date);
    }
}
