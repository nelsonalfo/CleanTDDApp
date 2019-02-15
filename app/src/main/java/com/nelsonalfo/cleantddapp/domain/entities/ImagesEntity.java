package com.nelsonalfo.cleantddapp.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class ImagesEntity {

    public String baseUrl;
    public String secureBaseUrl;
    public List<String> backdropSizes = null;
    public List<String> logoSizes = null;
    public List<String> posterSizes = null;
    public List<String> profileSizes = null;
    public List<String> stillSizes = null;

    public ImagesEntity() {
    }

    public ImagesEntity(String baseUrl, String posterSize) {
        this.baseUrl = baseUrl;
        this.posterSizes = new ArrayList<>();
        posterSizes.add(posterSize);
    }

}
