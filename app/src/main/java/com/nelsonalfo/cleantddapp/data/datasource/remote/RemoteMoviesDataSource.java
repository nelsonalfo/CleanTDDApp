package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.commons.exceptions.ClientErrorException;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.commons.exceptions.UnknownErrorException;
import com.nelsonalfo.cleantddapp.data.api.Constants;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import retrofit2.HttpException;

public class RemoteMoviesDataSource implements MoviesDataSource {

    private final TheMovieDbRestApi api;

    public RemoteMoviesDataSource(TheMovieDbRestApi api) {
        this.api = api;
    }

    @Override
    public Single<MoviesResponse> getMostPopularMovies() {
        return api.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY)
                .onErrorResumeNext(this::handleError);
    }

    private SingleSource<? extends MoviesResponse> handleError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return handleHttpException(throwable);
        }

        return Single.error(throwable);
    }

    private SingleSource<? extends MoviesResponse> handleHttpException(Throwable throwable) {
        final HttpException exception = (HttpException) throwable;

        switch (exception.code()) {
            case 401:
                return Single.error(new ClientErrorException());
            case 500:
                return Single.error(new ServerErrorException());
            default:
                return Single.error(new UnknownErrorException());
        }
    }
}
