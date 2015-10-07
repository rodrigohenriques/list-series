package com.github.rodrigohenriques.sample.application.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.rodrigohenriques.sample.R;
import com.github.rodrigohenriques.sample.application.service.ApplicationService;
import com.github.rodrigohenriques.sample.application.service.ApplicationServiceCallback;
import com.github.rodrigohenriques.sample.application.service.ApplicationServiceError;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonInput;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonOutput;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

public class SeasonActivity extends RoboActionBarActivity {

    @Inject
    ApplicationService<LoadSeasonInput, LoadSeasonOutput> loadSeasonInfoService;


    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.season_logo) ImageView imageViewLogo;
    @Bind(R.id.season_header) ImageView imageViewHeader;
    @Bind(R.id.season_rating) TextView textViewRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        ButterKnife.bind(this);

        toolbar.setTitle("Season 1");

        loadData();
    }

    private void loadData() {
        loadSeasonHeader();
        loadSeasonEpisodes();
    }

    private void loadSeasonEpisodes() {

    }

    private void loadSeasonHeader() {
        LoadSeasonInput input = new LoadSeasonInput("game-of-thrones", 1);
        loadSeasonInfoService.execute(input, new ApplicationServiceCallback<LoadSeasonOutput>() {
            @Override
            public void onSuccess(LoadSeasonOutput loadSeasonOutput) {
                Picasso.with(SeasonActivity.this)
                        .load(loadSeasonOutput.getShowThumbUrl())
                        .placeholder(R.drawable.serie_thumbnail_placeholder)
                        .into(imageViewLogo);

                Picasso.with(SeasonActivity.this)
                        .load(loadSeasonOutput.getSeasonThumbUrl())
                        .placeholder(R.drawable.season_background_placeholder)
                        .into(imageViewHeader);

                textViewRating.setText(loadSeasonOutput.getSeasonRating());
            }

            @Override
            public void onError(ApplicationServiceError error) {

            }
        });
    }
}
