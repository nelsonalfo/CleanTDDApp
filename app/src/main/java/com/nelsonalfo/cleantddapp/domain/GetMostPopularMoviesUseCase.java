package com.nelsonalfo.cleantddapp.domain;

import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GetMostPopularMoviesUseCase {
    private final Scheduler backScheduler;
    private final Scheduler uiScheduler;

    private MoviesRepository repository;


    public GetMostPopularMoviesUseCase(Scheduler backScheduler, Scheduler uiScheduler, MoviesRepository repository) {
        this.backScheduler = backScheduler;
        this.uiScheduler = uiScheduler;
        this.repository = repository;
    }

    public void execute(Consumer<MoviesResponseEntity> consumerSuccess, Consumer<Throwable> consumerError) {
        final Disposable disposable = repository.getPopularMovies()
                .subscribeOn(uiScheduler)
                .observeOn(backScheduler)
                .subscribe(consumerSuccess, consumerError);
    }
}
