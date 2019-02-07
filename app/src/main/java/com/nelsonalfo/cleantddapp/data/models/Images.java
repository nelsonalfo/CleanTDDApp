package com.nelsonalfo.cleantddapp.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Images {

    @SerializedName("base_url")
    @Expose
    public String baseUrl;
    @SerializedName("secure_base_url")
    @Expose
    public String secureBaseUrl;
    @SerializedName("backdrop_sizes")
    @Expose
    public List<String> backdropSizes = null;
    @SerializedName("logo_sizes")
    @Expose
    public List<String> logoSizes = null;
    @SerializedName("poster_sizes")
    @Expose
    public List<String> posterSizes = null;
    @SerializedName("profile_sizes")
    @Expose
    public List<String> profileSizes = null;
    @SerializedName("still_sizes")
    @Expose
    public List<String> stillSizes = null;

}
