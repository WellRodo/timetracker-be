package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.ProjectDatePeriodRequest;
import miniapp.timetracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
@CrossOrigin
public class StatisticController {
    @Autowired
    private TimeSheetService timeSheetService;

    @GetMapping("/project")
    public ResponseEntity<Object> getUserStatisticsByProject(@RequestBody ProjectDatePeriodRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsByProject(request.getDateStart(),request.getDateEnd(),request.getProjectId()));
    }

    @GetMapping("/project/all")
    public ResponseEntity<Object> getUserStatisticsAllProjects(@RequestBody ProjectDatePeriodRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsAllProjects(request.getDateStart(),request.getDateEnd()));
    }
}
