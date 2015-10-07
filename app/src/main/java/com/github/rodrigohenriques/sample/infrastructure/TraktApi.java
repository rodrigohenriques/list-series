package com.github.rodrigohenriques.sample.infrastructure;

import com.github.rodrigohenriques.sample.domain.model.Episode;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface TraktApi {
    @Headers({
            "trakt-api-version: 2",
            "trakt-api-key: 4122c40ed43a1f9a8f015538f4b6a80c8ef4e76601b91576982820d955df3f9f"
    })
    @GET("shows/{showId}/seasons/{seasonId}")
    Call<List<Episode>> episodes(@Path("showId") String showId, @Path("seasonId") int seasonId);
}
