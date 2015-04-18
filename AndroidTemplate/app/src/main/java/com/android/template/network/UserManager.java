package com.android.template.network;

import com.android.template.model.RegisterResponse;

import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by isacssouza on 4/18/15.
 */
public interface UserManager {
    @POST("/registration/v1/registerDevice/{regId}")
    Observable<RegisterResponse> register(@Path("regId") String regId);
}
