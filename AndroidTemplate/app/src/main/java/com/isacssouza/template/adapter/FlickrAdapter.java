package com.isacssouza.template.adapter;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.isacssouza.template.ui.MainActivity;
import com.isacssouza.template.model.FlickrPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by isacssouza on 3/10/15.
 */
public class FlickrAdapter extends RecyclerView.Adapter<FlickrAdapter.ViewHolder> {

    @Inject
    LayoutInflater mLayoutInflater;

    private MainActivity mMainActivity;
    private List<FlickrPhoto> photos = new ArrayList<>();
    private int mSpanCount = 2;
    private int mWidth;

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(com.isacssouza.template.R.id.flickr_list_item_photo) public ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }

    @Inject
    public FlickrAdapter(MainActivity mainActivity) {
        mMainActivity = mainActivity;

        Display display = mainActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(com.isacssouza.template.R.layout.flickr_list_item, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlickrPhoto photo = photos.get(position);
        String stringUri = mMainActivity.getString(com.isacssouza.template.R.string.flickr_photo_uri_format,
                photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret());
        Picasso.with(mMainActivity)
                .load(stringUri)
                .resize(mWidth / mSpanCount, 0)
                .placeholder(com.isacssouza.template.R.drawable.ic_theaters_black_48dp)
                .error(com.isacssouza.template.R.drawable.ic_error_black_48dp)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setSpanCount(int spanCount) {
        mSpanCount = spanCount;
    }

    public void add(FlickrPhoto photo) {
        photos.add(photo);
        notifyItemInserted(photos.size()-1);
    }

    public void clear() {
        photos.clear();
        notifyDataSetChanged();
    }
}
