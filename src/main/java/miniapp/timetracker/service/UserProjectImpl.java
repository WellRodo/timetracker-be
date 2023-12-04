package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.ProjectContract;
import miniapp.timetracker.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserProjectImpl implements UserProjectService{
    @Autowired
    UserProjectRepository userProjectRepo;
    @Autowired
    UserService userService;

    @Override
    public UserProject addUserProject(UUID userId, Project project) {
        User user = userService.getUser(userId);
        return userProjectRepo.save(new UserProject(user, project));
    }

    @Override
    public void addUsersOnProject(ProjectContract projectContract) {
        for(User user: projectContract.getUserList()){
            if(userProjectRepo.searchUserProjectsByUserIdAndProjectIdEquals(user.getId(), projectContract.getProject().getId()).isEmpty()){
                addUserProject(user.getId(),projectContract.getProject());
            }
        }
    }

    @Override
    public void updateUserOnProject(ProjectContract projectContract) {
        for(User user: projectContract.getUserList()){
            if(userProjectRepo.searchUserProjectsByUserIdAndProjectIdEquals(user.getId(), projectContract.getProject().getId()).isEmpty()){
                addUserProject(user.getId(),projectContract.getProject());
            }
        }
        List<UUID> uuidList = projectContract.getUserList().stream().map(c -> c.getId()).collect(Collectors.toList());
        userProjectRepo.deleteUsersFromProject(projectContract.getProject().getId(), uuidList);

    }

    @Override
    public List<User> getUsersFromProject(UUID projectId) {
        List<UserProject> userProjectList = userProjectRepo.searchUserProjectsByProjectIdEquals(projectId);
        return userProjectList.stream().map(c -> c.getUser()).collect(Collectors.toList());
    }
}
