package com.isacssouza.template;

import android.test.ActivityInstrumentationTestCase2;

import com.isacssouza.template.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() {
        getActivity();
    }

    @After
    @Override
    public void tearDown() {
    }

    @Test
    public void testHasToolbar() {
        onView(withId(com.isacssouza.template.R.id.toolbar)).check(matches(isDisplayed()));
    }
}