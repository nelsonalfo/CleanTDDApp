package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.data.models.MovieResume;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;

import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public final class StubFactory {
    private StubFactory() {
    }

    public static MoviesResponse createMovieResponseStub() {
        final MoviesResponse moviesResponse = new MoviesResponse();
        moviesResponse.page = 1;
        moviesResponse.totalPages = 1;
        moviesResponse.totalResults = 2;

        moviesResponse.results = Arrays.asList(new MovieResume(1, "Titanic"), new MovieResume(2, "Avatar"));

        return moviesResponse;
    }

    public static HttpException createError401() {
        return new HttpException(Response.error(401, ResponseBody.create(MediaType.parse("application/json"),
                "{\n" +
                        "     \"message\": {\n" +
                        "         \"code\": 1301,\n" +
                        "         \"title\": \"Ha ocurrido un error\",\n" +
                        "         \"body\": \"Número de celular invalido\"\n" +
                        "     }\n" +
                        "}")));
    }
}