package miniapp.timetracker.service;

import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProjectImpl {
    @Autowired
    UserProjectRepository userProjectRepo;

    UserProject addUserProject(UserProject userProject) {
        return userProjectRepo.save(userProject);
    }
}
