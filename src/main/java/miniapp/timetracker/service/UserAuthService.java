package miniapp.timetracker.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miniapp.timetracker.model.UserAuth;
import miniapp.timetracker.model.contracts.CustomUserDetails;
import miniapp.timetracker.model.contracts.UserRegisterContract;
import miniapp.timetracker.repository.UserAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class UserAuthService implements UserDetailsService {
    @Autowired
    private  UserAuthRepository userAuthRepository;

    public Optional<UserAuth> findByLogin(String login){
        return userAuthRepository.findByLogin(login);
    }
    public Optional<UserAuth> findByUserID(UUID userId){return  userAuthRepository.findByUserId(userId);}

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        return new CustomUserDetails(
                userAuth.getLogin(),
                userAuth.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(userAuth.getManagerRole() ? ("ROLE_ADMIN") : ("ROLE_USER"))
                ), userAuth.getUser().getId()
        );
    }

    public UserAuth createNewUser(UserRegisterContract userRegisterContract){
        UserAuth userAuth = userRegisterContract.getUserAuth();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userAuth.setPassword(bCryptPasswordEncoder.encode(userAuth.getPassword()));
        return userAuthRepository.save(userAuth);
    }

    public UserAuth updateUserAuth(UserRegisterContract userRegisterContract){
        return userAuthRepository.save(userRegisterContract.getUserAuth());
    }
    public Object getAllUserAuth(){
        List<UserAuth> userAuths = (List<UserAuth>) userAuthRepository.findAll();
        List<UserRegisterContract> userRegisterContracts = new ArrayList<>();
        for(UserAuth userAuth: userAuths){
            if(userAuth.getUser().getIsActive())
                userRegisterContracts.add(new UserRegisterContract(userAuth));
        }
        return userRegisterContracts;

    }
}
