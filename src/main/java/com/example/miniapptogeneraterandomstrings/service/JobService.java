package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.model.Job;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class JobService {
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

    public void generateStrings(List<Job> jobs) throws IOException {
        FileWriter fileWriter = new FileWriter("generatedStrings.txt", true);

        jobs.sort(Comparator.comparingInt(Job::getNumberOfStrings));

        Iterator<Job> iterator = jobs.iterator();
        while (iterator.hasNext()) {
            Job job = iterator.next();
            Set<String> stringList = new HashSet<>();

            int missingLetters = job.getMax() % job.getTextToGenerateRandomString().length();

            String stringWithAddedLetters = job.getTextToGenerateRandomString() + RandomStringUtils.random(missingLetters, job.getTextToGenerateRandomString());

            while (stringList.size() < job.getNumberOfStrings()) {
                stringList.add(RandomStringUtils.random(getRandomNumber(job.getMin(), job.getMax()), stringWithAddedLetters));
            }

            Iterator<String> stringIterator = stringList.iterator();
            while (stringIterator.hasNext()) {
                fileWriter.append(stringIterator.next() + "\n");
            }
            fileWriter.close();
        }

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }
}
