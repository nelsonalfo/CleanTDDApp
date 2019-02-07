package com.nelsonalfo.cleantddapp.domain.entities;

import java.util.List;


public class MovieListEntity {
    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public List<MovieResumeEntity> results = null;
}
