package com.nelsonalfo.cleantddapp.domain.entities;

import java.util.List;


public class MovieDetailEntity {

    public Boolean adult;
    public String backdropPath;
    public Object belongsToCollection;
    public Integer budget;
    public List<GenreEntity> genres = null;
    public String homepage;
    public Integer id;
    public String imdbId;
    public String originalLanguage;
    public String originalTitle;
    public String overview;
    public Double popularity;
    public String posterPath;
    public List<ProductionCompanyEntity> productionCompanies = null;
    public List<ProductionCountryEntity> productionCountries = null;
    public String releaseDate;
    public Integer revenue;
    public Integer runtime;
    public List<SpokenLanguageEntity> spokenLanguages = null;
    public String status;
    public String tagline;
    public String title;
    public Boolean video;
    public Double voteAverage;
    public Integer voteCount;

    public StringBuilder getFormattedGenres() {
        final StringBuilder genresText = new StringBuilder();

        if (genres != null) {
            for (int i = 0; i < genres.size(); i++) {
                final GenreEntity genre = genres.get(i);

                if (genre != null && genre.name != null && !genre.name.isEmpty()) {
                    addGenreInString(genresText, i, genre);
                }
            }
        }

        return genresText;
    }

    private void addGenreInString(StringBuilder genresText, int i, GenreEntity genre) {
        if (genresText.length() > 0) {
            genresText.append(", ");
        }

        genresText.append(genre.name);
    }

}

