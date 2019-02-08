package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GetMostPopularMoviesUseCase {
    private MoviesRepository repository;
    private Disposable disposable;
    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;


    public GetMostPopularMoviesUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, MoviesRepository repository) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.repository = repository;
    }

    public void execute(Consumer<MovieListEntity> capture, Consumer<Throwable> throwableConsumer) {
        disposable = repository.getMostPopularMovies()
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(capture, throwableConsumer);
    }
}
