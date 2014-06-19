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
    public static final String KEY_CARDNAME = "card_name";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_KNOWN = "known";
    public static final String KEY_DECKNAME = "deck_name"; //Subject for each note card topic
    public static final String KEY_FONTSIZE = "font_size";
    private static final String TAG = "NoteCardDB";

    private static final String DATABASE_NAME = "NoteCards";
    private static final String DATABASE_TABLE = "Flash Cards";
    private static final String DB_SETTINGS_TABLE = "Settings";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_NOTECARD =
            " create table if not exists " + DATABASE_TABLE + " (id integer primary key autoincrement, "
            + "card_name VARCHAR not null, question VARCHAR not null, answer VARCHAR, known INTEGER );";

    private static final String DATABASE_CREATE_CARD_SETTINGS =
            " create table if not exists " + DB_SETTINGS_TABLE + " ( " + KEY_ROWID
                    + " integer primary key autoincrement, " + KEY_DECKNAME + " VARCHAR, "
                    + KEY_FONTSIZE + " INTEGER );";

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
                db.execSQL(DATABASE_CREATE_NOTECARD);
                db.execSQL(DATABASE_CREATE_CARD_SETTINGS);
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
    public long insertRecord (String cardName, String question, String answer, int known) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CARDNAME, cardName);
        initialValues.put(KEY_QUESTION, question);
        initialValues.put(KEY_ANSWER, answer);
        initialValues.put(KEY_KNOWN, known);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //Delete one record
    public boolean deleteRecord(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllRecords(String title) {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_CARDNAME, KEY_QUESTION, KEY_ANSWER, KEY_ANSWER},
                KEY_CARDNAME + "=\"" + title +"\"", null, null, null, null, null);
    }

    public Cursor getNoteCardTitles() {
        return db.query(true, DATABASE_TABLE,
                new String[] {KEY_ROWID, KEY_CARDNAME, KEY_QUESTION, KEY_ANSWER, KEY_ANSWER},
                null, null, KEY_CARDNAME, null, null, null);
    }

    //Get one Record
    public Cursor getRecord(long rowId) throws SQLException {
        Cursor qCursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_CARDNAME,
            KEY_QUESTION, KEY_ANSWER, KEY_KNOWN}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if(qCursor != null) {
            qCursor.moveToFirst();
        }
        return qCursor;
    }

    //Update one record
    public boolean updateRecord(long rowId, String cardName, String question, String answer, int known) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CARDNAME, cardName);
        newValues.put(KEY_QUESTION, question);
        newValues.put(KEY_ANSWER, answer);
        newValues.put(KEY_KNOWN, known);
        return db.update(DATABASE_TABLE, newValues, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //Methods for interacting with the Settings Table
    //Insert Settings info
    public long insertSettings(String deckName, int fontSize) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DECKNAME, deckName);
        initialValues.put(KEY_FONTSIZE, fontSize);
        return db.insert(DB_SETTINGS_TABLE, null, initialValues);
    }

    public boolean updateSettingsRecord(long rowId, String deckName, int fontSize) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DECKNAME, deckName);
        newValues.put(KEY_FONTSIZE, fontSize);
        return db.update(DB_SETTINGS_TABLE, newValues, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getSettingsRecord(long rowId) {
        Cursor cursor = db.query(true, DB_SETTINGS_TABLE,
                new String[] {KEY_ROWID, KEY_DECKNAME, KEY_FONTSIZE},
                KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllSettings(){
        return db.query(true, DB_SETTINGS_TABLE,
                new String[] {KEY_ROWID, KEY_DECKNAME, KEY_FONTSIZE},
                null, null, null, null, null, null, null);
    }





}
