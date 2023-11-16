package miniapp.timetracker.repository;

import miniapp.timetracker.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
