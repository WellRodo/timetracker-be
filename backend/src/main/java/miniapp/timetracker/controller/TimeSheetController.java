package miniapp.timetracker.controller;

import miniapp.timetracker.model.TimeSheet;
import miniapp.timetracker.model.User;
import miniapp.timetracker.service.TimeSheetService;
import miniapp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/timesheet")
public class TimeSheetController {
    @Autowired
    private TimeSheetService timeSheetService;

    @GetMapping("/day/{date}")
    private TimeSheet[] get(@PathVariable String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = formatter.parse(date);
        return timeSheetService.GetTimeSheetsByDate(dt);
    }

    @PostMapping("/day")
    private void post(@RequestBody TimeSheet timeSheet){
        timeSheetService.SaveTimeSheet(timeSheet);
    }
}
