package miniapp.timetracker.service;

import miniapp.timetracker.model.Job;
import miniapp.timetracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobImpl implements  JobService{
    private final JobRepository jobRepository;

    public JobImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public Job GetJob(UUID id){
        return jobRepository.findById(id).get();
    }
}
