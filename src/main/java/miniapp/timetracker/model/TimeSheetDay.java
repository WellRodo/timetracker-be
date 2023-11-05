package miniapp.timetracker.model;

import java.sql.Time;
import java.util.Date;
import java.util.Dictionary;

public class TimeSheetDay {
    Project project;
    Time worktime;
    String description;
    User user;
    Date date;
}
