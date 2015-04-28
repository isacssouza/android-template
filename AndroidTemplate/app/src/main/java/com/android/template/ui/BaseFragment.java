package com.android.template.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A fragment containing boilerplate code.
 *
 * Created by isacssouza on 4/23/15.
 */
public class BaseFragment extends Fragment {
    protected CompositeSubscription mSubscriptions;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSubscriptions.unsubscribe();
    }

    protected <T> Subscription bind(Observable<T> observable, Observer<T> observer) {
        Subscription subscription = AppObservable.bindFragment(this, observable)
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
        mSubscriptions.add(subscription);

        return subscription;

    }
}
