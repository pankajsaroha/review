package com.review.service.impl;

import com.review.model.Movie;
import com.review.repository.MovieRepository;
import com.review.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie find(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie update(int id, Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void delete(int id) {
        movieRepository.deleteById(id);
    }

    @Override
    public void deleteAll () {
        movieRepository.deleteAll();
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> saveAll(List<Movie> movies) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters parameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .addString("data", String.valueOf(movies))
                .toJobParameters();
        jobLauncher.run(job, parameters);
        //return movieRepository.saveAll(movies);
        return movies;
    }
}
