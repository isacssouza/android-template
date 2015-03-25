package com.android.template.network;

import com.android.template.model.Search;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * A manager for movie-related network requests.
 *
 * Created by isacssouza on 3/10/15.
 */
public interface MovieManager {
    @GET("/")
    public Observable<Search> getMoviesByTitle(@Query("s") String title);
}
