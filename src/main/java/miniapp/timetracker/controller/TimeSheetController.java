package miniapp.timetracker.controller;

import miniapp.timetracker.model.*;
import miniapp.timetracker.service.JobService;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserService;
import org.apache.coyote.Response;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @Autowired
    private ProjectsService projectsService;


    @GetMapping("/day/{date}")
    private List<TimeSheet> get(@PathVariable String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = formatter.parse(date);
        return timeSheetService.GetTimeSheetsByDate(dt);
    }

    @PostMapping("/day")
    private void post(@RequestBody TimeSheetContract timeSheet){
        timeSheetService.SaveTimeSheet(timeSheet);
    }


    //Получение общего времени на проекте за определенное время
    @GetMapping("/project/time")
    @ResponseBody
    private ProjectTime getProjectTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dtStart = formatter.parse(dateStart);
        Date dtEnd = formatter.parse(dateEnd);
        List<TimeSheet> timeSheetList =  timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
        ProjectTime projectTime = new ProjectTime("", 0.0);

        projectTime.setProjectName(projectsService.getProjectName(projectId));

        for (TimeSheet timeSheet: timeSheetList) {
            projectTime.setTime(projectTime.getTime() + timeSheet.getWorkTime());
        }

        return projectTime;
    }

    //Получение времени по должностям на проекте за определенное время
    @GetMapping("/job/time")
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    private ResponseEntity<Object> getJobTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dtStart = formatter.parse(dateStart);
        Date dtEnd = formatter.parse(dateEnd);
        List<TimeSheet> timeSheetList =  timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
        if(timeSheetList.size() == 0){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Таймшиты не найдены"));
        }

        Map<String , Double> jobTime = new HashMap<>();
        for (TimeSheet timeSheet: timeSheetList) {

            try {
                String jobName = timeSheet.getUser().getJob().getName();
                if(jobTime.containsKey(jobName))
                    jobTime.put(jobName, jobTime.get(jobName) + timeSheet.getWorkTime());
                else
                    jobTime.put(jobName, timeSheet.getWorkTime());
            }catch (Exception ex){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage()));
            }

        }

        List<JobTimeOnProject> jobTimeOnProjects = new ArrayList<>();
        for(Map.Entry<String , Double> pair : jobTime.entrySet()){
            jobTimeOnProjects.add(new JobTimeOnProject(pair.getKey(), pair.getValue()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(jobTimeOnProjects);
    }
}
