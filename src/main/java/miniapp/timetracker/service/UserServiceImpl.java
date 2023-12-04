package miniapp.timetracker.service;

import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserAuth;
import miniapp.timetracker.model.UserProject;
import miniapp.timetracker.model.contracts.UserRegisterContract;
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
    public User saveUser(UserRegisterContract userRegisterContract) {
        UserAuth userAuth = userRegisterContract.getUserAuth();
        userRepository.save(userAuth.getUser());
        userAuthRepository.save(userAuth);
        userProjectRepository.save(new UserProject(userAuth.getUser(), new Project(userProjectRepository.emptyProjectUUID, "Простой", true)));
        return userAuth.getUser();
    }

    @Override
    public User getUser(UUID id) {
        Optional<User> user = userRepository.findById(id);

        return user.get();
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID ID) {
        User user = userRepository.findById(ID).get();
        user.setIsActive(false);
        userRepository.save(user);
    }
}
