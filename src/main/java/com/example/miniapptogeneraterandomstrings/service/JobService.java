package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.model.Job;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {


    public Long maxNumberOfCharsCombinationsFromMaxAndMin(Job job) {
        int min = job.getMin();
        int max = job.getMax();
        int numberOfStrings = job.getNumberOfStrings();
        Long maxNumberOfCombinations = 0L;

        if (max < min || job.getTextCombination() == null || numberOfStrings <= 0) {
            throw new IllegalArgumentException("ERROR: Change input data.");
        }
        for (int i = max; i >= min; i--) {
            maxNumberOfCombinations += getFactorial(i);
        }
        if (maxNumberOfCombinations < numberOfStrings) {
            throw new IllegalArgumentException("ERROR: You want too many combination than can be made.");
        }
        return maxNumberOfCombinations;
    }

    public Long maxNumberOfGivenStringCombinations(String string) {
        return getFactorial(string.length());
    }

    private Long getFactorial(int number) {
        Long factorial = 1L;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return factorial;
    }


    public void generateStrings(List<Job> jobs) {
        if (jobs.size() > 1) {
            Collections.sort(jobs, Comparator.comparingInt(Job::getNumberOfStrings));
        }

    }
}
