package miniapp.timetracker.repository;

import miniapp.timetracker.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, UUID> {
    TimeSheet[] searchTimeSheetsByDateBetweenAndProjectIdEquals(Date startDate, Date endDate, UUID projectId);
    TimeSheet[] searchTimeSheetsByDateEquals(Date date);
}
