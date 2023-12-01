package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.JwtRequest;
import miniapp.timetracker.model.contracts.JwtResponse;
import miniapp.timetracker.model.contracts.CustomException;
import miniapp.timetracker.model.contracts.CustomUserDetails;
import miniapp.timetracker.service.JwtTokenUtils;
import miniapp.timetracker.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping
    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            CustomUserDetails userDetails = userAuthService.loadUserByUsername(authRequest.getUsername());
            String token = jwtTokenUtils.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token, userDetails.getUserId()));
        }catch (BadCredentialsException e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Неправильный логин или пароль");
        }
    }

}
