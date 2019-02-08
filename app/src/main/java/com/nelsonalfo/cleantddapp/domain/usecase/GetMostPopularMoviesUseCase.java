package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.Single;

public class GetMostPopularMoviesUseCase extends SingleUseCase<MovieListEntity> {
    private MoviesRepository repository;


    public GetMostPopularMoviesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, MoviesRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Single<MovieListEntity> buildUseCase() {
        return repository.getMostPopularMovies();
    }
}
