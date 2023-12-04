package miniapp.timetracker.controller;

import miniapp.timetracker.model.UserAuth;
import miniapp.timetracker.model.contracts.*;
import miniapp.timetracker.service.EmailService;
import miniapp.timetracker.service.JwtTokenUtils;
import miniapp.timetracker.service.UserAuthService;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.memory.UserAttribute;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping
public class AuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @PostMapping("/auth")
    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            CustomUserDetails userDetails = userAuthService.loadUserByUsername(authRequest.getUsername());
            String token = jwtTokenUtils.generateToken(userDetails);
            UserAuth userAuth = userAuthService.findByLogin(userDetails.getUsername()).get();
            if (userAuth.getUser().getIsActive() == false) { throw new CustomException(HttpStatus.UNAUTHORIZED, ("Попытка войти в удаленный аккаунт"));}
            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUserId(), userAuth.getManagerRole()));
        } catch (BadCredentialsException e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Неправильный логин или пароль");
        } catch (CustomException e){
            throw e;
        }
    }

    @PostMapping("/register/{email}")
    public ResponseEntity<Object> register(@PathVariable("email") String email, @RequestBody UserRegisterContract userRegisterContract){
        String pass = emailService.sendSimpleMessage(email);
        userService.saveUser(userRegisterContract);
        userRegisterContract.getUserAuth().setLogin(email);
        userRegisterContract.getUserAuth().setPassword(pass);
        return ResponseEntity.ok(userAuthService.createNewUser(userRegisterContract));
    }

    @GetMapping("/userAuth/get")
    public ResponseEntity<Object> getAllUsersAuth(){
        return ResponseEntity.ok(userAuthService.getAllUserAuth());
    }

}
