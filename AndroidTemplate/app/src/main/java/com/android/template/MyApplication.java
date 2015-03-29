package com.android.template;

import android.app.Application;
import android.os.StrictMode;

import com.android.template.androidtemplate.BuildConfig;
import com.squareup.picasso.Picasso;

import dagger.ObjectGraph;
import java.util.Arrays;
import java.util.List;

public class MyApplication extends Application {
    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }

        Picasso.with(this).setIndicatorsEnabled(BuildConfig.DEBUG);
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
