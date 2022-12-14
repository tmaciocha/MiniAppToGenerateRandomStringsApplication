package com.example.miniapptogeneraterandomstrings.controllers;

import com.example.miniapptogeneraterandomstrings.model.Job;
import com.example.miniapptogeneraterandomstrings.service.JobService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    List<Job> jobs = new ArrayList<>();

    //to add new job
    @PostMapping("/")
    public Job addJob(@RequestBody Job job){
        jobService.saveJob(job);
        jobs.add(job);
        return job;
    }

    //to see how many jobs are running
    @GetMapping("/runningJobs")
    public int getJob() {
        return jobService.getAllJobs().size();
    }


    //to generate results
    @GetMapping("/")
    public List<Job> getAllJobs() {
        jobService.generateStrings();
        return new ArrayList<>(jobs);
    }
}
