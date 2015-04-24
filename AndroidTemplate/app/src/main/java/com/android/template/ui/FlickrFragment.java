package com.android.template.ui;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.R;
import com.android.template.adapter.FlickrAdapter;
import com.android.template.model.FlickrPhoto;
import com.android.template.model.FlickrSearch;
import com.android.template.network.FlickrService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * A fragment that shows flickr photos on a grid.
 */
public class FlickrFragment extends Fragment implements Observer<FlickrPhoto>, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = FlickrFragment.class.getSimpleName();
    private static final String KEY_SEARCH_TEXT = "KEY_SEARCH_TEXT";

    @Inject
    FlickrService flickrService;

    @Inject
    FlickrAdapter flickrAdapter;

    private SwipeRefreshLayout swipeLayout;
    private Subscription subscription = Subscriptions.empty();
    private SearchView searchView;
    private String searchText = "meerkat";

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

        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_SEARCH_TEXT)) {
                searchText = savedInstanceState.getString(KEY_SEARCH_TEXT);
            }
        }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.flickr_search_menu, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchText = searchView.getQuery().toString();
                doSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_SEARCH_TEXT, searchText);
        super.onSaveInstanceState(outState);
    }

    private void doSearch() {
        searchView.clearFocus();
        flickrAdapter.clear();
        swipeLayout.setRefreshing(true);
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

        subscription.unsubscribe();
        ButterKnife.reset(this);
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "Flickr subscriber completed.");

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Flickr subscriber error.", e);

        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onNext(FlickrPhoto photo) {
        Log.i(TAG, "Flickr subscriber next: " + photo);
        flickrAdapter.add(photo);
    }

    @Override
    public void onRefresh() {
        subscription = AppObservable.bindFragment(this, flickrService.search(searchText)
                .flatMap(new Func1<FlickrSearch, Observable<FlickrPhoto>>() {
                    @Override
                    public Observable<FlickrPhoto> call(FlickrSearch search) {
                        return Observable.from(search.getPhotos().getPhoto());
                    }
                }))
                .subscribeOn(Schedulers.io())
                .subscribe(this);
    }
}
