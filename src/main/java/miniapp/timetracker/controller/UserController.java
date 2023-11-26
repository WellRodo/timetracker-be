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
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /** Регистрация нового пользователя*/
    @PostMapping
    private User add(@RequestBody UserContract userContract) {
        return userService.SaveUser(userContract);
    }

    /** Получение пользователя по его id*/
    @GetMapping("{id}")
    private User get(@PathVariable UUID id) {
        return userService.GetUser(id);
    }

    /** Получение полного списка пользователей*/
    @GetMapping
    private List<User> getAll() {
        return userService.GetAll();
    }


}
