package com.android.template.presenter;

import android.test.mock.MockContext;

import com.android.template.model.Movie;
import com.android.template.model.Search;
import com.android.template.network.MovieService;
import com.android.template.ui.MovieFragment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests for the movie presenter.
 *
 * Created by isacssouza on 4/29/15.
 */
public class MoviePresenterTest {
    private MoviePresenter moviePresenter;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieFragment movieFragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        moviePresenter = new MoviePresenter(new MockContext());
        moviePresenter.movieService = movieService;
    }

    @Test
    public void testShouldRefreshOnResumeWithNoData() {
        when(movieService.searchByTitle("The")).thenReturn(Observable.<Search>empty());

        moviePresenter.setView(movieFragment);
        moviePresenter.resume();

        verify(movieFragment).clearMovies();
        verify(movieFragment).startRefreshing();
    }

    @Test
    public void testShouldNotRefreshOnResumeWithData() {
        // inject the movies
        Movie movie0 = new Movie();
        Movie movie1 = new Movie();
        movie0.setImdbID("id0");
        movie1.setImdbID("id1");
        moviePresenter.onNext(movie0);
        moviePresenter.onNext(movie1);

        moviePresenter.setView(movieFragment);
        moviePresenter.resume();

        verify(movieFragment).clearMovies();
        verify(movieFragment).addMovie(movie0);
        verify(movieFragment).addMovie(movie1);
        verifyZeroInteractions(movieService);
    }

    @Test
    public void testShouldStopRefreshingOnError() {
        moviePresenter.setView(movieFragment);
        moviePresenter.onError(new Throwable());

        verify(movieFragment).stopRefreshing();
    }

    @Test
    public void testShouldStopRefreshingOnComplete() {
        moviePresenter.setView(movieFragment);
        moviePresenter.onCompleted();

        verify(movieFragment).stopRefreshing();
    }

    @Test
    public void testShouldAddMovieOnNext() {
        Movie movie = new Movie();
        movie.setImdbID("id0");

        moviePresenter.setView(movieFragment);
        moviePresenter.onNext(movie);

        verify(movieFragment).addMovie(movie);
    }

    @Test
    public void testPauseDoesNotInteractWithView() {
        moviePresenter.setView(movieFragment);
        moviePresenter.pause();

        verifyZeroInteractions(movieFragment);
    }

    @Test
    public void testRefreshShouldGetMovieById() {
        Movie movie = new Movie();
        movie.setImdbID("id0");
        Search search = new Search();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        search.setSearch(movies);

        TestScheduler testScheduler = new TestScheduler();
        TestSubject<Search> observable = TestSubject.create(testScheduler);

        when(movieService.searchByTitle("The")).thenReturn(observable);

        moviePresenter.setView(movieFragment);
        moviePresenter.resume();

        observable.onNext(search);
        testScheduler.triggerActions();

        verify(movieService).getById(movie.getImdbID());
    }
}
