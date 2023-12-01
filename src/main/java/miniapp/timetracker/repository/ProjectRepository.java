package miniapp.timetracker.repository;

import jakarta.transaction.Transactional;
import miniapp.timetracker.model.Project;
import miniapp.timetracker.model.contracts.WeekWorkTime;
import miniapp.timetracker.model.contracts.WeekWorkTimeInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    @Query(value = "SELECT " +
            "tp.name AS projectName, :startDate AS weekDate, (SELECT sum(work_time) from tt_time_sheet tts " +
            "WHERE tts.project_id = tp.id AND tts.is_finished = true " +
            "AND tts.date between :startDate AND :endDate) AS workTime " +
            "FROM tt_project tp " +
            "WHERE tp.id IN (" +
            "SELECT project_id " +
            "FROM tt_user_project tup " +
            "WHERE user_id = :employee_id " +
            "AND project_id IN (:project_ids))", nativeQuery = true)
    List<WeekWorkTimeInterface> getWorkTimeByWeek(@Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("employee_id") UUID employeeID, @Param("project_ids") List<UUID> projectIDs );
}
