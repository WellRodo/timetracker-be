package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.FinishTimeSheetsContract;
import miniapp.timetracker.model.contracts.TimeSheetContract;
import miniapp.timetracker.model.*;
import miniapp.timetracker.model.contracts.*;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/timesheet")
@CrossOrigin
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

//    ..@Autowired
//    private ProjectsService projectsService;


    /** Получение таймшитов всех пользователей за определённую дату (Может РП-шник)*/
    @GetMapping("/day/{date}")
    private ResponseEntity<Object> getTimeSheetsByDate(@PathVariable LocalDate date) {
        List<TimeSheet> result = timeSheetService.GetTimeSheetsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

//    /** Получение общего времени на проекте за определенное время */
//    @GetMapping("/project/time")
//    @ResponseBody
//    private ProjectTime getProjectTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) {
//        LocalDate dtStart = LocalDate.parse(dateStart);
//        LocalDate dtEnd = LocalDate.parse(dateEnd);
//        List<TimeSheet> timeSheetList =  timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
//        ProjectTime projectTime = new ProjectTime("", 0.0);
//
//        projectTime.setProjectName(projectsService.getProjectName(projectId));
//
//        for (TimeSheet timeSheet: timeSheetList) {
//            projectTime.setTime(projectTime.getTime() + timeSheet.getWorkTime());
//        }
//
//        return projectTime;
//    }
//
//    /** Получение времени по должностям на проекте за определенное время */
//    @GetMapping("/job/time")
//    private ResponseEntity<Object> getJobTime(@RequestHeader("dateStart") String dateStart, @RequestHeader("dateEnd") String dateEnd, @RequestHeader("projectId") UUID projectId) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        LocalDate dtStart = LocalDate.parse(dateStart);
//        LocalDate dtEnd = LocalDate.parse(dateEnd);
//        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
//        if (timeSheetList.size() == 0) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Таймшиты не найдены",HttpStatus.NOT_FOUND));
//        }
//
//        Map<String , Double> jobTime = new HashMap<>();
//        for (TimeSheet timeSheet: timeSheetList) {
//
//            try {
//                String jobName = timeSheet.getUser().getJob().getName();
//                if(jobTime.containsKey(jobName))
//                    jobTime.put(jobName, jobTime.get(jobName) + timeSheet.getWorkTime());
//                else
//                    jobTime.put(jobName, timeSheet.getWorkTime());
//            }catch (Exception ex){
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
//            }
//
//        }
//
//        List<JobTimeOnProject> jobTimeOnProjects = new ArrayList<>();
//        for(Map.Entry<String , Double> pair : jobTime.entrySet()){
//            jobTimeOnProjects.add(new JobTimeOnProject(pair.getKey(), pair.getValue()));
//        }
//
//        return ResponseEntity.status(HttpStatus.OK).body(jobTimeOnProjects);
//    }

    /** Добавление таймшитов на определённую дату */
    @PostMapping("/day")
    private ResponseEntity<Object> post(@RequestBody TimeSheetContract timeSheet){
        TimeSheet result = timeSheetService.SaveTimeSheet(timeSheet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /** Изменение таймшита */
    @PutMapping("/day/{timeSheetId}")
    private ResponseEntity<Object> putTimeSheet(@RequestBody TimeSheetContract timeSheet, @PathVariable UUID timeSheetId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.UpdateTimeSheet(timeSheet, timeSheetId));
    }

    /** Изменение статуса таймшита */
    @PutMapping("/day/finish")
    private ResponseEntity<Object> finishTimeSheets(@RequestBody FinishTimeSheetsContract timeSheets) {
        try{
            timeSheetService.FinishTimeSheets(timeSheets);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Завершено",HttpStatus.OK));
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.OK).body(new Message(ex.getMessage(),HttpStatus.NOT_FOUND));
        }

    }

    /** Удаление таймшита */
    @DeleteMapping("/day/{timeSheetId}")
    private ResponseEntity<Object> deleteTimeSheet(@PathVariable UUID timeSheetId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.DeleteTimeSheet(timeSheetId));
    }
}
