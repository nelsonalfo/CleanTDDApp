package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.commons.Constants;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;

import io.reactivex.Single;
import retrofit2.HttpException;

public class MoviesDataSourceRemote implements MoviesDataSource {
    private TheMovieDbRestApi api;

    public MoviesDataSourceRemote(TheMovieDbRestApi api) {

        this.api = api;
    }

    @Override
    public Single<MoviesResponse> fetchPopularMovies() {
        return api.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY).onErrorResumeNext(throwable ->
                Single.error(resolveError(throwable)));
    }

    @Override
    public Single<TmdbConfiguration> getConfiguration() {
        return api.getConfiguration(Constants.API_KEY).onErrorResumeNext(throwable ->
                Single.error(resolveError(throwable)));
    }

    public Exception resolveError(Throwable t) {
        HttpException httpException = (HttpException) t;
        if (httpException.code() == 500) {
            return new ServerErrorException();
        }
        return null;
    }
}
