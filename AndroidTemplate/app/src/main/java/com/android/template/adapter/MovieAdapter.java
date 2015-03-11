package com.android.template.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.template.MainActivity;
import com.android.template.androidtemplate.R;
import com.android.template.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by isacssouza on 3/10/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    @Inject
    LayoutInflater mLayoutInflater;

    private MainActivity mMainActivity;
    private List<Movie> movies = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView year;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movie_list_item_title);
            year = (TextView) itemView.findViewById(R.id.movie_list_item_year);
        }
    }

    @Inject
    public MovieAdapter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.movie_list_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear().toString());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void swapMovies(List<Movie> newMovies) {
        movies.clear();
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }
}
