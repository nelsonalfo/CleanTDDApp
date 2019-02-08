package com.nelsonalfo.cleantddapp.domain.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GenreEntity {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;

    public GenreEntity() {
    }

    public GenreEntity(String name) {
        this.name = name;
    }
}
