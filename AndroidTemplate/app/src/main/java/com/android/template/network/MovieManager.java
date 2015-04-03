package com.android.template.network;

import com.android.template.model.Movie;
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
    public Observable<Search> searchByTitle(@Query("s") String title);

    @GET("/")
    public Observable<Movie> getById(@Query("i") String id);
}
