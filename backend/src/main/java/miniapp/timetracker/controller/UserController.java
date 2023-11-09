package miniapp.timetracker.controller;

import miniapp.timetracker.model.User;
import miniapp.timetracker.service.RoleService;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping
    private User add(@RequestBody User user) {
        return userService.SaveUser(user);
    }

    @GetMapping("{id}")
    private User get(@PathVariable UUID id) {
        return userService.GetUser(id);
    }

    @GetMapping
    private List<User> getAll() {
        return userService.GetAll();
    }
}
