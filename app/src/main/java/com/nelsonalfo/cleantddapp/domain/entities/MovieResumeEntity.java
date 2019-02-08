package com.nelsonalfo.cleantddapp.domain.entities;

import java.util.List;


/**
 * Created by nelso on 26/12/2017.
 */

public class MovieResumeEntity {
    public Integer voteCount;
    public Integer id;
    public Boolean video;
    public Double voteAverage;
    public String title;
    public Double popularity;
    public String posterPath;
    public String originalLanguage;
    public String originalTitle;
    public List<Integer> genreIds = null;
    public String backdropPath;
    public Boolean adult;
    public String overview;
    public String releaseDate;

    public MovieResumeEntity() {
    }

    public MovieResumeEntity(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
