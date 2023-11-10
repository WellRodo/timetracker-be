package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.repository.ProjectRepository;
import miniapp.timetracker.repository.TimeSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectsImpl implements ProjectsService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getProject(){
        return projectRepository.findAll();
    }

    @Override
    public Project saveProject(Project project){
        return projectRepository.save(project);
    }

}
