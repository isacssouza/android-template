package com.isacssouza.template.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for a movie search.
 *
 * Created by isacssouza on 3/10/15.
 */
public class Search {
    @SerializedName("Search")
    private List<Movie> search;

    public List<Movie> getSearch() {
        return search;
    }

    public void setSearch(List<Movie> search) {
        this.search = search;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Search{");
        sb.append("search=").append(search);
        sb.append('}');
        return sb.toString();
    }
}
