package miniapp.timetracker.service;

import miniapp.timetracker.model.User;
import miniapp.timetracker.model.contracts.UserRegisterContract;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User saveUser(UserRegisterContract userRegisterContract);
    public User getUser(UUID id);
    public List<User> getAll();
    public User update(User user);
    public void delete(UUID ID);
}