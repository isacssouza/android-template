package com.android.template;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.adapter.FlickrAdapter;
import com.android.template.androidtemplate.R;
import com.android.template.model.FlickrPhoto;
import com.android.template.model.FlickrSearch;
import com.android.template.network.FlickrManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class FlickrFragment extends Fragment implements Observer<FlickrPhoto>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = FlickrFragment.class.getSimpleName();

    @Inject
    FlickrManager flickrManager;

    @Inject
    FlickrAdapter flickrAdapter;

    private SwipeRefreshLayout swipeLayout;
    private Subscription moviesSubscription = Subscriptions.empty();

    /**
     * Returns a new instance of this fragment
     * number.
     */
    public static FlickrFragment newInstance() {
        return new FlickrFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        swipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.fragment_flickr, container, false);

        return swipeLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView photoList = ButterKnife.findById(swipeLayout, R.id.photo_list);
        StaggeredGridLayoutManager layoutManager = getLayoutForDisplay(getActivity().getWindowManager().getDefaultDisplay());
        photoList.setLayoutManager(layoutManager);
        flickrAdapter.setSpanCount(layoutManager.getSpanCount());
        photoList.setAdapter(flickrAdapter);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.main_color);
        // post to workaround https://code.google.com/p/android/issues/detail?id=77712
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    private StaggeredGridLayoutManager getLayoutForDisplay(Display display) {
        Point size = new Point();
        display.getSize(size);

        StaggeredGridLayoutManager layoutManager;
        if (size.x > 800) {
            layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        return layoutManager;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        moviesSubscription.unsubscribe();
        ButterKnife.reset(this);
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Movie subscriber completed.");

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Movie subscriber error.", e);
    }

    @Override
    public void onNext(FlickrPhoto photo) {
        Log.i(TAG, "Flickr subscriber next: " + photo);
        flickrAdapter.add(photo);
    }

    @Override
    public void onRefresh() {
        moviesSubscription = AppObservable.bindFragment(this, flickrManager.search("meerkat")
                .concatMap(new Func1<FlickrSearch, Observable<FlickrPhoto>>() {
                    @Override
                    public Observable<FlickrPhoto> call(FlickrSearch search) {
                        return Observable.from(search.getPhotos().getPhoto());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()))
                .subscribe(this);
    }
}
