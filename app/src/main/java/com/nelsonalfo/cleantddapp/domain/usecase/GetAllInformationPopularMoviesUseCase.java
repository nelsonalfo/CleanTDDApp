package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetAllInformationPopularMoviesUseCase extends SingleUseCase<MoviesResponseEntity> {

    private final GetMostPopularMoviesUseCase moviesUseCase;
    private final GetTmdbConfigurationUseCase configurationUseCase;

    public GetAllInformationPopularMoviesUseCase(Scheduler backScheduler,
                                                 Scheduler uiScheduler,
                                                 GetMostPopularMoviesUseCase moviesUseCase,
                                                 GetTmdbConfigurationUseCase configurationUseCase) {
        super(backScheduler, uiScheduler);
        this.moviesUseCase = moviesUseCase;
        this.configurationUseCase = configurationUseCase;
    }

    @Override
    Single<MoviesResponseEntity> buildObservable() {
        return moviesUseCase.buildObservable()
                .zipWith(configurationUseCase.buildObservable(),
                        this::getMoviesResponseEntityWithUrl);
    }

    private MoviesResponseEntity getMoviesResponseEntityWithUrl(MoviesResponseEntity moviesEntity, TmdbConfigurationEntity configuration) {
        for (MovieResumeEntity movie : moviesEntity.results) {
            movie.posterUrl = getPosterUrl(configuration, movie);
        }
        return moviesEntity;
    }

    private String getPosterUrl(TmdbConfigurationEntity configuration, MovieResumeEntity movie) {
        return configuration.images.baseUrl +
                configuration.images.posterSizes.get(0) +
                movie.posterPath;
    }
}
