package barqsoft.footballscores.model;

/**
 * Created by Hilfritz P. Camallere on 7/7/2015.
 */
public class Match {

    /*match_values.put(DatabaseContract.scores_table.MATCH_ID,matchId);
    match_values.put(DatabaseContract.scores_table.DATE_COL,mDate);
    match_values.put(DatabaseContract.scores_table.TIME_COL,mTime);
    match_values.put(DatabaseContract.scores_table.HOME_COL,home);
    match_values.put(DatabaseContract.scores_table.AWAY_COL,away);
    match_values.put(DatabaseContract.scores_table.HOME_GOALS_COL,homeGoals);
    match_values.put(DatabaseContract.scores_table.AWAY_GOALS_COL,awayGoals);
    match_values.put(DatabaseContract.scores_table.LEAGUE_COL,league);
    match_values.put(DatabaseContract.scores_table.MATCH_DAY,matchDay);*/

    //Match data
    String league = null;
    String mDate = null;
    String mTime = null;
    String home = null;
    String away = null;
    String homeGoals = null;
    String awayGoals = null;
    String matchId = null;
    String matchDay = null;




    public Match(String league, String mDate, String mTime, String home, String away, String home_goals, String awayGoals, String matchId, String matchDay) {
        this.league = league;
        this.mDate = mDate;
        this.mTime = mTime;
        this.home = home;
        this.away = away;
        homeGoals = home_goals;
        this.awayGoals = awayGoals;
        this.matchId = matchId;
        this.matchDay = matchDay;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    public String getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(String awayGoals) {
        this.awayGoals = awayGoals;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(String matchDay) {
        this.matchDay = matchDay;
    }
}
