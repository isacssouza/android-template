package com.isacssouza.template.presenter;

/**
 * A presenter is the component responsible for fetching data and presenting it on the view.
 * Created by isacssouza on 4/28/15.
 */
public interface Presenter<T> {
    void setView(T view);
    void resume();
    void pause();
}
