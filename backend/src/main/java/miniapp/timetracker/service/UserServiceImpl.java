package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserContract;
import miniapp.timetracker.repository.JobRepository;
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
    private JobRepository jobRepository;

    @Override
    public User SaveUser(UserContract userContract) {
        User user = new User(
                UUID.randomUUID(),
                userContract.getName(),
                jobRepository.findById(userContract.getJobId()).get()
        );
        return userRepository.save(user);
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
