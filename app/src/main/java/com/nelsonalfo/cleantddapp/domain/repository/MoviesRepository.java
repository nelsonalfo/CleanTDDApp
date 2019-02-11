package com.nelsonalfo.cleantddapp.domain.repository;

import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import io.reactivex.Single;

public interface MoviesRepository {
    Single<MoviesResponseEntity> getPopularMovies();

    Single<TmdbConfigurationEntity> fetchTmdbConfiguration();
}
