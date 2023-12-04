package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;

import java.util.List;
import java.util.UUID;

public interface JobService {
    public Job getByID(UUID id);
    public List<Job> getAll();
    public Job update(Job job);
    public void delete(UUID id);
    public Job create(Job job);
}
