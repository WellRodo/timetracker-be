package miniapp.timetracker.controller;

import miniapp.timetracker.model.User;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private String add(@RequestBody User user) {
        userService.SaveUser(user);
        return "POSTOLA!";
    }

    @GetMapping("{id}")
    private User get(@PathVariable Integer id) {
        return userService.GetUser(id);
    }

    @GetMapping
    private List<User> getAll() {
        return userService.GetAll();
    }
}
