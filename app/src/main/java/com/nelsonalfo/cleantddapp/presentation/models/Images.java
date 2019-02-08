package com.nelsonalfo.cleantddapp.presentation.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Images {

    public String baseUrl;
    public String secureBaseUrl;
    public List<String> backdropSizes = null;
    public List<String> logoSizes = null;
    public List<String> posterSizes = null;
    public List<String> profileSizes = null;
    public List<String> stillSizes = null;

}
