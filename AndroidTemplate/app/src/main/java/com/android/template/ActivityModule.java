package com.android.template;

import android.content.Context;
import android.view.LayoutInflater;

import com.android.template.adapter.MovieAdapter;
import com.android.template.androidtemplate.R;
import com.android.template.network.FlickrManager;
import com.android.template.network.MovieManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

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
                FlickrFragment.class,
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

    @Provides @Singleton
    MainActivity provideMainActivity() {
        return activity;
    }

    @Provides @Singleton
    MovieManager provideMovieManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(activity.getString(R.string.base_uri))
                .build();

        return restAdapter.create(MovieManager.class);
    }

    @Provides @Singleton
    FlickrManager provideFlickrManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(activity.getString(R.string.flickr_base_uri))
                .build();

        return restAdapter.create(FlickrManager.class);
    }

    @Provides @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
