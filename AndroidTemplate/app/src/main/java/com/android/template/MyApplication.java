package com.android.template;

import android.app.Application;
import android.os.StrictMode;

import com.android.template.dagger.ApplicationModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class MyApplication extends Application {
    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());

        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ApplicationModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    /** Inject the supplied {@code object} using the application-specific graph. */
    public void inject(Object object) {
        applicationGraph.inject(object);
    }
}
