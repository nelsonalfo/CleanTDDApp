package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.commons.Constants;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;

import io.reactivex.Single;
import retrofit2.HttpException;

// TODO Se pudiera renombrar a algo mas generico dado que hace algo mas que devolver peliculas
public class TMDbDataSourceRemote implements MoviesDataSource {
    private TheMovieDbRestApi api;

    public TMDbDataSourceRemote(TheMovieDbRestApi api) {
        this.api = api;
    }

    @Override
    public Single<MoviesResponse> fetchPopularMovies() {
        return api.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY)
                .onErrorResumeNext(throwable -> Single.error(resolveError(throwable)));
    }

    @Override
    public Single<TmdbConfiguration> getConfiguration() {
        return api.getConfiguration(Constants.API_KEY)
                .onErrorResumeNext(throwable -> Single.error(resolveError(throwable)));
    }

    // TODO este metodo deberia ser privado
    private Exception resolveError(Throwable t) {
        HttpException httpException = (HttpException) t;
        if (httpException.code() == 500) {
            return new ServerErrorException();
        }
        return null;
    }
}
