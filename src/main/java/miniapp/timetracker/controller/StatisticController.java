package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.EmployeeStatisticRequestContract;
import miniapp.timetracker.model.contracts.ProjectDatePeriodRequest;
import miniapp.timetracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/statistic")
@CrossOrigin
public class StatisticController {
    @Autowired
    private TimeSheetService timeSheetService;

    @GetMapping("/project")
    public ResponseEntity<Object> getUserStatisticsByProject(@PathVariable LocalDate dateStart, @PathVariable LocalDate dateEnd, @PathVariable UUID projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsByProject(dateStart, dateEnd, projectId));
    }

    @GetMapping("/project/all")
    public ResponseEntity<Object> getUserStatisticsAllProjects(@PathVariable LocalDate dateStart, @PathVariable LocalDate dateEnd){
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getUserStatisticsAllProjects(dateStart, dateEnd));
    }

    @GetMapping("/employee")
    public ResponseEntity<Object> getUserStatisticOnProjects(@RequestBody EmployeeStatisticRequestContract request){
        return null;
    }
}
