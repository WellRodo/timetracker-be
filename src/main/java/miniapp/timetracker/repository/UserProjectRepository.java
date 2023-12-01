package miniapp.timetracker.repository;

import jakarta.transaction.Transactional;
import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.UserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserProjectRepository extends JpaRepository<UserProject, UUID> {
    public static final UUID emptyProjectUUID = UUID.fromString("82a8ed50-906b-11ee-b9d1-0242ac120004");

    List<UserProject> searchUserProjectsByUserIdEquals(UUID userId);
    List<UserProject> searchUserProjectsByProjectIdEquals(UUID projectId);
    List<UserProject> searchUserProjectsByUserIdAndProjectIdEquals(UUID userId, UUID projectId);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE from tt_user_project where (project_id = (:projectId) and " +
            "user_id NOT IN (:usersId))", nativeQuery = true)
    public void deleteUsersFromProject(@Param("projectId")UUID projectId, @Param("usersId")List<UUID> usersId);
}
