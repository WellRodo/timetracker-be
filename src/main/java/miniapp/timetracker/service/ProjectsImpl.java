package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.EmployeeStatisticRequestContract;
import miniapp.timetracker.model.contracts.EmployeeStatisticResponseContract;
import miniapp.timetracker.model.contracts.WeekWorkTime;
import miniapp.timetracker.model.contracts.WeekWorkTimeInterface;
import miniapp.timetracker.repository.ProjectRepository;
import miniapp.timetracker.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectsImpl implements ProjectsService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserProjectRepository userProjectRepository;

    @Override
    public List<Project> getAllProjects(String userId){
        List<UserProject> userProjectList= userProjectRepository.searchUserProjectsByUserIdEquals(UUID.fromString(userId));
        return new ArrayList<Project>(userProjectList.stream().map(c -> c.getProject()).collect(Collectors.toList()));
    }

    @Override
    public Project getProject(UUID projectId) {
        return projectRepository.findById(projectId).get();
    }

    @Override
    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public String getProjectName(UUID projectId){
        Optional<Project> project = projectRepository.findById(projectId);
        return project.get().getName();
    }

    @Override
    public List<Project> getAllProjectsByUser(UUID userId) {
        List<UserProject> userProjectList = userProjectRepository.searchUserProjectsByUserIdEquals(userId);
        List<Project> projectList = new ArrayList<>();
        for(UserProject userProject: userProjectList){
            projectList.add(userProject.getProject());
        }
        return projectList;
    }

    @Override
    public EmployeeStatisticResponseContract getWorkTimeOnProjectsByUser(EmployeeStatisticRequestContract req) {
        var currentDate = req.getMonthDate();
        EmployeeStatisticResponseContract workTimeByProject = new EmployeeStatisticResponseContract();
        workTimeByProject.setWorkWeeks(new ArrayList<>());
        while (currentDate.isBefore(req.getMonthDate().plusMonths(3))) {
            List<WeekWorkTimeInterface> queryResult = projectRepository.getWorkTimeByWeek(currentDate, currentDate.plusDays(7), req.getEmployeeID(), req.getProjectIDs()); //query()
            for (WeekWorkTimeInterface w: queryResult) {
              workTimeByProject.addWorkWeeks(new WeekWorkTime(w.getProjectName(), w.getWeekDate(), w.getWorkTime()));
            }
            currentDate = currentDate.plusDays(7);
        }
        return workTimeByProject;
    }
}
