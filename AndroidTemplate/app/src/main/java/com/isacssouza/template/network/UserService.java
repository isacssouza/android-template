package com.isacssouza.template.network;

import com.isacssouza.template.model.response.RegisterResponse;

import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by isacssouza on 4/18/15.
 */
public interface UserService {
    @POST("/registration/v1/registerDevice/{regId}")
    Observable<RegisterResponse> register(@Path("regId") String regId);
}
