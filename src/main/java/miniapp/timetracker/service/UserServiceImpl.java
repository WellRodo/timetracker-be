package miniapp.timetracker.service;

import miniapp.timetracker.model.User;
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

    @Override
    public User SaveUser(User user) {
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
