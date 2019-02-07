package com.nelsonalfo.cleantddapp.data.api;

import com.nelsonalfo.cleantddapp.data.models.MovieDetail;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by nelso on 26/12/2017.
 */
public interface TheMovieDbRestApi {
    @GET("discover/movie")
    Single<MoviesResponse> getMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Single<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Single<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Single<MovieDetail> getMovieDetail(@Path("movie_id") Integer movieId, @Query("api_key") String apiKey);

    @GET("configuration")
    Single<TmdbConfiguration> getConfiguration(@Query("api_key") String apiKey);
}
