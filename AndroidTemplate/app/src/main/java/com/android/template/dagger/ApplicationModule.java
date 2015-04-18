package com.android.template.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.template.MyApplication;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(library = true)
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
}