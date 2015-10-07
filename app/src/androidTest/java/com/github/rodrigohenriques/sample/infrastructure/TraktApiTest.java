package com.github.rodrigohenriques.sample.infrastructure;

import com.github.rodrigohenriques.sample.BuildConfig;
import com.github.rodrigohenriques.sample.domain.model.Episode;

import junit.framework.TestCase;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class TraktApiTest extends TestCase {

    public void testEpisodes() throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TraktApi traktApi = retrofit.create(TraktApi.class);

        Call<List<Episode>> call = traktApi.episodes("game-of-thrones", 1);

        Response<List<Episode>> response = call.execute();

        if (response.isSuccess()) {
            List<Episode> episodes = response.body();

            boolean hasEpisodes = episodes.size() > 0;

            assertTrue(hasEpisodes);
        } else {
            assertFalse(true);
        }
    }
}