package com.nelsonalfo.cleantddapp.data.mappers;

import com.nelsonalfo.cleantddapp.data.models.MovieResume;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import java.util.ArrayList;

public class MoviesMapper {
    public MoviesResponseEntity createEntity(MoviesResponse moviesResponse) {
        final MoviesResponseEntity entity = new MoviesResponseEntity();

        entity.page = moviesResponse.page;
        entity.totalPages = moviesResponse.totalPages;
        entity.totalResults = moviesResponse.totalResults;

        entity.results = new ArrayList<>();

        for (MovieResume movieModel : moviesResponse.results) {
            final MovieResumeEntity movieEntity = new MovieResumeEntity(movieModel.id, movieModel.title);
            movieEntity.adult = movieModel.adult;
            entity.results.add(movieEntity);
        }
        return entity;
    }


    public TmdbConfigurationEntity createConfigurationEntity(TmdbConfiguration response){
        TmdbConfigurationEntity tmdbConfigurationEntity = new TmdbConfigurationEntity();
        tmdbConfigurationEntity.changeKeys = response.changeKeys;

        return tmdbConfigurationEntity;
    }
}
