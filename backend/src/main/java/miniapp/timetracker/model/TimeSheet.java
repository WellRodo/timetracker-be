package miniapp.timetracker.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class TimeSheet {
    UUID projectId;
    UUID userId;
    Time worktime;
    String description;
    Date date;
    Boolean isFinished;
}
