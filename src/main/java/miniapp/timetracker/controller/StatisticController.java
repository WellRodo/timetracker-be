package miniapp.timetracker.controller;

import miniapp.timetracker.model.Message;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private TimeSheetService timeSheetService;
    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/{dateStart}/{dateEnd}/{projectId}")
    public ResponseEntity<Object> getUserStatisticsByProject(
            @PathVariable("dateStart") String dateStart,
            @PathVariable("dateEnd") String dateEnd,
            @PathVariable("projectId") UUID projectId) throws ParseException {

        LocalDate dtStart =  LocalDate.parse(dateStart);
        LocalDate dtEnd =LocalDate.parse(dateEnd);
        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriodAndProject(dtStart, dtEnd, projectId);
        if (timeSheetList.size() == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Таймшиты не найдены"));
        }

        Map<String, Double> jobTime = new HashMap<>();
        for (TimeSheet timeSheet : timeSheetList) {

            try {
                String jobName = timeSheet.getUser().getJob().getName();
                if (jobTime.containsKey(jobName))
                    jobTime.put(jobName, jobTime.get(jobName) + timeSheet.getWorkTime());
                else
                    jobTime.put(jobName, timeSheet.getWorkTime());
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage()));
            }



        }
        return null;
    }



    @GetMapping("/{dateStart}/{dateEnd}")
    public ResponseEntity<Object> getUserStatisticsAllProjects(
            @PathVariable("dateStart") String dateStart,
            @PathVariable("dateEnd") String dateEnd)
    {



        return null;
    }
}
