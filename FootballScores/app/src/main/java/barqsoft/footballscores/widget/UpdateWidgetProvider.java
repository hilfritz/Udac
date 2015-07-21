package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by Hilfritz P. Camallere on 6/28/2015.
 */
public class UpdateWidgetProvider extends AppWidgetProvider {
    private static String TAG = "UpdateWidgetProvider";
    public UpdateWidgetProvider() {
        super();
    }
    /**
     * this method is called every 30 mins as specified on widgetinfo.xml
     * this method is also called on every phone reboot
     **/

    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager,int[] appWidgetIds) {
        Log.d(TAG, "onUpdate() is called.");
        //Toast.makeText(context, " onUpdate() is called", Toast.LENGTH_SHORT).show();
    /*int[] appWidgetIds holds ids of multiple instance
     * of your widget
     * meaning you are placing more than one widgets on
     * your homescreen*/
        int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            RemoteViews remoteViews = updateWidgetListView(context,appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.widget_update);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);

        remoteViews = addOpenAppListener(context, remoteViews, appWidgetId);



        return remoteViews;
    }

    /**
     * @see https://stackoverflow.com/questions/1937236/launching-activity-from-widget
     */
    private RemoteViews addOpenAppListener(Context context, RemoteViews remoteViews, int appId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appId);  // Identifies the particular widget...
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Make the pending intent unique...
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Add click on header
        remoteViews.setOnClickPendingIntent(R.id.mainRelativeLayout,pendIntent);
        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
}
