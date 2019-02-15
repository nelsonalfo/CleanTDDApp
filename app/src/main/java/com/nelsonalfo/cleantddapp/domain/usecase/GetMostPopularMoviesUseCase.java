package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetMostPopularMoviesUseCase extends SingleUseCase<MoviesResponseEntity> {
    private MoviesRepository repository;


    public GetMostPopularMoviesUseCase(Scheduler backScheduler, Scheduler uiScheduler, MoviesRepository repository) {
        super(backScheduler, uiScheduler);
        this.repository = repository;
    }

    @Override
    Single<MoviesResponseEntity> buildObservable() {
        return repository.getPopularMovies();
    }
}
