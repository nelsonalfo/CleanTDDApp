package com.nelsonalfo.cleantddapp.data.datasource;

import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;

import io.reactivex.Single;

public interface MoviesDataSource {
    Single<MoviesResponse> getMostPopularMovies();
}
