package com.nelsonalfo.cleantddapp.domain.repository;

import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;

import io.reactivex.Single;

public interface MoviesRepository {
    Single<MovieListEntity> getMostPopularMovies();
}
