package miniapp.timetracker.service;

import miniapp.timetracker.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User SaveUser(User user);
    public User GetUser(UUID id);
    public List<User> GetAll();
}