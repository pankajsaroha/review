package com.review.config;

import com.review.model.Movie;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class CustomItemReader implements ItemReader<Movie> {
    @Override
    public Movie read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        return null;
    }
}
