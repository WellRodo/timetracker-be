package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;

import java.util.List;

public interface ProjectsService {

    public Project saveProject(Project project);


    public List<Project> getProject();

}
