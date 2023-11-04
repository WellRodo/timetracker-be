package miniapp.timetracker.service;

import miniapp.timetracker.model.User;

import java.util.List;

public interface UserService {
    public User SaveUser(User user);
    public User GetUser(Integer id);
    public List<User> GetAll();
}