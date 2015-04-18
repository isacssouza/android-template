package com.android.template.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.android.template.ui.FlickrFragment;
import com.android.template.ui.MovieFragment;
import com.android.template.ui.MainActivity;
import com.android.template.ui.NavigationDrawerFragment;
import com.android.template.R;
import com.android.template.adapter.MovieAdapter;
import com.android.template.network.FlickrService;
import com.android.template.network.MovieService;
import com.android.template.network.UserService;

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
                MovieFragment.class,
                FlickrFragment.class,
                MovieService.class,
                MovieAdapter.class
        },
        addsTo = ApplicationModule.class
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
    MovieService provideMovieManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(activity.getString(R.string.movie_base_uri))
                .build();

        return restAdapter.create(MovieService.class);
    }

    @Provides @Singleton
    UserService provideUserManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(activity.getString(R.string.base_uri))
                .build();

        return restAdapter.create(UserService.class);
    }

    @Provides @Singleton
    FlickrService provideFlickrManager() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(activity.getString(R.string.flickr_base_uri))
                .build();

        return restAdapter.create(FlickrService.class);
    }

    @Provides @Singleton
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
