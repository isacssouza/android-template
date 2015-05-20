package com.android.template.presenter;

import android.content.Context;
import android.util.Log;

import com.android.template.R;
import com.android.template.dagger.ForApplication;
import com.android.template.model.FlickrPhoto;
import com.android.template.model.FlickrSearch;
import com.android.template.network.FlickrService;
import com.android.template.ui.FlickrView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * A presenter for the Flickr fragment.
 *
 * Created by isacssouza on 4/28/15.
 */
@Singleton
public class FlickrPresenter implements Presenter<FlickrView>, Observer<FlickrPhoto> {
    private static final String TAG = FlickrPresenter.class.getSimpleName();

    private Context context;
    private FlickrView view;
    private Subscription subscription = Subscriptions.unsubscribed();
    private String currentSearch;

    @Inject
    FlickrService flickrService;
    private List<FlickrPhoto> photos = new ArrayList<>();

    @Inject
    public FlickrPresenter(@ForApplication Context context) {
        this.context = context;

        currentSearch = context.getString(R.string.default_flickr_search);
    }

    @Override
    public void setView(FlickrView view) {
        this.view = view;
    }

    public String getCurrentSearch() {
        return currentSearch;
    }

    @Override
    public void resume() {
        if (photos.isEmpty()) {
            search(currentSearch);
        } else if (view != null) {
            view.clearPhotos();
            for (FlickrPhoto photo : photos) {
                view.addPhoto(photo);
            }
        }
    }

    @Override
    public void pause() {
    }

    public void search(String searchText) {
        if (!currentSearch.equals(searchText)) {
            currentSearch = searchText;
            subscription.unsubscribe();
            subscription = loadData();
        } else if (subscription.isUnsubscribed()) {
            subscription = loadData();
        }
    }

    private Subscription loadData() {
        photos.clear();

        if (view != null) {
            view.clearPhotos();
            view.startRefreshing();
        }

        return flickrService.search(currentSearch)
                .flatMap(new Func1<FlickrSearch, Observable<FlickrPhoto>>() {
                    @Override
                    public Observable<FlickrPhoto> call(FlickrSearch search) {
                        return Observable.from(search.getPhotos().getPhoto());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Flickr subscriber completed.");

        if (view != null) {
            view.stopRefreshing();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Flickr subscriber error.", e);

        if (view != null) {
            view.stopRefreshing();
        }
    }

    @Override
    public void onNext(FlickrPhoto photo) {
        Log.i(TAG, "Flickr subscriber next: " + photo);

        photos.add(photo);

        if (view != null) {
            view.addPhoto(photo);
        }
    }
}
