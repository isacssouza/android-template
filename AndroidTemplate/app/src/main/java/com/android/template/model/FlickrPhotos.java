package com.android.template.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isacssouza on 4/2/15.
 */
public class FlickrPhotos {
    private Integer page;
    private Integer pages;
    private Integer perpage;
    private String total;
    private List<FlickrPhoto> photo = new ArrayList<FlickrPhoto>();

    /**
     * @return The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return The pages
     */
    public Integer getPages() {
        return pages;
    }

    /**
     * @param pages The pages
     */
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    /**
     * @return The perpage
     */
    public Integer getPerpage() {
        return perpage;
    }

    /**
     * @param perpage The perpage
     */
    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    /**
     * @return The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return The photo
     */
    public List<FlickrPhoto> getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(List<FlickrPhoto> photo) {
        this.photo = photo;
    }
}
