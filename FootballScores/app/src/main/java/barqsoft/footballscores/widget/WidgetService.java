package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.RemoteViewsService;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.ScoresDBHelper;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.model.Match;

public class WidgetService extends RemoteViewsService {
    private static final String TAG = "WidgetService";
/*
* So pretty simple just defining the Adapter of the listview
* here Adapter is ListProvider
* */

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory() ");
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        List<Match> matchList = ScoresDBHelper.getAllScoresByDate(Utilies.getCurrentDateStr(), getApplicationContext());
        if (matchList!=null){
            Log.d(TAG, "onGetViewFactory() matchlist.size()="+matchList.size());

        }else{
            Log.d(TAG, "onGetViewFactory() matchlist is null");
        }
        return (new ListProvider(this.getApplicationContext(), intent, matchList));
    }
}
