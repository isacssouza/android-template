package com.android.template.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.template.MyApplication;
import com.android.template.R;
import com.android.template.network.FlickrService;
import com.android.template.network.MovieService;
import com.android.template.network.UserService;
import com.android.template.presenter.FlickrPresenter;
import com.android.template.presenter.MoviePresenter;
import com.android.template.ui.NavigationDrawerFragment;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(injects = {
        NavigationDrawerFragment.class,
        MoviePresenter.class,
        FlickrPresenter.class
}, library = true)
public class ApplicationModule {
    private static final String DEFAULT_PREFS = "DEFAULT_PREFS";

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    GoogleCloudMessaging provideGCM() {
        return GoogleCloudMessaging.getInstance(application);
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
    }

    @Provides @Singleton
    MovieService provideMovieManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(application.getString(R.string.movie_base_uri))
                .build();

        return restAdapter.create(MovieService.class);
    }

    @Provides @Singleton
    UserService provideUserManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(application.getString(R.string.base_uri))
                .build();

        return restAdapter.create(UserService.class);
    }

    @Provides @Singleton
    FlickrService provideFlickrManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(application.getString(R.string.flickr_base_uri))
                .setRequestInterceptor(new FlickrInterceptor(application))
                .build();

        return restAdapter.create(FlickrService.class);
    }

    static class FlickrInterceptor implements RequestInterceptor {
        private Context context;

        public FlickrInterceptor(Context context) {
            this.context = context;
        }

        @Override public void intercept(RequestFacade request) {
            request.addQueryParam("api_key", context.getString(R.string.flickr_api_key));
        }
    }
}