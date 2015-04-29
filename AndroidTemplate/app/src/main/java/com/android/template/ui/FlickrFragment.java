package com.android.template.ui;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.R;
import com.android.template.adapter.FlickrAdapter;
import com.android.template.model.FlickrPhoto;
import com.android.template.presenter.FlickrPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * A fragment that shows flickr photos on a grid.
 */
public class FlickrFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = FlickrFragment.class.getSimpleName();

    @Inject
    FlickrAdapter flickrAdapter;

    @Inject
    FlickrPresenter flickrPresenter;

    private SwipeRefreshLayout swipeLayout;
    private SearchView searchView;

    /**
     * Returns a new instance of this fragment
     * number.
     */
    public static FlickrFragment newInstance() {
        return new FlickrFragment();
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

        RecyclerView photoList = ButterKnife.findById(swipeLayout, R.id.photo_list);
        StaggeredGridLayoutManager layoutManager = getLayoutForDisplay(getActivity().getWindowManager().getDefaultDisplay());
        photoList.setLayoutManager(layoutManager);
        flickrAdapter.setSpanCount(layoutManager.getSpanCount());
        photoList.setAdapter(flickrAdapter);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(R.color.main_color);

        flickrPresenter.setView(this);
    }

    @Override
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
        searchView.setQuery(flickrPresenter.getCurrentSearch(), false);
        searchView.clearFocus();
    }

    @Override
    public void onResume() {
        super.onResume();

        flickrPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();

        flickrPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        flickrPresenter.setView(null);
    }

    private void doSearch(String searchText) {
        flickrPresenter.search(searchText);
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
        flickrPresenter.search(flickrPresenter.getCurrentSearch());
    }

    public void startRefreshing() {
        // post to workaround https://code.google.com/p/android/issues/detail?id=77712
        swipeLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(true);
            }
        });
    }

    public void stopRefreshing() {
        swipeLayout.setRefreshing(false);
    }

    public void addPhoto(FlickrPhoto photo) {
        flickrAdapter.add(photo);
    }

    public void clearPhotos() {
        flickrAdapter.clear();
    }
}
