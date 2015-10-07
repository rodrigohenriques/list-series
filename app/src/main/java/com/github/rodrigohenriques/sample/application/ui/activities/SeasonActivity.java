package com.github.rodrigohenriques.sample.application.ui.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.rodrigohenriques.sample.R;
import com.github.rodrigohenriques.sample.application.service.ApplicationService;
import com.github.rodrigohenriques.sample.application.service.ApplicationServiceCallback;
import com.github.rodrigohenriques.sample.application.service.ApplicationServiceError;
import com.github.rodrigohenriques.sample.application.service.LoadEpisodesInput;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonInput;
import com.github.rodrigohenriques.sample.application.service.LoadSeasonOutput;
import com.github.rodrigohenriques.sample.application.ui.recyclerview.DividerItemDecoration;
import com.github.rodrigohenriques.sample.application.ui.recyclerview.EpisodesRecyclerViewAdapter;
import com.github.rodrigohenriques.sample.domain.model.Episode;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import roboguice.activity.RoboActionBarActivity;

public class SeasonActivity extends RoboActionBarActivity implements EpisodesRecyclerViewAdapter.OnItemClickListener {

    private final String showId = "game-of-thrones";
    private final int seasonId = 1;

    private ProgressDialog progressDialog;

    @Inject
    ApplicationService<LoadSeasonInput, LoadSeasonOutput> loadSeasonInfoService;

    @Inject
    ApplicationService<LoadEpisodesInput, List<Episode>> loadEpisodesService;

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.season_logo) ImageView imageViewLogo;
    @Bind(R.id.season_header) ImageView imageViewHeader;
    @Bind(R.id.season_rating) TextView textViewRating;

    @Bind(R.id.recyclerview) RecyclerView recyclerView;

    @BindColor(android.R.color.transparent) int colorTransparent;
    @BindColor(R.color.title_color) int colorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        ButterKnife.bind(this);

        collapsingToolbarLayout.setTitle("Season 1");
        collapsingToolbarLayout.setCollapsedTitleTextColor(colorTitle);
        collapsingToolbarLayout.setExpandedTitleColor(colorTransparent);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        loadSeasonHeader();
        loadSeasonEpisodes();
    }

    private void loadSeasonEpisodes() {
        progressDialog = ProgressDialog.show(this, "Processando", "Aguarde...", true);

        LoadEpisodesInput input = new LoadEpisodesInput(showId, seasonId);
        loadEpisodesService.execute(input, new ApplicationServiceCallback<List<Episode>>() {
            @Override
            public void onSuccess(List<Episode> episodes) {
                EpisodesRecyclerViewAdapter episodesRecyclerViewAdapter = new EpisodesRecyclerViewAdapter(SeasonActivity.this, episodes);
                episodesRecyclerViewAdapter.setOnItemClickListener(SeasonActivity.this);
                recyclerView.setAdapter(episodesRecyclerViewAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onError(ApplicationServiceError error) {
                handleServiceError(error);

                progressDialog.dismiss();
            }
        });
    }

    private void loadSeasonHeader() {
        LoadSeasonInput input = new LoadSeasonInput(showId, seasonId);
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
                handleServiceError(error);
            }
        });
    }

    private void handleServiceError(ApplicationServiceError error) {
        AlertDialog dialog = new AlertDialog.Builder(SeasonActivity.this)
                .setTitle("Atenção")
                .setMessage(String.format("%s\nCódigo: %s", error.getMessage(), error.getCode()))
                .setPositiveButton(R.string.action_close, null)
                .create();

        dialog.show();
    }

    @Override
    public void onItemClick(Episode episode) {
        Snackbar.make(recyclerView, episode.getTitle(), Snackbar.LENGTH_LONG).show();
    }
}
