package miniapp.timetracker.controller;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.*;
import miniapp.timetracker.service.JwtTokenUtils;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

// TODO Поменять Dictionary на project
@RestController
@RequestMapping("/dictionary")
@CrossOrigin
public class ProjectController {
    @Autowired
    private TimeSheetService timeSheetService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/project")
    private ResponseEntity<Object> getAll(@RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getAllProjects(jwtTokenUtils.getUserId(token)));
    }

    @GetMapping("/project/{id}")
    private ResponseEntity<Object> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getProject(id));
    }

    @PostMapping("/project/{name}")
    private ResponseEntity<Object> saveProject(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.saveProject(new Project(null, name)));
    }

    /** Получение общего времени на проекте за определенное время */
    @GetMapping("/project/time/{dateStart}/{dateEnd}/{projectId}")
    @ResponseBody
    private ProjectTime getProjectTime(@PathVariable("dateStart") LocalDate dateStart, @PathVariable("dateEnd") LocalDate dateEnd, @PathVariable("projectId") UUID projectId) {
        return timeSheetService.GetProjectWorkTimePeriod(dateStart, dateEnd, projectId);
    }

    /** Получение времени по должностям на проекте за определенное время */
    @GetMapping("/project/job/time/{dateStart}/{dateEnd}/{projectId}")
    private ResponseEntity<Object> getJobTime(@PathVariable("dateStart") LocalDate dateStart, @PathVariable("dateEnd") LocalDate dateEnd, @PathVariable("projectId") UUID projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(timeSheetService.getJobTimeOnProject(dateStart, dateEnd, projectId));
    }

    /** Получение времени по работнику понедельно */
    @GetMapping("/project/employee")
    private ResponseEntity<Object> getWorkTimeByEmployee(@RequestBody EmployeeStatisticRequestContract request) {
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getWorkTimeOnProjectsByUser(request));
    }
}
