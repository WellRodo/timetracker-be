package miniapp.timetracker.model.contracts;


import java.util.UUID;

public class UserContract {

    private String name;
    private UUID jobId;

    public UserContract() {
    }

    public UserContract(String name, UUID jobId) {
        this.name = name;
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }
}
