package com.github.rodrigohenriques.sample.application.service;

public class LoadEpisodesInput {
    protected final String showId;
    protected final int seasonId;

    public LoadEpisodesInput(String showId, int seasonId) {
        this.showId = showId;
        this.seasonId = seasonId;
    }
}
