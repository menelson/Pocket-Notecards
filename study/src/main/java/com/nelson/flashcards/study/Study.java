package com.nelson.flashcards.study;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Mike on 7/17/14.
 */
public class Study extends Activity {
    private String [] subjectArray = new String [5];
    private int [] fontSizeArray = new int [5];
    protected NoteCardDB db = new NoteCardDB(this);
    protected Button[] buttons = new Button [5];
    protected String folder = "PocketNoteCards";
    protected String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
    public File appDirectory = new File(filePath);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //setContentView(R.layout.welcome_page);
        //Resources res = getResources();
        //Drawable shape = res.getDrawable(R.drawable.rounded_button);

        buttons[0] = (Button)findViewById(R.id.deck1);
        buttons[1] = (Button)findViewById(R.id.deck2);
        buttons[2] = (Button)findViewById(R.id.deck3);
        buttons[3] = (Button)findViewById(R.id.deck4);
        buttons[4] = (Button)findViewById(R.id.deck5);

        //deck2.setBackground(shape);
        initializeSubjectArray();
        initializeFontSizeArray();
        setButtonNames(buttons, subjectArray);

        //Creating a Directory for Application Text files.
        if(!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "External Media Not Mounted", Toast.LENGTH_LONG).show();
        } else {
            if (!appDirectory.exists()) {
                appDirectory.mkdir();
                //Toast.makeText(this, "Dir created: " + folder, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void test(View view) {
        Toast.makeText(this, "TextView Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeSubjectArray();
        setButtonNames(buttons, subjectArray);
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.welcome, menu);
            return true;
        }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_settings:
                launchSettings(item);
                break;
            case R.id.create_cards:
                launchCreateQuestion(item);
                break;
            case R.id.file_import:
                launchFileImport(item);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Go to Create Question Screen
    public void launchCreateQuestion(MenuItem item) {
        Intent intentCreate = new Intent(this, CreateQuestion.class);
        startActivity(intentCreate);
    }


    //Go to Settings via Menu
    public void launchSettings(MenuItem item) {
        Intent intentSettings = new Intent(this, CardSettings.class);
        startActivity(intentSettings);
    }



    //Import via menu
    public void launchFileImport(MenuItem item) {
        Intent intent = new Intent(this, ImportFile.class);
        startActivity(intent);
    }



    //Launch NoteCard Activity based on Deck Selected.
    public void launchNoteCard(View view) {
        String subjectName = "";
        int fontSize = 12;
        switch (view.getId()) {
            case R.id.deck1:
                subjectName = subjectArray[0];
                fontSize = fontSizeArray[0];
                break;
            case R.id.deck2:
                subjectName = subjectArray[1];
                fontSize = fontSizeArray[1];
                break;
            case R.id.deck3:
                subjectName = subjectArray[2];
                fontSize = fontSizeArray[2];
                break;
            case R.id.deck4:
                subjectName = subjectArray[3];
                fontSize = fontSizeArray[3];
                break;
            case R.id.deck5:
                subjectName = subjectArray[4];
                fontSize = fontSizeArray[4];
                break;
            default:
                break;
        }
        Intent intent = new Intent(this, NoteCard.class);
        intent.putExtra("Subject", subjectName);
        intent.putExtra("Font", fontSize);
        startActivity(intent);
    }

    //Get Stored NoteCard Table Name
    public String getNoteCardSubject(long rowID) {
        String subject = null;
        db.open();
        Cursor cursor = db.getSettingsRecord(rowID);
        if (cursor.moveToFirst()) {
            subject = cursor.getString(1);
        }
        db.close();
        return subject;
    }

    public void setButtonNames(Button [] buttons, String [] subjectArray) {
        for(int i = 0; i < subjectArray.length; i++) {
            if(subjectArray[i] == null) {
                buttons[i].setText("Not Assigned");
            } else {
                buttons[i].setText(subjectArray[i]);
            }
        }
    }

    public void initializeSubjectArray(){
        for(int i = 0; i < 5; i++) {
            long tmpRowId = i + 1;
            subjectArray[i] = getNoteCardSubject(tmpRowId);
        }
    }

    public int getFontSize(long rowId) {
        int fSize = 12;
        db.open();
        Cursor cursor = db.getSettingsRecord(rowId);
        if(cursor.moveToFirst()) {
            fSize = cursor.getInt(2);
        }
        db.close();
        return fSize;
    }

    public void initializeFontSizeArray() {
        for(int i = 0; i < fontSizeArray.length; i++) {
            long tmpRowId = i + 1;
            fontSizeArray[i] = getFontSize(tmpRowId);
        }
    }
}
