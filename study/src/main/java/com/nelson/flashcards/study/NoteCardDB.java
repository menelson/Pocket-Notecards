package com.nelson.flashcards.study;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mike on 5/29/2014.
 */
public class NoteCardDB extends SQLiteOpenHelper {
    //Tutorial Examples Modify
    public static final String TABLE_QUESTIONS = "questions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "question";

    private static final String DATABASE_NAME = "questions.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_QUESTIONS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    public NoteCardDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(NoteCardDB.class.getName(),
        "upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }
}
