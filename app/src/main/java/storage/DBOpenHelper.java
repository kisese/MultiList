package storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brayo on 2/24/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QUIZ_DB";
    public static final String TABLE_NAME = "live_quiz";


    public static final int VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_DATE = "date";
    public static final String KEY_MSG = "message";
    public static final String KEY_INDEX = "key_index";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ANSWERED = "answered";


    public static final String SCRIPT = "create table " + TABLE_NAME + " ("
            + KEY_ID + " integer primary key autoincrement not null, " +
            KEY_DATE+ " text not null, " +
            KEY_MSG+ " text not null, " +
            KEY_INDEX+ " text not null, " +
            KEY_TYPE+ " text not null, " +
            KEY_ANSWERED + " text not null);";



    public DBOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
