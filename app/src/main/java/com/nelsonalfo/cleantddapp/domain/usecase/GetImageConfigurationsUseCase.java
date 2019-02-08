package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;
import com.nelsonalfo.cleantddapp.domain.repository.TMDbConfigurationRepository;

import io.reactivex.Single;

public class GetImageConfigurationsUseCase extends SingleUseCase<TmdbConfigurationEntity> {

    private final TMDbConfigurationRepository repository;

    public GetImageConfigurationsUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, TMDbConfigurationRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Single<TmdbConfigurationEntity> buildUseCase() {
        return repository.getConfiguration();
    }
}
