package com.android.template.network;

import com.android.template.model.FlickrSearch;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by isacssouza on 4/2/15.
 */
public interface FlickrManager {
    @GET("/?method=flickr.photos.search&api_key=4143542731626dd57dc916803c3a08c7&&format=json&nojsoncallback=1")
    Observable<FlickrSearch> search(@Query("text") String text);
}
