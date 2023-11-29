package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.ProjectContract;
import miniapp.timetracker.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface UserProjectService {
    public UserProject addUserProject(UUID userId, Project project);
    public void addUsersOnProject(ProjectContract projectContract);
    public void updateUserOnProject(ProjectContract projectContract);
}
