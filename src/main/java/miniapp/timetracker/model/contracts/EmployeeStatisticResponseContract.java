package miniapp.timetracker.model.contracts;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EmployeeStatisticResponseContract {
    List<WeekWorkTime> workWeeks;

    public EmployeeStatisticResponseContract() {
    }

    public EmployeeStatisticResponseContract(List<WeekWorkTime> workWeeks) {
        this.workWeeks = workWeeks;
    }

    public List<WeekWorkTime> getWorkWeeks() {
        return workWeeks;
    }

    public void setWorkWeeks(List<WeekWorkTime> workWeeks) {
        this.workWeeks = workWeeks;
    }

    public void addWorkWeeks(WeekWorkTime workWeek) {
        this.workWeeks.add(workWeek);
    }
}
