package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;
import miniapp.timetracker.model.User;
import miniapp.timetracker.model.UserContract;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User SaveUser(UserContract userContract);
    public User GetUser(UUID id);
    public List<User> GetAll();
}