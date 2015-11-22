package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.hilfritz.jokedisplayer.JokeActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * Created by hilfritz.p.camallere on 7/29/2015.
 * This is used to test the live version of the Jokeendpoint.
 * This is the <b>functional</b> test for the app.
 * <b>IMPORTANT: internet connection is needed in this test.</b>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class MainActivityLiveTest {
    MainActivity mainActivity;
    ShadowActivity shadowActivity;


    @Before
    public void setup() throws Exception{
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().get();
        shadowActivity = Shadows.shadowOf(mainActivity);
    }

    /**
     * https://www.bignerdranch.com/blog/testing-android-product-flavors-with-robolectric/
     * Check the visibility of the AdView <br>
     * Visible if free <br>
     * Gone if paid <br>
     */
    @Test
    public void adViewVisibilityTest(){
        //CHECK THE ADVIEW
        FragmentManager fm = mainActivity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment);
        MainActivityFragment mainActivityFragment = (MainActivityFragment)fragment;
        View view = mainActivityFragment.getView().findViewById(R.id.adView);
        Assert.assertNotNull(view);

        //CHECK THE VISIBILITY OF THE ADVIEW
        String applicationId = BuildConfig.APPLICATION_ID;
        if (applicationId.equals("com.udacity.gradle.builditbigger.free")) {
            Assert.assertTrue(view instanceof AdView);
            Assert.assertTrue(view.getVisibility()!=View.GONE);
        }else if (applicationId.equals("com.udacity.gradle.builditbigger.paid")){
            Assert.assertTrue(view instanceof View);
            Assert.assertTrue(view.getVisibility()==View.GONE);
        }
    }

    /**
     * See http://stackoverflow.com/questions/21735084/mock-httpresponse-with-robolectric
     * See http://kiefermat.com/2012/03/19/mocking-http-responses-in-android-using-robolectric/
     * See http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
     * See http://stackoverflow.com/questions/15035850/testing-async-tasks-with-robolectric
     * See https://groups.google.com/forum/#!topic/robolectric/0wZxGivxsuE
     * See http://robolectric.blogspot.in/2011/01/how-to-test-http-requests.html
     * See http://stackoverflow.com/questions/26290867/asynctask-test-doesnt-execute
     * See https://github.com/upsight/playhaven-robolectric/blob/master/src/test/java/com/xtremelabs/robolectric/shadows/AsyncTaskTest.java
     * @throws Exception
     */
    @Test
    public void openJokeScreenFromLiveJokeButtonTest() throws Exception {
        Button liveJokeBtn = (Button)mainActivity.getMainActivityFragment().getView().findViewById(R.id.liveJokesBtn);
        Assert.assertNotNull(liveJokeBtn);
        liveJokeBtn.performClick();

        jokeBackendSuccessCallTest();

        //TEST if JokeActivity is started
        Intent expectedIntent = new Intent(mainActivity, JokeActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(startedIntent.getComponent(), expectedIntent.getComponent());

    }

    @Test
    public void openJokeScreenFromLocalJokeButtonTest() throws Exception {
        Button localJokeBtn = (Button)mainActivity.getMainActivityFragment().getView().findViewById(R.id.localJokeBtn);
        Assert.assertNotNull(localJokeBtn);
        localJokeBtn.performClick();
        Intent expectedIntent = new Intent(mainActivity, JokeActivity.class);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        Assert.assertEquals(startedIntent.getComponent(), expectedIntent.getComponent());
    }

    public void jokeBackendSuccessCallTest() throws Exception {
        Robolectric.flushBackgroundThreadScheduler(); //from 3.0,  http://stackoverflow.com/questions/15035850/testing-async-tasks-with-robolectric
        String jokeStr = mainActivity.getMainActivityFragment().getTaskInstance().get().getJoke().toString();
        Assert.assertNotNull(jokeStr);
        Assert.assertTrue(jokeStr.length() > 0);
    }



}