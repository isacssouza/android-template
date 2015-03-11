package com.android.template.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for movies.
 *
 * Created by isacssouza on 3/10/15.
 */
public class Movie {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private Integer year;
    private String imdbID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer mYear) {
        this.year = mYear;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String mImdbID) {
        this.imdbID = mImdbID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!imdbID.equals(movie.imdbID)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return imdbID.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("title='").append(title).append('\'');
        sb.append(", year=").append(year);
        sb.append(", imdbID='").append(imdbID).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
