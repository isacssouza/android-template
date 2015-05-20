package com.android.template.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.template.R;

/**
 * A fragment that shows flickr photos on a grid.
 *
 * This fragment demonstrates how to use a custom view to handle the view logic instead of
 * the fragment itself to avoid having to handle the fragment lifecycle.
 */
public class FlickrFragment extends BaseFragment {
    private static final String TAG = FlickrFragment.class.getSimpleName();

    private FlickrView flickrView;

    /**
     * Returns a new instance of this fragment.
     */
    public static FlickrFragment newInstance() {
        return new FlickrFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flickrView = (FlickrView) inflater.inflate(R.layout.fragment_flickr, container, false);

        return flickrView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        flickrView.onCreateOptionsMenu(menu, inflater);
    }

}
