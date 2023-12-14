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
import java.util.*;

@Service
public class TimeSheetImpl implements TimeSheetService {
    @Autowired
    private TimeSheetRepository timeSheetRepo;
    @Autowired
    ProjectsService projectsService;
    @Autowired
    UserService userService;

    @Override
    public TimeSheet SaveTimeSheet(TimeSheetContract timeSheetContract, UUID userId) {
        TimeSheet timeSheet = new TimeSheet(
                UUID.randomUUID(),
                projectsService.getProject(timeSheetContract.getProjectId()),
                userService.getUser(userId),
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
    public ProjectTime GetProjectWorkTimePeriod(LocalDate startDate, LocalDate endDate, UUID projectId) {
        List<TimeSheet> timeSheetsProject = timeSheetRepo.searchTimeSheetsByDateBetweenAndProjectIdEquals(startDate, endDate, projectId);

        ProjectTime projectTime = new ProjectTime("", 0.0);

        projectTime.setProjectName(projectsService.getProjectName(projectId));

        for (TimeSheet timeSheet : timeSheetsProject) {
            projectTime.setTime(projectTime.getTime() + timeSheet.getWorkTime());
        }

        return projectTime;
    }


    public List<TimeSheet> GetTimeSheetFromPeriodAndProject(LocalDate startDate, LocalDate endDate) {
        return timeSheetRepo.searchTimeSheetsByDateBetween(startDate, endDate);
    }

    @Override
    public List<TimeSheet> GetTimeSheetsByDate(LocalDate date, UUID userID) {
        return timeSheetRepo.searchTimeSheetsByDateAndUserId(date, userID);
    }

    @Override
    public TimeSheet UpdateTimeSheet(TimeSheetContract timeSheet, UUID timeSheetId, UUID userID) {
        TimeSheet updTimeSheet = new TimeSheet(
                timeSheetId,
                projectsService.getProject(timeSheet.getProjectId()),
                userService.getUser(userID),
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
        if (timeSheetList.size() == 0) throw new CustomException(HttpStatus.OK, "За данный период не найдено Таймшитов");
        List<User> userList = userService.getAll(); // Список всех пользователей
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

        List<TimeSheet> timeSheetList = GetTimeSheetFromPeriodAndProject(dateStart, dateEnd);
        if (timeSheetList.size() == 0) throw new CustomException(HttpStatus.OK, "За данный период не найдено Таймшитов");

        List<User> userList = userService.getAll(); // Список всех пользователей
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

    @Override
    public List<JobTimeOnProject> getJobTimeOnProject(LocalDate dateStart, LocalDate dateEnd, UUID projectId) {
        List<TimeSheet> timeSheetList = GetTimeSheetFromPeriodAndProject(dateStart, dateEnd, projectId);
        if (timeSheetList.size() == 0) {
            throw new CustomException(HttpStatus.OK, "За данный период не найдено Таймшитов");
        }
        Map<String, Double> jobTime = getStringDoubleMap(timeSheetList);
        List<JobTimeOnProject> jobTimeOnProjects = new ArrayList<>();
        for(Map.Entry<String , Double> pair : jobTime.entrySet()){
            jobTimeOnProjects.add(new JobTimeOnProject(pair.getKey(), pair.getValue()));
        }
        return  jobTimeOnProjects;
    }

    private static Map<String, Double> getStringDoubleMap(List<TimeSheet> timeSheetList) {
        Map<String , Double> jobTime = new HashMap<>();
        for (TimeSheet timeSheet: timeSheetList) {
            try {
                String jobName = timeSheet.getUser().getJob().getName();
                if(jobTime.containsKey(jobName))
                    jobTime.put(jobName, jobTime.get(jobName) + timeSheet.getWorkTime());
                else
                    jobTime.put(jobName, timeSheet.getWorkTime());
            }catch (Exception ex){
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            }

        }
        return jobTime;
    }
}
