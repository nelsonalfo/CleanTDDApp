package com.nelsonalfo.cleantddapp.domain.entities;

import java.util.List;


/**
 * Created by nelso on 26/12/2017.
 */

public class MoviesResponseEntity {
    public Integer page;
    public Integer totalResults;
    public Integer totalPages;
    public List<MovieResumeEntity> results = null;
}
