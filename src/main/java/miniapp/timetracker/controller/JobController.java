package miniapp.timetracker.controller;

import miniapp.timetracker.model.contracts.JobContract;
import miniapp.timetracker.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dictionary")
@CrossOrigin
public class JobController {

    @Autowired
    private JobService jobService;
    @GetMapping("/job/all")
    private ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getAll());
    }

    @GetMapping("/job/{id}")
    private ResponseEntity<Object> getById(@PathVariable("id")UUID ID) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.getByID(ID));
    }

    @PutMapping("/job")
    private ResponseEntity<Object> update(@RequestBody JobContract jobContract) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.update(jobContract.getJob()));
    }

    @PostMapping("/job")
    private ResponseEntity<Object> create(@RequestBody JobContract jobContract) {
        return ResponseEntity.status(HttpStatus.OK).body(jobService.create(jobContract.getJob()));
    }

    @DeleteMapping("/job/{id}")
    private ResponseEntity<Object> delete(@PathVariable("id")UUID ID) {
        jobService.delete(ID);
        return ResponseEntity.status(HttpStatus.OK).body("200");
    }
}
