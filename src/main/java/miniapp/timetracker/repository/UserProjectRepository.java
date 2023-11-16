package miniapp.timetracker.repository;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserProjectRepository extends JpaRepository<UserProject, UUID> {
    List<UserProject> searchUserProjectsByUserIdEquals(UUID userId);
}
