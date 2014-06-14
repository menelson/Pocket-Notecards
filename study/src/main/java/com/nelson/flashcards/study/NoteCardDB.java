package com.nelson.flashcards.study;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;

import java.util.Currency;


/**
 * Created by Mike on 5/29/2014.
 */
public class NoteCardDB {

    public static final String KEY_ROWID = "id";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_KNOWN = "known";
    private static final String TAG = "NoteCardDB";

    private static final String DATABASE_NAME = "NoteCards";
    private static final String DATABASE_TABLE = "Multiplication";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            " create table if not exists " + DATABASE_TABLE + " (id integer primary key autoincrement, "
            + "question VARCHAR not null, answer VARCHAR, known INTEGER );";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public NoteCardDB(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //Open the DB
    public NoteCardDB open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //Close the DB
    public void close() {
        DBHelper.close();
    }

    //Insert Questions and Answers
    public long insertRecord (String question, String answer, int known) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_QUESTION, question);
        initialValues.put(KEY_ANSWER, answer);
        initialValues.put(KEY_KNOWN, known);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //Delete one record
    public boolean deleteRecord(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllRecords() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_QUESTION, KEY_ANSWER, KEY_ANSWER},
                null, null, null, null, null, null);
    }

    //Get one Record
    public Cursor getRecord(long rowId) throws SQLException {
        Cursor qCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
            KEY_QUESTION, KEY_ANSWER, KEY_KNOWN}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if(qCursor != null) {
            qCursor.moveToFirst();
        }
        return qCursor;
    }

    //Update one record
    public boolean updateRecord(long rowId, String question, String answer, int known) {

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_QUESTION, question);
        newValues.put(KEY_ANSWER, answer);
        newValues.put(KEY_KNOWN, known);
        return db.update(DATABASE_TABLE, newValues, KEY_ROWID + "=" + rowId, null) > 0;
    }


}
