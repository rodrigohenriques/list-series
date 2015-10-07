package com.github.rodrigohenriques.sample.application;

import android.app.Application;

import com.github.rodrigohenriques.sample.BuildConfig;
import com.github.rodrigohenriques.sample.application.service.ApplicationService;
import com.github.rodrigohenriques.sample.application.service.FakeLoadSeasonInfoService;
import com.github.rodrigohenriques.sample.application.service.LoadEpisodesInput;
import com.github.rodrigohenriques.sample.application.service.LoadEpisodesService;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonInput;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonOutput;
import com.github.rodrigohenriques.sample.domain.model.Episode;
import com.github.rodrigohenriques.sample.infrastructure.TraktApi;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApplicationModule extends AbstractModule {
    Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Override
    protected void configure() {
        bind(TraktApi.class).toInstance(getTraktApiInstance());
        bind(new TypeLiteral<ApplicationService<LoadSeasonInput, LoadSeasonOutput>>() {
        }).to(FakeLoadSeasonInfoService.class);

        bind(new TypeLiteral<ApplicationService<LoadEpisodesInput, List<Episode>>>() {
        }).to(LoadEpisodesService.class);
    }

    public TraktApi getTraktApiInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TraktApi.class);
    }
}
