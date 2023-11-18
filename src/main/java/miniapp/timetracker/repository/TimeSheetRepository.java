package miniapp.timetracker.repository;

import jakarta.transaction.Transactional;
import miniapp.timetracker.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, UUID> {
    List<TimeSheet> searchTimeSheetsByDateBetweenAndProjectIdEquals(LocalDate startDate, LocalDate endDate, UUID projectId);
    List<TimeSheet> searchTimeSheetsByDateEquals(LocalDate date);
    List<TimeSheet> searchTimeSheetsByDateBetween(LocalDate startDate, LocalDate endDate);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE tt_time_sheet SET is_finished = (:name) WHERE id IN (:id)", nativeQuery = true)
    public void updateFinished(@Param("name")Boolean finished, @Param("id")List<UUID> id);
}
