package miniapp.timetracker.service;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.repository.TimeSheetRepository;
import miniapp.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
@Service
public class TimeSheetImpl implements TimeSheetService{
    @Autowired
    private TimeSheetRepository timeSheetRepo;

    @Override
    public TimeSheet SaveTimeSheet(TimeSheet tm) {
        return timeSheetRepo.save(tm);
    }

    @Override
    public TimeSheet[] GetTimeSheetFromPeriodAndProject(Date startDate, Date endDate, UUID projectId) {
        return timeSheetRepo.searchTimeSheetsByDateBetweenAndProjectIdEquals(startDate, endDate, projectId);
    }

    @Override
    public TimeSheet[] GetTimeSheetsByDate(Date date) {
        return timeSheetRepo.searchTimeSheetsByDateEquals(date);
    }
}
