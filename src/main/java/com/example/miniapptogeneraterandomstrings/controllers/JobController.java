package com.example.miniapptogeneraterandomstrings.controllers;

import com.example.miniapptogeneraterandomstrings.model.Job;
import com.example.miniapptogeneraterandomstrings.service.JobService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    List<Job> jobs = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);


    @PostMapping("/")
    public Job addJob(@RequestBody Job job){
        jobs.add(job);

        jobService.maxNumberOfCharsCombinationsFromMaxAndMin(job);
        logger.info("max number of string with Min and Max values. Min: " + job.getMin() + ", max: " + job.getMax() + " = "
                + jobService.maxNumberOfCharsCombinationsFromMaxAndMin(job));

        jobService.maxNumberOfGivenStringCombinations(job.getTextCombination());
        logger.info("maxNumberOfGivenStringCombinations for given string: " + job.getTextCombination() + " = "
                + jobService.maxNumberOfGivenStringCombinations(job.getTextCombination()));

        jobService.generateStrings(jobs);

        return job;
    }

    @GetMapping("/runningJobs")
    public int getJob(){
        return jobs.size();
    }


    @GetMapping("/")
    public List<Job> getAllJobs(){
        return new ArrayList<>(jobs);
    }




}
