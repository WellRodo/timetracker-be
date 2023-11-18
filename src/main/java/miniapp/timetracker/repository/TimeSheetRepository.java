package miniapp.timetracker.repository;

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

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE TimeSheet tt SET tt.isFinished=(:name) WHERE tt.id IN(:id)")
//    public void updateFinished(@Param("name")Boolean finished, @Param("id")List<UUID> id);
}
