package com.isacssouza.template;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.TestScheduler;

/**
 * From https://github.com/pakerfeldt/rxjava-the-schedulers-pitfall/blob/master/src/test/java/se/akerfeldt/rxjava/TestSchedulerProxy.java
 */
public class TestSchedulerProxy {

    private static final TestScheduler SCHEDULER = new TestScheduler();
    private static final TestSchedulerProxy INSTANCE = new TestSchedulerProxy();

    static {
        try {
            RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
                @Override
                public Scheduler getIOScheduler() {
                    return SCHEDULER;
                }

                @Override
                public Scheduler getComputationScheduler() {
                    return SCHEDULER;
                }

                @Override
                public Scheduler getNewThreadScheduler() {
                    return SCHEDULER;
                }
            });
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Schedulers class already initialized. " +
                    "Ensure you always use the TestSchedulerProxy in unit tests.");
        }
    }

    public static TestSchedulerProxy get() {
        return INSTANCE;
    }

    public void advanceTimeBy(long delayTime, TimeUnit unit) {
        SCHEDULER.advanceTimeBy(delayTime, unit);
    }

    public void triggerActions() {
        SCHEDULER.triggerActions();
    }
}
