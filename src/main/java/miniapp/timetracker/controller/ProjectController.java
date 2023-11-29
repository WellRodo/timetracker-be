package miniapp.timetracker.controller;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.contracts.*;
import miniapp.timetracker.service.JwtTokenUtils;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private UserProjectService userProjectService;

    @GetMapping("/project")
    private ResponseEntity<Object> getAll(@RequestHeader("Authorization") String token){
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getAllProjects(jwtTokenUtils.getUserId(token)));
    }

    @GetMapping("/project/{id}")
    private ResponseEntity<Object> getById(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.getProject(id));
    }

    @PostMapping("/project")
    private ResponseEntity<Object> saveNewProject(@RequestHeader("Authorization") String token, @RequestBody ProjectContract projectContract)  {
        Project project = new Project(UUID.randomUUID(), projectContract.getProject().getName());
        projectsService.saveProject(project);
        userProjectService.addUserProject(UUID.fromString(jwtTokenUtils.getUserId(token)),project);
        projectContract.setProject(project);
        userProjectService.addUsersOnProject(projectContract);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/project")
    private ResponseEntity<Object> updateProject(@RequestHeader("Authorization") String token, @RequestBody ProjectContract projectContract)  {
        projectContract.getUserList().add(new User(UUID.fromString(jwtTokenUtils.getUserId(token)), "", null));
        userProjectService.updateUserOnProject(projectContract);
        return ResponseEntity.status(HttpStatus.OK).body(projectsService.updateProject(projectContract.getProject()));
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
