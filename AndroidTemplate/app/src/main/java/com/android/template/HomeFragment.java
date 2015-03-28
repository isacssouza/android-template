package com.android.template;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.adapter.MovieAdapter;
import com.android.template.androidtemplate.R;
import com.android.template.model.Movie;
import com.android.template.model.Search;
import com.android.template.network.MovieManager;
import com.android.template.util.DividerItemDecoration;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeFragment extends Fragment implements Observer<Movie> {
    private static final String TAG = HomeFragment.class.getSimpleName();

    @Inject
    MovieManager movieManager;

    @Inject
    MovieAdapter movieAdapter;

    /**
     * Returns a new instance of this fragment
     * number.
     */
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).inject(this);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView movieList = ButterKnife.findById(rootView, R.id.movie_list);
        movieList.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        movieList.setAdapter(movieAdapter);

        movieManager.searchByTitle("The")
                .concatMap(new Func1<Search, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(Search search) {
                        return Observable.from(search.getSearch());
                    }
                })
                .flatMap(new Func1<Movie, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(Movie movie) {
                        return movieManager.getById(movie.getImdbID());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);

        return rootView;
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Movie subscriber completed.");
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Movie subscriber error.", e);
    }

    @Override
    public void onNext(Movie movie) {
        Log.i(TAG, "Movie subscriber next: " + movie);
        movieAdapter.add(movie);
    }
}
