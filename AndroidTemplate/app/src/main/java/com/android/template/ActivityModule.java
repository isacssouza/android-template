package com.android.template;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * This module represents objects which exist only for the scope of a single activity. We can
 * safely create singletons using the activity instance because the entire object graph will only
 * ever exist inside of that activity.
 */
@Module(
        injects = {
                MainActivity.class,
                NavigationDrawerFragment.class,
                HomeFragment.class
        },
        addsTo = AndroidModule.class,
        library = true
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
    @Provides @Singleton @ForActivity Context provideActivityContext() {
        return activity;
    }
}
