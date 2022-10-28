package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.model.Job;
import com.example.miniapptogeneraterandomstrings.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    private Long maxNumberOfCharsCombinationsFromMaxAndMin(Job job) {
        int min = job.getMin();
        int max = job.getMax();
        int numberOfStrings = job.getNumberOfStrings();
        Long maxNumberOfCombinations = 0L;

        if (max < min ||
                job.getTextToGenerateRandomString() == null ||
                numberOfStrings <= 0 ||
                job.getTextToGenerateRandomString().equals("")) {
            throw new IllegalArgumentException("ERROR: Change input data.");
        }

        for (int i = max; i >= min; i--) {
            maxNumberOfCombinations += getFactorial(i);
        }

        if (maxNumberOfCombinations < numberOfStrings) {
            throw new IllegalArgumentException("ERROR: You want too many combination than can be made.");
        }

        if (max < job.getTextToGenerateRandomString().length()) {
            throw new IllegalArgumentException("ERROR: Source string length can't be larger than max, please change input.");
        }

        return maxNumberOfCombinations;
    }

    private Long maxNumberOfGivenStringCombinations(String string) {
        return getFactorial(string.length());
    }

    public void generateStrings() {
        List<Job> jobs = getJobs();

        jobs.sort(Comparator.comparingInt(Job::getNumberOfStrings));

        Iterator<Job> iterator = jobs.iterator();

        while (iterator.hasNext()) {
            ExecutorService executorService = Executors.newFixedThreadPool(jobs.size());
            Future<?> f = executorService.submit(() -> {
                Job job = iterator.next();
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("generatedStringsFromGivenString" + job.getTextToGenerateRandomString().toUpperCase() + "_" + job.getId() + ".txt", true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Set<String> stringList = new HashSet<>();

                while (stringList.size() < job.getNumberOfStrings()) {
                    stringList.add(RandomStringUtils
                            .random(getRandomNumber(job.getMin(), job.getMax()),
                                    job.getTextToGenerateRandomString() + RandomStringUtils.random(countMissingLetters(job),
                                            job.getTextToGenerateRandomString())));
                }

                Iterator<String> stringIterator = stringList.iterator();
                while (stringIterator.hasNext()) {
                    try {
                        fileWriter.append(stringIterator.next()).append("\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    fileWriter.close();
                    removeJob(job.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            executorService.shutdown();
        }
    }

    private int countMissingLetters(Job job) {
        return job.getMax() % job.getTextToGenerateRandomString().length();
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }

    public void saveJob(Job job) {
        if (maxNumberOfCharsCombinationsFromMaxAndMin(job) >= maxNumberOfGivenStringCombinations(job.getTextToGenerateRandomString())) {
            jobRepository.save(job);
        }
    }

    private Long getFactorial(int number) {
        Long factorial = 1L;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return factorial;
    }

    public int getNumberJobsInProgress() {
        return jobRepository.findAll().size();
    }

    private List<Job> getJobs() {
        return jobRepository.findAll();
    }

    private void removeJob(Long id) {
        jobRepository.deleteById(id);
    }
}