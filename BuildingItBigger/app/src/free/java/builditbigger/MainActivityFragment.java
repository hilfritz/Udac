package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.hilfritz.joke.backend.jokeApi.JokeApi;
import com.hilfritz.joke.backend.jokeApi.model.Joke;

import java.io.IOException;

/**
 * Created by Hilfritz P. Camallere on 8/2/2015.
 */
public class MainActivityFragment extends Fragment {
    MainActivity mainActivity;
    boolean isReady = false;
    EndpointsAsyncTask endpointsAsyncTask;
    private static final String TAG = "tag1";

    public MainActivityFragment() {
    }

    public static MainActivityFragment findOrCreateRetainFragment(FragmentManager fm){
        MainActivityFragment mainActivityFragment=null;
        //MainActivityFragment mainActivityFragment =(MainActivityFragment)fm.findFragmentByTag(TAG);
        Fragment fragment = fm.findFragmentById(R.id.fragment);
        if (fragment==null){
            //mainActivityFragment = new MainActivityFragment();
            //fm.beginTransaction().add(mainActivityFragment, TAG).commit();


            //Fragment fragment = fm.findFragmentById(R.id.fragment);
            mainActivityFragment = (MainActivityFragment)fragment;
        }
        mainActivityFragment = (MainActivityFragment)fragment;
        return mainActivityFragment;
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        //getNewTaskInstance(mainActivity).execute();

    }

    /**
     * Hold a reference to the parent Activity so we can report the
     * task's current progress and results. The Android framework
     * will pass us a reference to the newly created Activity after
     * each configuration change.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isReady = true;
    }

    /**
     * Set the callback to null so we don't accidentally leak the
     * Activity instance.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        isReady = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        View tempView = root.findViewById(R.id.adView);
        if (tempView instanceof AdView) {
            AdView mAdView = (AdView) tempView;
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
        return root;
    }

    public EndpointsAsyncTask getNewTaskInstance(Context context){
        endpointsAsyncTask = new EndpointsAsyncTask(context);
        return endpointsAsyncTask;
    }



    public EndpointsAsyncTask getTaskInstance(){
        return endpointsAsyncTask;
    }



    public class EndpointsAsyncTask extends AsyncTask<Void, Void, Joke> {
        private JokeApi myApiService = null;
        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isReady){
                mainActivity.showProgressDialog();

            }
        }

        EndpointsAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Joke doInBackground(Void... params) {
            if(myApiService == null) {
                /*
                // Only do this once
                JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                        // end options for devappserver
                */

                JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://jokegcmbackend.appspot.com/_ah/api/");


                myApiService = builder.build();
            }
            try {
                return myApiService.getRandomJoke().execute();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Joke result) {
            //Toast.makeText(context, result.getJoke(), Toast.LENGTH_LONG).show();
            if (isReady) {
                mainActivity.dismissProgressDialog();
                if (result != null) {
                    if (result.getJoke() != null)
                        mainActivity.startJokeDisplayerIntent(result.getJoke());
                    else
                        Toast.makeText(mainActivity, "Sorry there was a problem loading the great joke. Please try again.", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(mainActivity, "Sorry there was a problem loading the great joke. Please try again.", Toast.LENGTH_SHORT).show();

            }
        }

        public JokeApi getMyApiService() {
            return myApiService;
        }
    }
}
