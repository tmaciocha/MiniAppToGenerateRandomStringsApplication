package com.example.miniapptogeneraterandomstrings.service;

import com.example.miniapptogeneraterandomstrings.controllers.JobController;
import com.example.miniapptogeneraterandomstrings.model.Job;
import com.example.miniapptogeneraterandomstrings.repository.JobRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class JobServiceTest {

    @Mock
    JobRepository jobRepository;
    @InjectMocks
    JobController jobController;
    @InjectMocks
    private JobService underTest;

   @BeforeEach
    void setUp() {
        underTest = new JobService(jobRepository);
    }

    private List<Job> prepareWrongMockData() {
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job(1L, 4, 2, "qwerty", 15));
        jobList.add(new Job(2L, 4, 8, "qwertydscx", 15));
        jobList.add(new Job(3L, 4, 5, "qwerty", 145));
        jobList.add(new Job(4L, -4, 5, "qwerty", 14));
        jobList.add(new Job(5L, 4, -5, "qwerty", 14));
        jobList.add(new Job(6L, 4, 5, "", 14));
        jobList.add(new Job(7L, 4, 5, "qwerty", 0));
        jobList.add(new Job(8L, 4, 5, "qwerty", -1));
        jobList.add(new Job(9L, 4, 5, null, 14));
        return jobList;
    }

    private List<Job> prepareGoodMockData() {
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job(1L, 2, 4, "four", 15));
        jobList.add(new Job(2L, 4, 8, "qwertyds", 155));
        jobList.add(new Job(3L, 4, 6, "qwerty", 145));
        jobList.add(new Job(4L, 4, 7, "qwert", 5040));
        jobList.add(new Job(5L, 3, 3, "wtx", 6));
        return jobList;
    }

    @Test
    public void should_not_saveJobs() {
//        given
        Job job1 = prepareWrongMockData().get(0);
        Job job2 = prepareWrongMockData().get(1);
        Job job3 = prepareWrongMockData().get(2);
        Job job4 = prepareWrongMockData().get(3);
        Job job5 = prepareWrongMockData().get(4);
        Job job6 = prepareWrongMockData().get(5);
        Job job7 = prepareWrongMockData().get(6);
        Job job8 = prepareWrongMockData().get(7);
        Job job9 = prepareWrongMockData().get(8);
//        when
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job7);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(job9);
        });
    }

    @Test
    public void should_saveJobs() {
//        given
//        then
        assertTrue(underTest.saveJob(prepareGoodMockData().get(0)));
        assertTrue(underTest.saveJob(prepareGoodMockData().get(1)));
        assertTrue(underTest.saveJob(prepareGoodMockData().get(2)));
        assertTrue(underTest.saveJob(prepareGoodMockData().get(3)));
        assertTrue(underTest.saveJob(prepareGoodMockData().get(4)));
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.saveJob(prepareWrongMockData().get(0));
        });
    }


    @Test
    public void should_return_Long_number_from_maxNumberOfCharsCombinationsFromMaxAndMin() {
//      given
        Job job1 = prepareGoodMockData().get(0);
        Job job2 = prepareGoodMockData().get(1);
        Job job3 = prepareGoodMockData().get(2);
        Job job4 = prepareGoodMockData().get(3);
        Job job5 = prepareGoodMockData().get(4);
//        then
        Assertions.assertEquals(32L, underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job1));
        Assertions.assertEquals(46224L, underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job2));
        Assertions.assertEquals(864L, underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job3));
        Assertions.assertEquals(5904L, underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job4));
        Assertions.assertEquals(6L, underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job5));
    }


    @Test
    public void should_throw_IllegalArgumentException_maxNumberOfCharsCombinationsFromMaxAndMin() {
        //    given
        List<Job> wrongJobList = prepareWrongMockData();
        Job job1 = wrongJobList.get(0);//max bigger than min
        Job job2 = wrongJobList.get(1);//ERROR: Source string length can't be larger than max, please change input.
        Job job3 = wrongJobList.get(2);//You want too many combination than can be made.
        Job job6 = wrongJobList.get(5);//string is ""
        Job job7 = wrongJobList.get(6);//number of strings is 0
        Job job8 = wrongJobList.get(7);//number of strings is -1
        Job job9 = wrongJobList.get(8);//string is null

//       then
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job7);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job8);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            underTest.maxNumberOfCharsCombinationsFromMaxAndMin(job9);
        });
    }


    @Test
    void maxNumberOfGivenStringCombinations() {
//        given
        String string = "123456789";
//        then
        assertEquals(362880, underTest.maxNumberOfGivenStringCombinations(string));

    }

    @Test
    void getRandomNumber() {
//        given
        List<Integer> integers = new ArrayList<>();
        Collections.addAll(integers, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        int min = 5;
        int max = 15;
//      then
        for (int i = 0; i < 100; i++) {
            assertTrue(integers.contains(underTest.getRandomNumber(min, max)));
        }
    }


    @Test
    @Disabled
    void should_generateStrings() {


    }


    @Test
    void getNumberJobsInProgress() {
//         given
        List<Job> jobList = new ArrayList<>();
        Job job1 = prepareGoodMockData().get(0);
        Job job2 = prepareGoodMockData().get(1);
//        when
        when(jobRepository.save(any(Job.class))).thenReturn(job1);
        jobList.add(jobRepository.save(job1));
        when(jobRepository.save(any(Job.class))).thenReturn(job2);
        jobList.add(jobRepository.save(job2));
        when(jobRepository.findAll()).thenReturn(jobList);
//        then
        assertThat(underTest.getAllJobs().size()).isEqualTo(2);
    }

    @Test
    void no_getAllJobs() {
//        given
        List<Job> jobs = jobRepository.findAll();
//        then
        assertThat(jobs.size()).isEqualTo(0);
    }

    @Test
    void getAllJobs() {
//         given
        List<Job> jobList = new ArrayList<>();
        Job job1 = prepareGoodMockData().get(0);
        Job job2 = prepareGoodMockData().get(1);
//        when
        when(jobRepository.save(any(Job.class))).thenReturn(job1);
        jobList.add(jobRepository.save(job1));
        when(jobRepository.save(any(Job.class))).thenReturn(job2);
        jobList.add(jobRepository.save(job2));
        when(jobRepository.findAll()).thenReturn(jobList);
//        then
        assertAll(
                () -> assertThat(underTest.getAllJobs().get(0).getTextToGenerateRandomString()).isEqualTo("four"),
                () -> assertThat(underTest.getAllJobs().get(1).getTextToGenerateRandomString()).isEqualTo("qwertyds")
        );
    }

    @Test
    void countMissingLetters() {
       Job job = prepareGoodMockData().get(3);
       assertEquals(2,underTest.countMissingLetters(job));
       assertNotEquals(3, underTest.countMissingLetters(job));
    }
}