package com.nelsonalfo.cleantddapp.data.datasource;

import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;

import io.reactivex.Single;

public interface MoviesDataSource {
    Single<MoviesResponse> fetchPopularMovies();

    Single<TmdbConfiguration> getConfiguration();
}
