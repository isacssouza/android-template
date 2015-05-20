package com.isacssouza.template.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isacssouza.template.ui.MainActivity;
import com.isacssouza.template.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by isacssouza on 3/10/15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static final String OTHER_SEPARATOR = "  |  ";
    @Inject
    LayoutInflater mLayoutInflater;

    private MainActivity mMainActivity;
    private Map<String, Movie> movieMap = new HashMap<>();
    private List<Movie> movies = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(com.isacssouza.template.R.id.movie_list_item_title) public TextView title;
        @InjectView(com.isacssouza.template.R.id.movie_list_item_year) public TextView year;
        @InjectView(com.isacssouza.template.R.id.movie_list_item_poster) public ImageView poster;
        @InjectView(com.isacssouza.template.R.id.movie_list_item_other) public TextView other;
        @InjectView(com.isacssouza.template.R.id.movie_list_item_rating) public TextView rating;
        @InjectView(com.isacssouza.template.R.id.movie_list_item_plot) public TextView plot;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Inject
    public MovieAdapter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(com.isacssouza.template.R.layout.movie_list_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.other.setText(buildOther(movie));
        holder.rating.setText(movie.getImdbRating());
        holder.plot.setText(movie.getPlot());
        Picasso.with(mMainActivity)
                .load(movie.getPoster())
                .placeholder(com.isacssouza.template.R.drawable.ic_theaters_black_48dp)
                .error(com.isacssouza.template.R.drawable.ic_error_black_48dp)
                .resize(holder.poster.getLayoutParams().width, holder.poster.getLayoutParams().height)
                .into(holder.poster);
    }

    private String buildOther(Movie movie) {
        return movie.getRated() + OTHER_SEPARATOR +
                movie.getRuntime() + OTHER_SEPARATOR +
                movie.getGenre();
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void add(Movie movie) {
        Movie oldMovie = movieMap.put(movie.getImdbID(), movie);
        if (oldMovie != null) {
            int pos = movies.indexOf(oldMovie);
            movies.set(pos, oldMovie);
            notifyItemChanged(pos);
        } else {
            movies.add(movie);
            notifyItemInserted(movies.size()-1);
        }
    }

    public void clear() {
        movies.clear();
        movieMap.clear();

        notifyDataSetChanged();
    }
}
