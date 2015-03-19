package com.android.template.network;

import android.util.Log;

import com.android.template.model.Movie;
import com.android.template.model.Search;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * A manager for movie-related network requests.
 *
 * Created by isacssouza on 3/10/15.
 */
public class MovieManager {
    private static final String TAG = MovieManager.class.getSimpleName();

    private String mBaseURL;

    public MovieManager(String baseURL) {
        mBaseURL = baseURL;
    }

    public Observable<List<Movie>> getMoviesByTitle(final String title) {
        return Observable.create(new Observable.OnSubscribe<List<Movie>>() {
            @Override
            public void call(Subscriber<? super List<Movie>> subscriber) {
                RestTemplate restTemplate = new RestTemplate();
                GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
                messageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("text", "html")));
                restTemplate.getMessageConverters().add(messageConverter);

                try {
                    Search search = restTemplate.getForObject(mBaseURL + "{title}", Search.class, title);
                    subscriber.onNext(search.getSearch());
                } catch (Exception e) {
                    Log.e(TAG, "Failed to get movies.", e);
                    subscriber.onError(e);
                }
            }
        });
    }
}
