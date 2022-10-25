package com.example.miniapptogeneraterandomstrings.Controllers;

import com.example.miniapptogeneraterandomstrings.Model.Job;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class JobController {
    ConcurrentHashMap<Integer, Job> jobs = new ConcurrentHashMap<>();


    @PostMapping("/")
    public Job addJob(@RequestBody Job job){
        jobs.put(job.getId(), job);
        return job;
    }

    @GetMapping("/runningJobs")
    public int getJob(){
        return jobs.size();
    }


    @GetMapping("/")
    public List<Job> getAllJobs(){
        return new ArrayList<Job>(jobs.values());
    }




}
