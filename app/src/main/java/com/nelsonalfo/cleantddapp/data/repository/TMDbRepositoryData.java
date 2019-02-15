package com.nelsonalfo.cleantddapp.data.repository;

import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.mappers.TMDbMapper;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import io.reactivex.Single;

public class TMDbRepositoryData implements MoviesRepository {
    private MoviesDataSource dataSource;
    private TMDbMapper mapper;

    public TMDbRepositoryData(MoviesDataSource dataSource, TMDbMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    @Override
    public Single<MoviesResponseEntity> getPopularMovies() {
        return dataSource.fetchPopularMovies().map(mapper::createEntity);
    }

    @Override
    public Single<TmdbConfigurationEntity> fetchTmdbConfiguration() {
        return dataSource.getConfiguration().map(mapper::createConfigurationEntity);
    }
}
