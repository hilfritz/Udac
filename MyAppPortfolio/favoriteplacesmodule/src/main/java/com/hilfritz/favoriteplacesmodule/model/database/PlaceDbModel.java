package com.hilfritz.favoriteplacesmodule.model.database;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.hilfritz.favoriteplacesmodule.JodaTimeDateTimeUtil;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;

import java.util.List;

/**
 * Created by Hilfritz P. Camallere on 1/2/2016.
 * added id = BaseColumns._ID so that cursorLoaders and cursors can be able to parse this model easily
 */
@Table(name="Place",  id = BaseColumns._ID)
public class PlaceDbModel extends Model {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_UPDATE_DATETIME = "update_datetime";
    public static final String COLUMN_CREATE_DATETIME = "create_datetime";
    public static final String COLUMN_ID = "id";

    /**
     * Autoincrement on ActiveAndroid
     * no need, see http://stackoverflow.com/questions/30692473/how-to-make-a-field-auto-increment-in-activeandroid-orm
     * @see https://github.com/pardom/ActiveAndroid/issues/22#issuecomment-81670905
     */
    //@Column(name=COLUMN_ID, index = true, notNull = true)
    //public long id;


    @Column(name=COLUMN_NAME)
    public String name;

    @Column(name = COLUMN_DESCRIPTION)
    public String description;

    /**
     * values are
     * <ul>
     *     <li>0- STATUS_DELETED</li>
     *     <li>1- STATUS_ACTIVE</li>
     * </ul>
     */
    @Column(name = COLUMN_STATUS)
    int status = 0;

    @Column(name=COLUMN_UPDATE_DATETIME)
    String update_datetime = null;

    @Column(name=COLUMN_CREATE_DATETIME)
    String create_datetime =  null;

    public static int STATUS_ACTIVE = 1;
    public static int STATUS_DELETED = 0;


    public PlaceDbModel(){

    }

    public PlaceDbModel(String name, String description) {
        super();
        this.name = name;
        this.description = description;
        this.create_datetime = (new DateTime()).toString();
        this.update_datetime = this.create_datetime;
        this.status = 1;
    }


    public static List<PlaceDbModel> getAllActive() {
        return getAll(STATUS_ACTIVE);
    }
    public static List<PlaceDbModel> getAllDeleted() {
        return getAll(STATUS_DELETED);
    }

    public static List<PlaceDbModel> getAll(int status) {
        // This is how you execute a query
        return new Select()
                .from(PlaceDbModel.class)
                .execute();
    }

    public static PlaceDbModel getPlace(int placeId){
        // This is how you execute a query
        return new Select()
                .from(PlaceDbModel.class)
                .where("_id = ?", placeId)
                .executeSingle();
    }

    public static PlaceDbModel getPlace(int placeId, int status){
        // This is how you execute a query
        return new Select()
                .from(PlaceDbModel.class)
                .where("_id = ? and status = ?", placeId, status)
                .orderBy("id DESC")
                .executeSingle();
    }


    public DateTime getUpdateDatetime() {
        return JodaTimeDateTimeUtil.formatDateStrToDateTime(update_datetime, JodaTimeDateTimeUtil.FORMAT_1);
    }

    public DateTime getCreateDatetime() {
        return JodaTimeDateTimeUtil.formatDateStrToDateTime(create_datetime, JodaTimeDateTimeUtil.FORMAT_1);
    }

    // Return cursor for result set for all todo items

    public static Cursor fetchResultCursor() {
        String tableName = Cache.getTableInfo(PlaceDbModel.class).getTableName();
        // Query all items without any conditions
        /**
         * String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
         * from(PlaceDbModel.class).toSql();
         */
        String resultRecords = new Select(tableName + ".* ").
                from(PlaceDbModel.class).toSql();
        // Execute query on the underlying ActiveAndroid SQLite database
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);
        return resultCursor;
    }
/*
    public static Cursor fetchResultCursor(String name) {
        String tableName = Cache.getTableInfo(PlaceDbModel.class).getTableName();
        // Query all items without any conditions
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(PlaceDbModel.class).
                where(PlaceDbModel.COLUMN_NAME+" LIKE %?%", name).toSql();
        // Execute query on the underlying ActiveAndroid SQLite database
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);
        return resultCursor;
    }
*/

}
