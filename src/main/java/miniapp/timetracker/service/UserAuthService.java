package miniapp.timetracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miniapp.timetracker.model.UserAuth;
import miniapp.timetracker.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class UserAuthService implements UserDetailsService {
    @Autowired
    private  UserAuthRepository userAuthRepository;

    public Optional<UserAuth> findByLogin(String login){
        return userAuthRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        return new org.springframework.security.core.userdetails.User(
                userAuth.getLogin(),
                userAuth.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userAuth.getManagerRole() ? ("ROLE_ADMIN") : ("ROLE_USER")))
        );
    }

    public void createNewUser(UserAuth userAuth){
        userAuthRepository.save(userAuth);
    }
}
