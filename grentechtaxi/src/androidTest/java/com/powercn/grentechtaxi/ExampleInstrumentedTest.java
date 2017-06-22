package com.powercn.grentechtaxi;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.powercn.grentechtaxi.activity.DateSelectorActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.powercn.grentechtaxi", appContext.getPackageName());

        Intent intent=new Intent();
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK );
        intent.setClass(appContext, DateSelectorActivity.class);
        appContext.startActivity(intent);

    }
}
