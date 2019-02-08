package com.nelsonalfo.cleantddapp.commons.rxjava;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
