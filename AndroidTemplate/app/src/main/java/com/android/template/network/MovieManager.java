package com.android.template.network;

import android.util.Log;

import com.android.template.MainActivity;
import com.android.template.model.Movie;
import com.android.template.model.Search;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * A manager for movie-related network requests.
 *
 * Created by isacssouza on 3/10/15.
 */
public class MovieManager {
    private static final String TAG = MovieManager.class.getSimpleName();

    @Inject
    OkHttpClient mHttpClient;

    private String mBaseURL;

    public MovieManager(String baseURL) {
        mBaseURL = baseURL;
    }

    public Observable<List<Movie>> getMoviesByTitle(final String title) {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                Request request = new Request.Builder()
                        .url(mBaseURL + title)
                        .build();

                Response response = null;
                try {
                    response = mHttpClient.newCall(request).execute();
                    Gson gson = new Gson();
                    Search search = gson.fromJson(response.body().string(), Search.class);
                    subscriber.onNext(search.getSearch());
                } catch (IOException e) {
                    Log.e(TAG, "Failed to get movies.", e);
                    subscriber.onError(e);
                }
            }
        });
    }
}
