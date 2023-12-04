package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.UserContract;
import miniapp.timetracker.model.contracts.UserRegisterContract;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dictionary/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /** Регистрация нового пользователя*/
    @PostMapping
    private ResponseEntity<Object> add(@RequestBody UserRegisterContract userRegisterContract) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.saveUser(userRegisterContract));
    }

    /** Получение пользователя по его id*/
    @GetMapping("/{id}")
    private ResponseEntity<Object> get(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    /** Получение полного списка пользователей*/
    @GetMapping
    private ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @PutMapping
    private ResponseEntity<Object> update(@RequestBody UserContract userContract) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userContract.getUser()));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(200);
    }
}
