package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.model.Job;
import org.springframework.stereotype.Service;

@Service
public class JobService {


    public Long maxNumberOfCharsCombinationsFromMaxAndMin(Job job) {
        int min = job.getMin();
        int max = job.getMax();
        int numberOfStrings = job.getNumberOfStrings();
        Long maxNumberOfCombinations = 0L;

        if (max < min || job.getTextCombination() == null || numberOfStrings <= 0) {
        throw new IllegalArgumentException("Wrong Data");
        }
        for (int i = max; i >= min; i--) {
            maxNumberOfCombinations+=getFactorial(i);
        }
        if(maxNumberOfCombinations < numberOfStrings) {
            throw new IllegalArgumentException("You want to many combination that can be made.");
        }
        return maxNumberOfCombinations;
    }

    public Long maxNumberOfGivenStringCombinations(String string){
        Long factorial = 1L;
        for (int i = 1; i <=string.length() ; i++) {
            factorial*=i;
        }
        return factorial;
    }

    private Long getFactorial(int number){
        Long factorial = 1L;
        for (int i = 1; i <= number ; i++) {
            factorial*=i;
        }
        return factorial;
    }


    private Long numberOfPossibilities(int min, int max, String textCombination, int numberOfStrings) {
        Long factorial = 1L;
        Long possibilities = 0L;
        for (int i = max; i < min; i--) {
            possibilities+=factorial;
            for (int j = 1; j < i; j++) {
                factorial *=j;
            }
        }


        return possibilities;
    }
}
