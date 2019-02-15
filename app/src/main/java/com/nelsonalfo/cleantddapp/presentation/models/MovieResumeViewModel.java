package com.nelsonalfo.cleantddapp.presentation.models;

/**
 * Created by nelso on 26/12/2017.
 */

public class MovieResumeViewModel {
    public Integer id;
    public String title;
    public String posterPath;
    public String overview;
    public String posterImageUrl;


    public MovieResumeViewModel() {
    }

    public MovieResumeViewModel(Integer id, String title) {
        this.id = id;
        this.title = title;
    }
}
