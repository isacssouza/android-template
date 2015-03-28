package com.android.template.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.template.MainActivity;
import com.android.template.androidtemplate.R;
import com.android.template.model.Movie;
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

    @Inject
    LayoutInflater mLayoutInflater;

    private MainActivity mMainActivity;
    private Map<String, Movie> movieMap = new HashMap<>();
    private List<Movie> movies = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.movie_list_item_title) public TextView title;
        @InjectView(R.id.movie_list_item_year) public TextView year;
        @InjectView(R.id.movie_list_item_poster) public ImageView poster;

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
        View rootView = mLayoutInflater.inflate(R.layout.movie_list_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear().toString());
        Picasso.with(mMainActivity)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_theaters_black_48dp)
                .error(R.drawable.ic_error_black_48dp)
                .resize(0, holder.poster.getLayoutParams().height)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void add(Movie movie) {
        Movie oldMovie = movieMap.put(movie.getImdbID(), movie);
        if (oldMovie != null) {
            movies.remove(oldMovie);
        }
        movies.add(movie);
        notifyDataSetChanged();
    }
}
