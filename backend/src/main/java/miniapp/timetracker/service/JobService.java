package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;

import java.util.UUID;

public interface JobService {
    public Job GetJob(UUID id);
}
