package miniapp.timetracker.controller;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.service.ProjectsService;
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
        return projectsService.getAllProjects();
    }

    @PostMapping("/project/{name}")
    private Project add(@PathVariable String name){
        Project project = new Project(UUID.randomUUID(), name);
        return projectsService.saveProject(project);
    }
}
