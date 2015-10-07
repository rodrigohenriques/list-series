package com.github.rodrigohenriques.sample.application.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rodrigohenriques.sample.R;
import com.github.rodrigohenriques.sample.domain.model.Episode;

import java.util.List;


public class EpisodesRecyclerViewAdapter extends RecyclerView.Adapter<EpisodesRecyclerViewAdapter.ViewHolder> {

    TypedValue typedValue = new TypedValue();

    Context context;
    List<Episode> episodes;

    OnItemClickListener onItemClickListener;

    public EpisodesRecyclerViewAdapter(Context context, List<Episode> episodes) {
        this.context = context;
        this.episodes = episodes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        view.setBackgroundResource(typedValue.resourceId);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        final Episode episode = episodes.get(position);

        holder.textViewName.setText(episode.getTitle());
        holder.view.setBackgroundResource(R.drawable.btn_flat_selector);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(episode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView textViewName;
        public final TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewName = (TextView) view.findViewById(android.R.id.text1);
            this.textViewDescription = (TextView) view.findViewById(android.R.id.text2);
            this.textViewDescription.setTextColor(view.getResources().getColor(R.color.secondary_text));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Episode episode);
    }
}
