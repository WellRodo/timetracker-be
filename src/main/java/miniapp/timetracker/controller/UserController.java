package miniapp.timetracker.controller;

import miniapp.timetracker.model.User;
import miniapp.timetracker.model.contracts.UserContract;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dictionary/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private User add(@RequestBody UserContract userContract) {
        return userService.SaveUser(userContract);
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
