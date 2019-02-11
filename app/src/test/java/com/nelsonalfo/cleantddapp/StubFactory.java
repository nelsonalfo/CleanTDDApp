package com.nelsonalfo.cleantddapp;

import com.nelsonalfo.cleantddapp.data.models.MovieResume;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;

import java.util.Arrays;

public class StubFactory {
    public static MoviesResponse createMoviesResponseStub() {
        final MoviesResponse moviesResponse = new MoviesResponse();
        moviesResponse.results = Arrays.asList(
                new MovieResume(1, "Titanic"),
                new MovieResume(2, "Avatar"));

        return moviesResponse;
    }
}
