package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresAdapter;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.model.Match;


/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 *
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Match> listItemList = new ArrayList<Match>();

    private Context context = null;
    private int appWidgetId;
    private ScoresAdapter mAdapter;

    public ListProvider(Context context, Intent intent, List<Match> listItemList) {
        this.listItemList = listItemList;
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        int layout = 0;
        //if (position%2 ==0){
            layout = R.layout.widget_list_item;
        //}else{
            //layout = R.layout.widget_list_item2;
        //}

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), layout);
        Match listItem = (Match)listItemList.get(position);
        String scoreStr = Utilies.getScores(
                Integer.valueOf(listItem.getHomeGoals()),
                Integer.valueOf(listItem.getAwayGoals())
        );
        remoteView.setTextViewText(R.id.home_name, listItem.getHome());
        remoteView.setTextViewText(R.id.away_name, listItem.getAway());
        remoteView.setTextViewText(R.id.score_textview, scoreStr);


        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
