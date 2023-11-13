package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;
import miniapp.timetracker.repository.ProjectRepository;
import miniapp.timetracker.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectsImpl implements ProjectsService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
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
    public String getProjectName(UUID projectId){
        Optional<Project> project =  projectRepository.findById(projectId);
        return project.get().getName();
    }
}
