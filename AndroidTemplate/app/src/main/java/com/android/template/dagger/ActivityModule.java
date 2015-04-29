package com.android.template.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.android.template.adapter.MovieAdapter;
import com.android.template.ui.FlickrFragment;
import com.android.template.ui.MainActivity;
import com.android.template.ui.MovieFragment;
import com.android.template.ui.NavigationDrawerFragment;

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
                MovieFragment.class,
                FlickrFragment.class,
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
    LayoutInflater provideLayoutInflater() {
        return (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
