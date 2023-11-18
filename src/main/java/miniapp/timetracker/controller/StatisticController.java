package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.Message;
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

    @GetMapping("/{dateStart}/{dateEnd}/{projectId}")
    public ResponseEntity<Object> getUserStatisticsByProject( @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd, @PathVariable("projectId") UUID projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsByProject(dateStart,dateEnd,projectId));
    }

    @GetMapping("/{dateStart}/{dateEnd}")
    public ResponseEntity<Object> getUserStatisticsAllProjects(@PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd){
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsAllProjects(dateStart,dateEnd));
    }
}
