package com.github.rodrigohenriques.sample.application.service;

/**
 * Created by rodrigohenriques on 10/6/15.
 */
public class FakeLoadSeasonInfoService implements ApplicationService<LoadSeasonInput,LoadSeasonOutput> {

    @Override
    public void execute(LoadSeasonInput loadSeasonInput, ApplicationServiceCallback<LoadSeasonOutput> callback) {
        String showThumbUrl = "https://walter.trakt.us/images/shows/000/001/390/posters/thumb/93df9cd612.jpg";
        String seasonThumbUrl = "https://walter.trakt.us/images/seasons/000/003/963/thumbs/original/6c996deed7.jpg";
        String seasonRating = "9.5";

        LoadSeasonOutput output = new LoadSeasonOutput(showThumbUrl, seasonThumbUrl, seasonRating);

        callback.onSuccess(output);
    }
}
