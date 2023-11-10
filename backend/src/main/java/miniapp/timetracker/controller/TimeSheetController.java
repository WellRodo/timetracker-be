package miniapp.timetracker.controller;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.User;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @GetMapping("{date}")
    private TimeSheet[] get(@PathVariable Date date) {
        return timeSheetService.GetTimeSheetsByDate(date);
    }
}
