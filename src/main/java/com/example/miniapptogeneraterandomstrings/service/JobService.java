package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.model.Job;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class JobService {
    public int jobInProgress = 0;
    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    public Long maxNumberOfCharsCombinationsFromMaxAndMin(Job job) {
        int min = job.getMin();
        int max = job.getMax();
        int numberOfStrings = job.getNumberOfStrings();
        Long maxNumberOfCombinations = 0L;

        if (max < min || job.getTextToGenerateRandomString() == null || numberOfStrings <= 0) {
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

    public void generateStrings(List<Job> jobs)  {

        jobs.sort(Comparator.comparingInt(Job::getNumberOfStrings));
        Iterator<Job> iterator = jobs.iterator();

        jobInProgress = jobs.size();

        while (iterator.hasNext()) {

            ExecutorService executorService = Executors.newFixedThreadPool(jobs.size());
            Future<?> f = executorService.submit(() -> {
                Job job = iterator.next();
                logger.info(job.getTextToGenerateRandomString() + " start: " + System.currentTimeMillis());
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("generatedStringsFromGivenString" + job.getTextToGenerateRandomString().toUpperCase() + ".txt", true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Set<String> stringList = new HashSet<>();

                int missingLetters = job.getMax() % job.getTextToGenerateRandomString().length();

                String stringWithAddedLetters = job.getTextToGenerateRandomString() + RandomStringUtils.random(missingLetters, job.getTextToGenerateRandomString());

                while (stringList.size() < job.getNumberOfStrings()) {
                    stringList.add(RandomStringUtils.random(getRandomNumber(job.getMin(), job.getMax()), stringWithAddedLetters));
                }
                try {
                    delay(8);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Iterator<String> stringIterator = stringList.iterator();
                while (stringIterator.hasNext()) {
                    try {
                        fileWriter.append(stringIterator.next() + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    logger.info(job.getTextToGenerateRandomString() + " STOP: " + System.currentTimeMillis());
                    fileWriter.close();
                    jobInProgress--;
                    logger.info("jobInProgress: " + jobInProgress);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            executorService.shutdown();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }


    private void delay(int second) throws InterruptedException {
        TimeUnit.SECONDS.sleep(second);
    }
}
