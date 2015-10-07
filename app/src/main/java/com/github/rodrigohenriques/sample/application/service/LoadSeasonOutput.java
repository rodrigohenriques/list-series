package com.github.rodrigohenriques.sample.application.service;

public class LoadSeasonOutput {
    String showThumbUrl;
    String seasonThumbUrl;
    String seasonRating;

    public LoadSeasonOutput(String showThumbUrl, String seasonThumbUrl, String seasonRating) {
        this.showThumbUrl = showThumbUrl;
        this.seasonThumbUrl = seasonThumbUrl;
        this.seasonRating = seasonRating;
    }

    public String getShowThumbUrl() {
        return showThumbUrl;
    }

    public String getSeasonThumbUrl() {
        return seasonThumbUrl;
    }

    public String getSeasonRating() {
        return seasonRating;
    }
}
