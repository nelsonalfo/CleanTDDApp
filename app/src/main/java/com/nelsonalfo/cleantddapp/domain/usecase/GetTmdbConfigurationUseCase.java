package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetTmdbConfigurationUseCase extends SingleUseCase<TmdbConfigurationEntity> {

    private MoviesRepository repository;

    public GetTmdbConfigurationUseCase(Scheduler backScheduler, Scheduler uiScheduler, MoviesRepository repository) {
        super(backScheduler, uiScheduler);
        this.repository = repository;
    }

    @Override
    Single<TmdbConfigurationEntity> buildObservable() {
        return repository.fetchTmdbConfiguration();
    }

}