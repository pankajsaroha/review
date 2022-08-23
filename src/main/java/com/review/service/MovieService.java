package com.review.service;

import com.review.model.Movie;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    public Movie save (Movie movie);
    public Movie find (int id);
    public Movie update (int id, Movie movie);
    public void delete (int id);
    public void deleteAll ();
    public List<Movie> findAll ();
    public List<Movie> saveAll (List<Movie> movies) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException;
}
