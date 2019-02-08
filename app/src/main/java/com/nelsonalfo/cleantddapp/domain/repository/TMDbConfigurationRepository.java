package com.nelsonalfo.cleantddapp.domain.repository;

import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import io.reactivex.Single;

public interface TMDbConfigurationRepository {
    Single<TmdbConfigurationEntity> getConfiguration();
}
