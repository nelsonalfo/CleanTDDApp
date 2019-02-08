package com.nelsonalfo.cleantddapp.presentation.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nelsonalfo.cleantddapp.R;
import com.nelsonalfo.cleantddapp.presentation.models.MovieResume;

import java.util.List;

class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {
    private final List<MovieResume> dataSet;


    public MoviesAdapter(List<MovieResume> movies) {
        dataSet = movies;
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_movie, parent, false);

        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoviesViewHolder holder, int position) {
        if (dataSet != null && !dataSet.isEmpty()) {
            final MovieResume movieResume = dataSet.get(position);
            holder.bind(movieResume);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.size() : 0;
    }
}