package miniapp.timetracker.controller;

import miniapp.timetracker.model.Message;
import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.contracts.ProjectTime;
import miniapp.timetracker.model.contracts.UserStatistics;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/statistic")
@CrossOrigin
public class StatisticController {
    @Autowired
    private TimeSheetService timeSheetService;
    @Autowired
    private ProjectsService projectsService;
    @Autowired
    private UserService userService;

    @GetMapping("/{dateStart}/{dateEnd}/{projectId}")
    public ResponseEntity<Object> getUserStatisticsByProject(
            @PathVariable("dateStart") String dateStart,
            @PathVariable("dateEnd") String dateEnd,
            @PathVariable("projectId") UUID projectId) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dateStart);
        LocalDate dtEnd = LocalDate.parse(dateEnd);
        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
        if (timeSheetList.size() == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Таймшиты не найдены"));
        }

        List<User> userList = userService.GetAll(); // Список всех пользователей
        List<UserStatistics> userStatisticsList = new ArrayList<>();

        int i = 0;
        for(User user: userList){
            userStatisticsList.add(new UserStatistics(user, new ArrayList<ProjectTime>()));
            userStatisticsList.get(i).getProjectTimeList().add(new ProjectTime());
            userStatisticsList.get(i).getProjectTimeList().get(0).setProjectName(projectsService.getProjectName(projectId));
            for (TimeSheet timeSheet : timeSheetList) {
                try {

                    if(timeSheet.getUser().equals(user)){
                        userStatisticsList.get(i).getProjectTimeList().get(0).addTime(timeSheet.getWorkTime());
                    }
                } catch (Exception ex) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage()));
                }
            }
            i++;
        }
        return ResponseEntity.status(HttpStatus.OK).body(userStatisticsList);
    }


    private UserStatistics addNewUserStatistic(User user){
        List<Project> projectList = projectsService.getAllProjectsByUser(user.getId()); // Список всех проектов на которых есть юзер
        List<ProjectTime> projectTimeList = new ArrayList<>();
        for(Project project:projectList){
            projectTimeList.add(new ProjectTime(project.getName(), 0d));
        }
        return new UserStatistics(user, projectTimeList);
    }
    @GetMapping("/{dateStart}/{dateEnd}")
    public ResponseEntity<Object> getUserStatisticsAllProjects(
            @PathVariable("dateStart") String dateStart,
            @PathVariable("dateEnd") String dateEnd) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dateStart);
        LocalDate dtEnd = LocalDate.parse(dateEnd);
        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriod(dtStart, dtEnd);
        if (timeSheetList.size() == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Таймшиты не найдены"));
        }

        List<User> userList = userService.GetAll(); // Список всех пользователей
        List<UserStatistics> userStatisticsList = new ArrayList<>();

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
                }
                i++;
            }
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(userStatisticsList);
    }
}
