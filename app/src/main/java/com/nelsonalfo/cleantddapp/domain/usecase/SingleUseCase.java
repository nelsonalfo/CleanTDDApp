package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class SingleUseCase<T> {
    private ThreadExecutor threadExecutor;
    private PostExecutionThread postExecutionThread;
    private Disposable disposable;


    public SingleUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Single<T> buildUseCase();

    public void execute(Consumer<T> successConsumer, Consumer<Throwable> throwableConsumer) {
        disposable = buildUseCase()
                .subscribeOn(threadExecutor.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(successConsumer, throwableConsumer);
    }
}
