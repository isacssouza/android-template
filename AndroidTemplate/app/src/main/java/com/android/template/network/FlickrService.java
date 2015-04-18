package com.android.template.network;

import com.android.template.model.FlickrSearch;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by isacssouza on 4/2/15.
 */
public interface FlickrService {
    @GET("/?method=flickr.photos.search&format=json&nojsoncallback=1")
    Observable<FlickrSearch> search(@Query("text") String text);
}
