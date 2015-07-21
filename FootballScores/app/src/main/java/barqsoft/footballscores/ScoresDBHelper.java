package barqsoft.footballscores;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract.scores_table;
import barqsoft.footballscores.model.Match;


/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "scores.db";
    private static final int DATABASE_VERSION = 5;
    private static final String TAG = "ScoresDBHelper";

    public ScoresDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CreateScoresTable = "CREATE TABLE IF NOT EXISTS " + DatabaseContract.SCORES_TABLE + " ("
                + scores_table._ID + " INTEGER PRIMARY KEY,"
                + scores_table.DATE_COL + " TEXT NOT NULL,"
                + scores_table.TIME_COL + " INTEGER NOT NULL,"
                + scores_table.HOME_COL + " TEXT NOT NULL,"
                + scores_table.AWAY_COL + " TEXT NOT NULL,"
                + scores_table.LEAGUE_COL + " INTEGER NOT NULL,"
                + scores_table.HOME_GOALS_COL + " TEXT NOT NULL,"
                + scores_table.AWAY_GOALS_COL + " TEXT NOT NULL,"
                + scores_table.MATCH_ID + " INTEGER NOT NULL,"
                + scores_table.MATCH_DAY + " INTEGER NOT NULL,"
                + " UNIQUE ("+scores_table.MATCH_ID+") ON CONFLICT REPLACE"
                + " );";
        try {
            db.execSQL(CreateScoresTable);
        }catch(Exception ex){
            Log.d(TAG, "onCreate() database table create failed.");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //Remove old values when upgrading.
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.SCORES_TABLE);
    }


    /**
     *
     * @param date String format should be 2015-07-01
     * @param context
     * @return List<Match>
     */
    public static List<Match> getAllScoresByDate(String date, Context context){
        List<Match> matchList =  new ArrayList<Match>();
        String whereClause = DatabaseContract.scores_table.DATE_COL+" = ?";
        String[] whereArgs = new String[] {
                date
        };

        if (date==null || date.isEmpty()==true || date.equalsIgnoreCase("")) {
            Log.d(TAG, "getAllScoresByDate() date is not valid");
            return matchList;
        }

        SQLiteDatabase database = context.openOrCreateDatabase(ScoresDBHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
        if (database==null) {
            Log.d(TAG, "getAllScoresByDate() database variable is null");
            return matchList;
        }

        Cursor queryResultCursor = database.query(DatabaseContract.SCORES_TABLE, null, whereClause, whereArgs, null, null, null);
        if (queryResultCursor==null){
            Log.d(TAG, "getAllScoresByDate() queryResultcursor is null");
            return matchList;
        }
        if (queryResultCursor.moveToFirst()){
            do{

                String home = queryResultCursor.getString(ScoresAdapter.COL_HOME);
                String away = queryResultCursor.getString(ScoresAdapter.COL_AWAY);
                String date2 = queryResultCursor.getString(ScoresAdapter.COL_MATCHTIME);
                int homeGoals = queryResultCursor.getInt(ScoresAdapter.COL_HOME_GOALS);
                int awayGoals = queryResultCursor.getInt(ScoresAdapter.COL_AWAY_GOALS);
                double matchId = queryResultCursor.getDouble(ScoresAdapter.COL_ID);
                String matchDay = Utilies.getMatchDay(queryResultCursor.getInt(ScoresAdapter.COL_MATCHDAY),
                        queryResultCursor.getInt(ScoresAdapter.COL_LEAGUE));
                String league = Utilies.getLeague(queryResultCursor.getInt(ScoresAdapter.COL_LEAGUE));
                Match match = new Match(league,
                                date2,
                                "",
                                home,
                                away,
                                homeGoals+"",
                                awayGoals+"",
                                matchId+"",
                                matchDay);
                matchList.add(match);
            }while(queryResultCursor.moveToNext());
        }
        return matchList;

    }
}
