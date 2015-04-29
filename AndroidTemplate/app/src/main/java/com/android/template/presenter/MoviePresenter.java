package com.android.template.presenter;

import android.content.Context;
import android.util.Log;

import com.android.template.dagger.ForApplication;
import com.android.template.model.Movie;
import com.android.template.model.Search;
import com.android.template.network.MovieService;
import com.android.template.ui.MovieFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Presenter for the movie fragment.
 * Created by isacssouza on 4/28/15.
 */
@Singleton
public class MoviePresenter implements Observer<Movie> {
    private static final String TAG = MoviePresenter.class.getSimpleName();

    private Context context;
    private MovieFragment view;
    private Subscription subscription = Subscriptions.unsubscribed();

    private List<Movie> movies = new ArrayList<>();

    @Inject
    MovieService movieService;

    @Inject
    public MoviePresenter(@ForApplication Context context) {
        this.context = context;
    }

    public MovieFragment getView() {
        return view;
    }

    public void setView(MovieFragment view) {
        this.view = view;
    }

    public void resume() {
        if (movies.isEmpty()) {
            refresh();
        } else if (view != null) {
            view.clearMovies();
            for (Movie movie : movies) {
                view.addMovie(movie);
            }
        }
    }

    public void pause() {
    }

    public void refresh() {
        if (subscription.isUnsubscribed() && view != null) {
            subscription = loadData(view.getTitle());
        }
    }

    private Subscription loadData(String title) {
        movies.clear();

        if (view != null) {
            view.clearMovies();
            view.startRefreshing();
        }

        return movieService.searchByTitle(title)
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
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Movie subscriber completed.");

        if (view != null) {
            view.stopRefreshing();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Movie subscriber error.", e);

        if (view != null) {
            view.stopRefreshing();
        }
    }

    @Override
    public void onNext(Movie movie) {
        Log.i(TAG, "Movie subscriber next: " + movie);

        movies.add(movie);

        if (view != null) {
            view.addMovie(movie);
        }
    }
}
