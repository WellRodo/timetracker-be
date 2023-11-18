package miniapp.timetracker.controller;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.contracts.ProjectDatePeriodRequest;
import miniapp.timetracker.model.contracts.JobTimeOnProject;
import miniapp.timetracker.model.contracts.Message;
import miniapp.timetracker.model.contracts.ProjectTime;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

// TODO Поменять Dictionary на project
@RestController
@RequestMapping("/dictionary")
@CrossOrigin
public class ProjectController {
    @Autowired
    private TimeSheetService timeSheetService;

    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/project")
    private List<Project> getAll(){
        return projectsService.getAllProjects();
    }

    // TODO: Доработать POST метода для Projects
    @PostMapping("/project/{name}")
    private Project add(@PathVariable String name){
        Project project = new Project(UUID.randomUUID(), name);
        return projectsService.saveProject(project);
    }

    // TODO Перенести логику на слой БЛ
    /** Получение общего времени на проекте за определенное время */
    @GetMapping("/project/time")
    @ResponseBody
    private ProjectTime getProjectTime(@RequestBody ProjectDatePeriodRequest request) {
        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriodAndProject(request.getDateStart(), request.getDateEnd(), request.getProjectId());
        ProjectTime projectTime = new ProjectTime("", 0.0);

        projectTime.setProjectName(projectsService.getProjectName(request.getProjectId()));

        for (TimeSheet timeSheet: timeSheetList) {
            projectTime.setTime(projectTime.getTime() + timeSheet.getWorkTime());
        }

        return projectTime;
    }

    // TODO Перенести логику на слой БЛ
    /** Получение времени по должностям на проекте за определенное время */
    @GetMapping("/project/job/time")
    private ResponseEntity<Object> getJobTime(@RequestBody ProjectDatePeriodRequest request) {
        List<TimeSheet> timeSheetList = timeSheetService.GetTimeSheetFromPeriodAndProject(request.getDateStart(), request.getDateEnd(), request.getProjectId());

        if (timeSheetList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Таймшиты не найдены",HttpStatus.NOT_FOUND));
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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
            }

        }

        List<JobTimeOnProject> jobTimeOnProjects = new ArrayList<>();
        for(Map.Entry<String , Double> pair : jobTime.entrySet()){
            jobTimeOnProjects.add(new JobTimeOnProject(pair.getKey(), pair.getValue()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(jobTimeOnProjects);
    }
}
