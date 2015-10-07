package com.github.rodrigohenriques.sample.application.service;

import android.util.Log;

import com.github.rodrigohenriques.sample.domain.model.Episode;
import com.github.rodrigohenriques.sample.infrastructure.TraktApi;
import com.google.inject.Inject;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoadEpisodesService implements ApplicationService<LoadEpisodesInput, List<Episode>> {

    @Inject
    TraktApi traktApi;

    @Override
    public void execute(LoadEpisodesInput loadEpisodesInput, final ApplicationServiceCallback<List<Episode>> callback) {
        final Call<List<Episode>> researchCall = traktApi.episodes(loadEpisodesInput.showId, loadEpisodesInput.seasonId);

        researchCall.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Response<List<Episode>> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                    List<Episode> episodes = response.body();

                    if (episodes != null && episodes.size() > 0) {
                        callback.onSuccess(episodes);
                    } else {
                        ApplicationServiceError error = new ApplicationServiceError(Errors.NO_DATA_AVAILABLE, "Não encontramos nenhum episódio para esta temporada");
                        callback.onError(error);
                    }
                } else {
                    ApplicationServiceError error = new ApplicationServiceError(String.valueOf(response.code()), "Ocorreu um falha no processamento da requisição. Verifique suas conexões de rede.");
                    callback.onError(error);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("LoadEpisodesService", t.getMessage(), t);
                ApplicationServiceError error = new ApplicationServiceError(Errors.UNKNOWN_EXCEPTION, "Ocorreu um falha no processamento da requisição. Verifique suas conexões de rede.", new Exception(t));
                callback.onError(error);
            }
        });
    }

    public static class Errors {
        public final static String NO_DATA_AVAILABLE = "1001";
        public final static String UNKNOWN_EXCEPTION = "9999";
    }
}
