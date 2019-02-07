package com.nelsonalfo.cleantddapp.data.repository;

import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.models.MovieResume;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import java.util.ArrayList;

import io.reactivex.Single;

class MoviesRepositoryData implements MoviesRepository {
    private MoviesDataSource dataSource;

    public MoviesRepositoryData(MoviesDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<MovieListEntity> getMostPopularMovies() {
        return dataSource.getMostPopularMovies().map(this::mapToEntity);
    }

    private MovieListEntity mapToEntity(MoviesResponse moviesResponse) {
        final MovieListEntity movieListEntity = new MovieListEntity();
        movieListEntity.page = moviesResponse.page;
        movieListEntity.totalPages = moviesResponse.totalPages;
        movieListEntity.totalResults = moviesResponse.totalResults;
        movieListEntity.results = getMovieResumeEntities(moviesResponse);

        return movieListEntity;
    }

    private ArrayList<MovieResumeEntity> getMovieResumeEntities(MoviesResponse moviesResponse) {
        final ArrayList<MovieResumeEntity> results = new ArrayList<>();
        for (MovieResume movieResume : moviesResponse.results) {
            final MovieResumeEntity movieResumeEntity = new MovieResumeEntity();
            movieResumeEntity.adult = movieResume.adult;
            movieResumeEntity.voteCount = movieResume.voteCount;
            movieResumeEntity.id = movieResume.id;
            movieResumeEntity.video = movieResume.video;
            movieResumeEntity.voteAverage = movieResume.voteAverage;
            movieResumeEntity.title = movieResume.title;
            movieResumeEntity.popularity = movieResume.popularity;
            movieResumeEntity.posterPath = movieResume.posterPath;
            movieResumeEntity.originalLanguage = movieResume.originalLanguage;
            movieResumeEntity.originalTitle = movieResume.originalTitle;
            movieResumeEntity.genreIds = movieResume.genreIds;
            movieResumeEntity.backdropPath = movieResume.backdropPath;
            movieResumeEntity.overview = movieResume.overview;
            movieResumeEntity.releaseDate = movieResume.releaseDate;
            results.add(movieResumeEntity);
        }
        return results;
    }
}
