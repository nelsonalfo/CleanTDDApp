package com.nelsonalfo.cleantddapp.commons.rxjava;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class BackgroundThread implements ThreadExecutor {
    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
