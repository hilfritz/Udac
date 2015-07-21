package barqsoft.footballscores;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import barqsoft.footballscores.service.MyFetchService;
import barqsoft.footballscores.widget.UpdateWidgetProvider;

public class MainActivity extends BaseActivity
{
    public static int selected_match_id;
    public static int current_fragment = 2;
    private final String save_tag = "Save Test";
    private PagerFragment pagerFragment;
    private static final String TAG = "MainActivity";
    ScoresRequestReceiver scoresRequestReceiver;
    Menu menu;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            pagerFragment = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, pagerFragment)
                    .commit();
        }
        registerReceiver();
        updateScores();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                Intent start_about = new Intent(this, AboutActivity.class);
                startActivity(start_about);
                break;
            case R.id.action_refresh:
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle(R.string.please_wait);
                progressDialog.setMessage(getString(R.string.loading));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();

                updateScores();
                setRefreshActionButtonState(true);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setRefreshActionButtonState(boolean refreshing){
        if (menu!=null){
            MenuItem refreshItem = menu.findItem(R.id.action_refresh);
            if (refreshItem!=null){
                if (refreshing)
                    refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
                else
                    refreshItem.setActionView(null);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateScores()
    {
        Intent service_start = new Intent(this, MyFetchService.class);
        startService(service_start);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.v(save_tag,"will save");
        Log.v(save_tag,"fragment: "+String.valueOf(pagerFragment.mPagerHandler.getCurrentItem()));
        Log.v(save_tag,"selected id: "+selected_match_id);
        outState.putInt("Pager_Current", pagerFragment.mPagerHandler.getCurrentItem());
        outState.putInt("Selected_match",selected_match_id);
        getSupportFragmentManager().putFragment(outState,"pagerFragment", pagerFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.v(save_tag,"will retrive");
        Log.v(save_tag,"fragment: "+String.valueOf(savedInstanceState.getInt("Pager_Current")));
        Log.v(save_tag,"selected id: "+savedInstanceState.getInt("Selected_match"));
        current_fragment = savedInstanceState.getInt("Pager_Current");
        selected_match_id = savedInstanceState.getInt("Selected_match");
        pagerFragment = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,"pagerFragment");
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(ScoresRequestReceiver.ACTION);
        scoresRequestReceiver = new ScoresRequestReceiver();
        registerReceiver(scoresRequestReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scoresRequestReceiver);
    }

    public class ScoresRequestReceiver extends BroadcastReceiver{
        public static final String ACTION = "barqsoft.footballscores.ACTION";
        @Override
        public void onReceive(Context context, Intent intent) {
            //get the pager here
            //update the fragments inside the pager
            int mode =intent.getIntExtra(MyFetchService.LOG_TAG, 0);

            //hide progress dialog
            if (progressDialog!=null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            switch (mode){
                case MyFetchService.MODE_OK:
                case MyFetchService.MODE_OK_ZERO_INSERT:
                    Log.d(TAG, "onReceive() ok");
                    int i = pagerFragment.getPagerHandler().getCurrentItem();
                    pagerFragment.updateFragment(i);
                    break;
                case MyFetchService.MODE_ERROR_JSON_PARSE:
                    Log.d(TAG, "onReceive() error on parsing json");
                    Toast.makeText(MainActivity.this, getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    break;
                case MyFetchService.MODE_ERROR_SERVER_CONNECTION:
                    Log.d(TAG, "onReceive() server connection error");
                    Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d(TAG, "onReceive() big error");
                    Toast.makeText(MainActivity.this, getString(R.string.error_server), Toast.LENGTH_SHORT).show();
                    break;

            }
            setRefreshActionButtonState(false);

        }
    }

}
