package miniapp.timetracker.service;

import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.contracts.*;
import miniapp.timetracker.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List<TimeSheet> timeSheetList = timeSheetRepo.searchTimeSheetsByDateEquals(date);
        return timeSheetList;
//        if(timeSheetList.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "Таймшиты не найдены");
//        else return timeSheetRepo.searchTimeSheetsByDateEquals(date);
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

    @Override
    public void FinishTimeSheets(FinishTimeSheetsContract timeSheets) {
        timeSheetRepo.updateFinished(true, timeSheets.timesheetIDs);
    }

    @Override
    public List<UserStatistics> getUserStatisticsByProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId) {
        List<TimeSheet> timeSheetList = GetTimeSheetFromPeriodAndProject(dateStart, dateEnd, projectId);
        if (timeSheetList.size() == 0) throw new CustomException(HttpStatus.NOT_FOUND, "За данный период не найдено Таймшитов");
        List<User> userList = userService.GetAll(); // Список всех пользователей
        List<UserStatistics> userStatisticsList = new ArrayList<>(); //Возвращаемый список

        int i = 0;
        for(User user: userList){
            userStatisticsList.add(new UserStatistics(user, new ArrayList<ProjectTime>()));
            userStatisticsList.get(i).getProjectTimeList().add(new ProjectTime());
            userStatisticsList.get(i).getProjectTimeList().get(0).setProjectName(projectsService.getProjectName(projectId));
            for (TimeSheet timeSheet : timeSheetList) {
                try {
                    if(timeSheet.getUser().equals(user))
                        userStatisticsList.get(i).getProjectTimeList().get(0).addTime(timeSheet.getWorkTime());
                } catch (Exception ex) {
                    throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                }
            } i++;
        }  return userStatisticsList;
    }

    private UserStatistics addNewUserStatistic(User user){
        List<Project> projectList = projectsService.getAllProjectsByUser(user.getId()); // Список всех проектов на которых есть юзер
        List<ProjectTime> projectTimeList = new ArrayList<>();
        for(Project project:projectList){
            projectTimeList.add(new ProjectTime(project.getName(), 0d));
        }
        return new UserStatistics(user, projectTimeList);
    }
    @Override
    public List<UserStatistics> getUserStatisticsAllProjects(LocalDate dateStart, LocalDate dateEnd) {

        List<TimeSheet> timeSheetList = GetTimeSheetFromPeriod(dateStart, dateEnd);
        if (timeSheetList.size() == 0) throw new CustomException(HttpStatus.NOT_FOUND, "За данный период не найдено Таймшитов");

        List<User> userList = userService.GetAll(); // Список всех пользователей
        List<UserStatistics> userStatisticsList = new ArrayList<>(); //Возвращаемый список

        try {
            int i = 0;
            for(User user: userList){
                userStatisticsList.add(addNewUserStatistic(user));

                for (TimeSheet timeSheet : timeSheetList) {
                    if(timeSheet.getUser().equals(user)){
                        int j = 0;
                        for(ProjectTime projectTime : userStatisticsList.get(i).getProjectTimeList()){

                            if(timeSheet.getProject().getName() == (projectTime.getProjectName())){
                                userStatisticsList.get(i).getProjectTimeList().get(j).addTime(timeSheet.getWorkTime());
                            }
                            j++;
                        }
                    }
                } i++;
            }
        }catch (Exception ex){
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        } return userStatisticsList;
    }
}
