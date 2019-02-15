package com.nelsonalfo.cleantddapp.data.mappers;

import com.nelsonalfo.cleantddapp.data.models.MovieResume;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;
import com.nelsonalfo.cleantddapp.domain.entities.ImagesEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import java.util.ArrayList;

public class TMDbMapper {

    public MoviesResponseEntity createEntity(MoviesResponse moviesResponse) {
        final MoviesResponseEntity entity = new MoviesResponseEntity();

        entity.page = moviesResponse.page;
        entity.totalPages = moviesResponse.totalPages;
        entity.totalResults = moviesResponse.totalResults;
        entity.results = new ArrayList<>();

        for (MovieResume movieModel : moviesResponse.results) {
            final MovieResumeEntity movieEntity = new MovieResumeEntity(movieModel.id, movieModel.title);
            movieEntity.title = movieModel.title;
            movieEntity.posterPath = movieModel.posterPath;
            movieEntity.overview = movieModel.overview;

            entity.results.add(movieEntity);
        }
        return entity;
    }

    public TmdbConfigurationEntity createConfigurationEntity(TmdbConfiguration response) {
        TmdbConfigurationEntity entity = new TmdbConfigurationEntity();
        entity.images = new ImagesEntity();
        entity.images.baseUrl = response.images.baseUrl;
        entity.images.secureBaseUrl = response.images.secureBaseUrl;
        entity.images.backdropSizes = response.images.backdropSizes;
        entity.images.logoSizes = response.images.logoSizes;
        entity.images.posterSizes = response.images.posterSizes;
        entity.images.profileSizes = response.images.profileSizes;
        entity.images.stillSizes = response.images.stillSizes;
        entity.changeKeys = response.changeKeys;

        return entity;
    }
}
