package com.isacssouza.template.network;

import com.isacssouza.template.model.Movie;
import com.isacssouza.template.model.Search;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * A manager for movie-related network requests.
 *
 * Created by isacssouza on 3/10/15.
 */
public interface MovieService {
    @GET("/")
    Observable<Search> searchByTitle(@Query("s") String title);

    @GET("/")
    Observable<Movie> getById(@Query("i") String id);
}
