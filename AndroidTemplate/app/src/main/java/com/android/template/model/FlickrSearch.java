package com.android.template.model;

/**
 * Created by isacssouza on 4/2/15.
 */
public class FlickrSearch {
    private FlickrPhotos photos;
    private String stat;

    /**
     * @return The photos
     */
    public FlickrPhotos getPhotos() {
        return photos;
    }

    /**
     * @param photos The photos
     */
    public void setPhotos(FlickrPhotos photos) {
        this.photos = photos;
    }

    /**
     * @return The stat
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The stat
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

}
