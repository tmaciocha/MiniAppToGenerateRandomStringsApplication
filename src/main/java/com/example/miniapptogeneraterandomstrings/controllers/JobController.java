package com.example.miniapptogeneraterandomstrings.controllers;

import com.example.miniapptogeneraterandomstrings.model.Job;
import com.example.miniapptogeneraterandomstrings.service.JobService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    List<Job> jobs = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);


    //to recive new job
    @PostMapping("/")
    public Job addJob(@RequestBody Job job){
        jobs.add(job);

        jobService.maxNumberOfCharsCombinationsFromMaxAndMin(job);
        logger.info("max number of string with Min and Max values. Min: " + job.getMin() + ", max: " + job.getMax() + " = "
                + jobService.maxNumberOfCharsCombinationsFromMaxAndMin(job));

        jobService.maxNumberOfGivenStringCombinations(job.getTextToGenerateRandomString());
        logger.info("maxNumberOfGivenStringCombinations for given string: " + job.getTextToGenerateRandomString() + " = "
                + jobService.maxNumberOfGivenStringCombinations(job.getTextToGenerateRandomString()));
        return job;
    }

    //to see how many jobs are running
    @GetMapping("/runningJobs")
    public int getJob(){
        return jobs.size();
    }


    //to generate results
    @GetMapping("/")
    public List<Job> getAllJobs() throws FileNotFoundException {
        jobService.generateStrings(jobs);
        return new ArrayList<>(jobs);
    }




}
