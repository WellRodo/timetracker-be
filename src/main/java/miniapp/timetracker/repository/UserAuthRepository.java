package miniapp.timetracker.repository;

import miniapp.timetracker.model.UserAuth;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepository extends CrudRepository<UserAuth, UUID> {
    Optional<UserAuth> findByLogin(String login);

    Optional<UserAuth> findByUserId(UUID userId);
}
