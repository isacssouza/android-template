package com.isacssouza.template.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.isacssouza.template.adapter.MovieAdapter;
import com.isacssouza.template.ui.FlickrFragment;
import com.isacssouza.template.ui.FlickrView;
import com.isacssouza.template.ui.MainActivity;
import com.isacssouza.template.ui.MovieFragment;
import com.isacssouza.template.ui.NavigationDrawerFragment;

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
                MovieAdapter.class,
                FlickrView.class
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
