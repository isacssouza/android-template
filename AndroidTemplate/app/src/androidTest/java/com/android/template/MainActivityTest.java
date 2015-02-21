package com.android.template;

import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;

import com.android.template.androidtemplate.R;
import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());

        solo.scrollToSide(Solo.RIGHT);
    }

    @Override
    public void tearDown() {
        solo.finishOpenedActivities();
    }

    public void testClickOnAction() {
        solo.clickOnView(solo.getView(R.id.action_example));

        solo.waitForText("Example action.");
    }

    public void testHasToolbar() {
        Toolbar toolbar = (Toolbar) solo.getView(R.id.toolbar);

        assertNotNull(toolbar);
    }
}