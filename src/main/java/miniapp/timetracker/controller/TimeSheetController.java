package miniapp.timetracker.controller;

import miniapp.timetracker.model.*;
import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.JobTimeOnProject;
import miniapp.timetracker.model.contracts.ProjectTime;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/timesheet")
@CrossOrigin
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @Autowired
    private ProjectsService projectsService;


    @GetMapping("/day/{date}")
    private ResponseEntity<Object> get(@PathVariable String date) {
        LocalDate dt = LocalDate.parse(date);
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.GetTimeSheetsByDate(dt));
    }


    @PostMapping("/day")
    private ResponseEntity<Object> post(@RequestBody TimeSheetContract timeSheet){
        Logger.getGlobal().log(Level.INFO, timeSheet.getDate().toString());
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.SaveTimeSheet(timeSheet));
    }


    //Получение общего времени на проекте за определенное время
    @GetMapping("/project/time")
    @ResponseBody
    private ProjectTime getProjectTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dateStart);
        LocalDate dtEnd = LocalDate.parse(dateEnd);
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
    private ResponseEntity<Object> getJobTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate dtStart = LocalDate.parse(dateStart);
        LocalDate dtEnd = LocalDate.parse(dateEnd);
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

    @PutMapping("/day/{timeSheetId}")
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    private ResponseEntity<Object> putTimeSheet(@RequestBody TimeSheetContract timeSheet, @PathVariable UUID timeSheetId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.UpdateTimeSheet(timeSheet, timeSheetId));
    }

    @PutMapping("/day/finish")
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    private ResponseEntity<Object> finishTimeSheets(@RequestBody FinishTimeSheetsContract timeSheets) {
        try{
            timeSheetService.FinishTimeSheets(timeSheets);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Завершено"));
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.OK).body(new Message(ex.getMessage()));
        }

    }

    @DeleteMapping("/day/{timeSheetId}")
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    private ResponseEntity<Object> deleteTimeSheet(@PathVariable UUID timeSheetId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.DeleteTimeSheet(timeSheetId));
    }
}
