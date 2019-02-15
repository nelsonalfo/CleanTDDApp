package com.nelsonalfo.cleantddapp.domain.usecase;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public abstract class SingleUseCase<T> {
    private final Scheduler backScheduler;

    private final Scheduler uiScheduler;


    public SingleUseCase(Scheduler backScheduler, Scheduler uiScheduler) {
        this.backScheduler = backScheduler;
        this.uiScheduler = uiScheduler;
    }


    abstract Single<T> buildObservable();

    public void execute(Consumer<T> consumerSuccess, Consumer<Throwable> consumerError) {

        final Disposable disposable = buildObservable()
                .subscribeOn(backScheduler)
                .observeOn(uiScheduler)
                .subscribe(consumerSuccess, consumerError);
    }
}
