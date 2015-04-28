package com.android.template.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.R;
import com.android.template.adapter.MovieAdapter;
import com.android.template.model.Movie;
import com.android.template.model.Search;
import com.android.template.network.MovieService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

public class MovieFragment extends BaseFragment implements Observer<Movie>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MovieFragment.class.getSimpleName();

    @Inject
    MovieService movieService;

    @Inject
    MovieAdapter movieAdapter;

    private SwipeRefreshLayout swipeLayout;

    /**
     * Returns a new instance of this fragment
     * number.
     */
    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        swipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_main, container, false);

        return swipeLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView movieList = ButterKnife.findById(swipeLayout, R.id.movie_list);
        RecyclerView.LayoutManager layoutManager = getLayoutForDisplay(getActivity().getWindowManager().getDefaultDisplay());
        movieList.setLayoutManager(layoutManager);
        movieList.setAdapter(movieAdapter);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.main_color);
        // post to workaround https://code.google.com/p/android/issues/detail?id=77712
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    private RecyclerView.LayoutManager getLayoutForDisplay(Display display) {
        Point size = new Point();
        display.getSize(size);

        if (size.x > 800) {
            return new GridLayoutManager(getActivity(), 2);
        } else {
            return new LinearLayoutManager(getActivity());
        }
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Movie subscriber completed.");

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Movie subscriber error.", e);

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onNext(Movie movie) {
        Log.i(TAG, "Movie subscriber next: " + movie);
        movieAdapter.add(movie);
    }

    @Override
    public void onRefresh() {
        bind(movieService.searchByTitle("The")
                .flatMap(new Func1<Search, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(Search search) {
                        return Observable.from(search.getSearch());
                    }
                })
                .map(new Func1<Movie, String>() {
                    @Override
                    public String call(Movie movie) {
                        return movie.getImdbID();
                    }
                })
                .concatMap(new Func1<String, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(String imdbId) {
                        return movieService.getById(imdbId);
                    }
                }), this);
    }
}
