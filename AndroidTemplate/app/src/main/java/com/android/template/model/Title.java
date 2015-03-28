package com.android.template.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for a title response.
 *
 * Created by isacssouza on 3/24/15.
 */
public class Title {
    @SerializedName("Title")
    private List<Movie> title;
}
