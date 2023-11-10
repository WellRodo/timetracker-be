package miniapp.timetracker.controller;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.service.ProjectsService;
import miniapp.timetracker.service.TimeSheetService;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dictionary")
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping("/project")
    private List<Project> getAll(){
        return projectsService.getProject();
    }

    @PostMapping("/project/{name}")
    private void add(@PathVariable String name){
        Project project = new Project(UUID.randomUUID(), name);
        projectsService.saveProject(project);
    }
}
