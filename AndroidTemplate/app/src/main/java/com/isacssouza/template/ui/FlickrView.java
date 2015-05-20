package com.isacssouza.template.ui;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;

import com.isacssouza.template.R;
import com.isacssouza.template.adapter.FlickrAdapter;
import com.isacssouza.template.model.FlickrPhoto;
import com.isacssouza.template.presenter.FlickrPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A custom view for showing flickr images on the screen.
 *
 * Created by isacssouza on 5/19/15.
 */
public class FlickrView extends SwipeRefreshLayout implements SwipeRefreshLayout.OnRefreshListener {
    @Inject
    FlickrPresenter presenter;

    @Inject
    FlickrAdapter flickrAdapter;

    private MainActivity mainActivity;
    private SearchView searchView;

    public FlickrView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mainActivity = (MainActivity) getContext();
        mainActivity.inject(this);

        RecyclerView photoList = ButterKnife.findById(this, R.id.photo_list);
        StaggeredGridLayoutManager layoutManager = getLayoutForDisplay(mainActivity.getWindowManager().getDefaultDisplay());
        photoList.setLayoutManager(layoutManager);
        flickrAdapter.setSpanCount(layoutManager.getSpanCount());
        photoList.setAdapter(flickrAdapter);

        setOnRefreshListener(this);
        setColorSchemeResources(R.color.main_color);

        presenter.setView(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        presenter.resume();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        presenter.pause();
    }

    private void doSearch(String searchText) {
        presenter.search(searchText);
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
    public void onRefresh() {
        presenter.search(presenter.getCurrentSearch());
    }

    public void startRefreshing() {
        // post to workaround https://code.google.com/p/android/issues/detail?id=77712
        post(new Runnable() {
            @Override
            public void run() {
                setRefreshing(true);
            }
        });
    }

    public void stopRefreshing() {
        setRefreshing(false);
    }

    public void addPhoto(FlickrPhoto photo) {
        flickrAdapter.add(photo);
    }

    public void clearPhotos() {
        flickrAdapter.clear();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.flickr_search_menu, menu);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(searchView.getQuery().toString());
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        MenuItemCompat.expandActionView(menu.findItem(R.id.search));
        searchView.setQuery(presenter.getCurrentSearch(), false);
        searchView.clearFocus();
    }
}
