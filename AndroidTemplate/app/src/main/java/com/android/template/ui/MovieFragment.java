package com.android.template.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.R;
import com.android.template.adapter.MovieAdapter;
import com.android.template.model.Movie;
import com.android.template.presenter.MoviePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MovieFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MovieFragment.class.getSimpleName();

    @Inject
    MovieAdapter movieAdapter;

    @Inject
    MoviePresenter moviePresenter;

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

        moviePresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        moviePresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        moviePresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        moviePresenter.setView(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public void onRefresh() {
        moviePresenter.refresh();
    }

    public void startRefreshing() {
        // post to workaround https://code.google.com/p/android/issues/detail?id=77712
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
    }

    public void stopRefreshing() {
        swipeLayout.setRefreshing(false);
    }

    public void addMovie(Movie movie) {
        movieAdapter.add(movie);
    }

    public void clearMovies() {
        movieAdapter.clear();
    }

    public String getTitle() {
        return "The";
    }
}
