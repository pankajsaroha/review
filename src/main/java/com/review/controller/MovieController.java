package com.review.controller;

import com.review.model.Movie;
import com.review.service.MovieService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/movies/one")
    public Movie save (@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping("/movies/one")
    public Movie find (@RequestParam int id) {
        return movieService.find(id);
    }

    @PatchMapping("/movies")
    public Movie update (@RequestParam int id, @RequestBody Movie movie) {
        return movieService.update(id, movie);
    }

    @DeleteMapping("/movies/one")
    public String delete (@RequestParam int id) {
        movieService.delete(id);
        return "Deleted!!!";
    }

    @DeleteMapping("/movies")
    public String deleteAll () {
        movieService.deleteAll();
        return "Deleted!!!";
    }

    @GetMapping("/movies")
    public List<Movie> findAll () {
        return movieService.findAll();
    }

    @PostMapping("/movies")
    public List<Movie> saveAll (@RequestBody List<Movie> movies) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        return movieService.saveAll(movies);
    }
}
