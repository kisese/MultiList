package storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Brayo on 5/29/2015.
 */
public class MessagesTableAdapter {

    SQLiteDatabase database_ob;
    DBOpenHelper openHelper_ob;
    Context context;

    public MessagesTableAdapter(Context context) {
        this.context = context;
    }

    public MessagesTableAdapter opnToRead() {
        openHelper_ob = new DBOpenHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getReadableDatabase();
        return this;

    }

    public MessagesTableAdapter opnToWrite() {
        openHelper_ob = new DBOpenHelper(context,
                openHelper_ob.DATABASE_NAME, null, openHelper_ob.VERSION);
        database_ob = openHelper_ob.getWritableDatabase();
        return this;
    }

    public void Close() {
        database_ob.close();
    }

    public long insertDetails( String date, String message, int index, int type, boolean answered) {
        ContentValues contentValues = new ContentValues();
        // contentValues.put(openHelper_ob.KEY_ID, id);
        contentValues.put(openHelper_ob.KEY_DATE, date);
        contentValues.put(openHelper_ob.KEY_MSG, message);
        contentValues.put(openHelper_ob.KEY_INDEX, index);
        contentValues.put(openHelper_ob.KEY_TYPE, type);
        contentValues.put(openHelper_ob.KEY_ANSWERED, answered);
        opnToWrite();
        long val = database_ob.insert(openHelper_ob.TABLE_NAME, null,
                contentValues);
        Close();
        return val;
    }

    public Cursor queryAll() {
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.KEY_DATE, openHelper_ob.KEY_MSG
                , openHelper_ob.KEY_INDEX, openHelper_ob.KEY_TYPE, openHelper_ob.KEY_ANSWERED};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
                null, null, null,  openHelper_ob.KEY_DATE + " desc");

        return c;

    }

    public Cursor queryFilter(String filter) {
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.KEY_DATE, openHelper_ob.KEY_MSG
                , openHelper_ob.KEY_INDEX, openHelper_ob.KEY_TYPE, openHelper_ob.KEY_ANSWERED};
        String[] selectionArgs = {filter+"%"};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, "name LIKE ?",
                selectionArgs, null, null,  openHelper_ob.KEY_DATE + " desc");

        return c;

    }

    public Cursor querySingle(int nameId) {
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.KEY_DATE, openHelper_ob.KEY_MSG
                , openHelper_ob.KEY_INDEX, openHelper_ob.KEY_TYPE, openHelper_ob.KEY_ANSWERED};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols,
                openHelper_ob.KEY_ID + "=" + nameId, null, null, null,   openHelper_ob.KEY_DATE + " desc");

        return c;

    }

    public long updateDetails(String date, String message, int index, String type, String answered) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(openHelper_ob.KEY_DATE, date);
        contentValues.put(openHelper_ob.KEY_MSG, message);
        contentValues.put(openHelper_ob.KEY_INDEX, index);
        contentValues.put(openHelper_ob.KEY_TYPE, type);
        contentValues.put(openHelper_ob.KEY_ANSWERED, answered);
        opnToWrite();
        long val = database_ob.update(openHelper_ob.TABLE_NAME, contentValues,
                openHelper_ob.KEY_MSG + " = " + message, null);
        Close();
        return val;
    }

    public int deletOneRecord(String message) {
        // TODO Auto-generated method stub
        opnToWrite();
        int val = database_ob.delete(openHelper_ob.TABLE_NAME,
                openHelper_ob.KEY_MSG + " = " + message, null);
        Close();
        return val;
    }

    public int deletAll() {
        // TODO Auto-generated method stub
        opnToWrite();
        int val = database_ob.delete(openHelper_ob.TABLE_NAME,
                null, null);
        Close();
        return val;
    }


    public double sumAll(){
        opnToWrite();
        Cursor c = database_ob.rawQuery("SELECT Sum(price) FROM " + openHelper_ob.TABLE_NAME, null);



        if(c.moveToFirst()) {
            return c.getDouble(0);
        }
        return 1;
    }

    public int getCount(){
        String[] cols = { openHelper_ob.KEY_ID, openHelper_ob.KEY_DATE, openHelper_ob.KEY_MSG
                , openHelper_ob.KEY_INDEX, openHelper_ob.KEY_TYPE, openHelper_ob.KEY_ANSWERED};
        opnToWrite();
        Cursor c = database_ob.query(openHelper_ob.TABLE_NAME, cols, null,
                null, null, null,  openHelper_ob.KEY_DATE + " desc");
        int count = c.getCount();
        return count;

    }

}
