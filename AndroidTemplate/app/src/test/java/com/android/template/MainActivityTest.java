package com.android.template;

import com.android.template.ui.MainActivity;

import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class MainActivityTest {
    @Test
    public void testIt() {
        MainActivity mainActivity = new MainActivity();

        assertThat(mainActivity, notNullValue());
    }
}