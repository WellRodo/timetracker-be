package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;
import miniapp.timetracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobImpl implements  JobService{
    private final JobRepository jobRepository;

    public JobImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job getByID(UUID id){
        return jobRepository.findById(id).get();
    }

    @Override
    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job update(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void delete(UUID id) {
        jobRepository.deleteById(id);
    }

    @Override
    public Job create(Job job) {
        return jobRepository.save(job);
    }
}
