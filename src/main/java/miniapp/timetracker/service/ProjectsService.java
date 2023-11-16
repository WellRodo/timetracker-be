package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectsService {

    public Project saveProject(Project project);
    public List<Project> getAllProjects();
    public Project getProject(UUID projectId);
    public String getProjectName(UUID projectId);
    public List<Project> getAllProjectsByUser(UUID userId);
}
