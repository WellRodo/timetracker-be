package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.UserContract;
import miniapp.timetracker.repository.JobRepository;
import miniapp.timetracker.repository.UserAuthRepository;
import miniapp.timetracker.repository.UserProjectRepository;
import miniapp.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserProjectRepository userProjectRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public User SaveUser(UserContract userContract) {
        var userAuth = userAuthRepository.save(userContract.getUserAuth());
        userProjectRepository.save(new UserProject(userAuth.getUser(), new Project(userProjectRepository.emptyProjectUUID, "Простой", true)));
        return userAuth.getUser();
    }

    @Override
    public User GetUser(UUID id) {
        Optional<User> user = userRepository.findById(id);

        return user.get();
    }

    @Override
    public List<User> GetAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

}
