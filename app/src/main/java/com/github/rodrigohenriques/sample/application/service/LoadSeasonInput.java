package com.github.rodrigohenriques.sample.application.service;

/**
 * Created by rodrigohenriques on 10/6/15.
 */
public class LoadSeasonInput {
    protected String showId;
    protected int seasonId;

    public LoadSeasonInput(String showId, int seasonId) {
        this.showId = showId;
        this.seasonId = seasonId;
    }
}
