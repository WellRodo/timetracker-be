package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.contracts.EmployeeStatisticRequestContract;
import miniapp.timetracker.model.contracts.EmployeeStatisticResponseContract;
import miniapp.timetracker.model.contracts.WeekWorkTime;

import java.util.List;
import java.util.UUID;

public interface ProjectsService {

    public Project saveProject(Project project);
    public Project updateProject(Project project);
    public void deleteProject(Project project);
    public List<Project> getAllProjectsWithActive(String userId, Boolean isActive);
    public List<Project> getAllProjects(String userId);
    public Project getProject(UUID projectId);
    public String getProjectName(UUID projectId);
    public List<Project> getAllProjectsByUser(UUID userId);
    public EmployeeStatisticResponseContract getWorkTimeOnProjectsByUser(EmployeeStatisticRequestContract req);
}
