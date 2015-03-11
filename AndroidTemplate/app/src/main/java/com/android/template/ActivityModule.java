package com.android.template;

import android.content.Context;
import android.view.LayoutInflater;

import com.android.template.adapter.MovieAdapter;
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
                MovieManager.class,
                MovieAdapter.class
        },
        addsTo = AndroidModule.class
)
public class ActivityModule {
    private final MainActivity activity;

    public ActivityModule(MainActivity activity) {
        this.activity = activity;
    }

//    @Provides @Singleton @ForActivity MainActivity provideMainActivity() {
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

    @Provides @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
