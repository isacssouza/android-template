package com.android.template;

import android.content.Context;

import com.android.template.network.MovieManager;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
        injects = {
                MainActivity.class,
                NavigationDrawerFragment.class,
                HomeFragment.class,
                MovieManager.class
        },
        addsTo = AndroidModule.class
)
public class ActivityModule {
    private final MainActivity activity;

    public ActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
//    @Provides @Singleton @ForActivity Context provideActivityContext() {
//        return activity;
//    }

    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides @Singleton
    MovieManager provideMovieManager() {
        MovieManager movieManager = new MovieManager("http://www.omdbapi.com/?s=");
        activity.inject(movieManager);
        return movieManager;
    }
}
