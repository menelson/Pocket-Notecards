package com.nelson.flashcards.study;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class Welcome extends ActionBarActivity {
    protected Button deck1, deck2, deck3, deck4, deck5;
    private String [] subjectArray = new String [5];
    private int [] fontSizeArray = new int [5];
    protected NoteCardDB db = new NoteCardDB(this);
    protected Button [] buttons = new Button [5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Resources res = getResources();
        Drawable shape = res.getDrawable(R.drawable.rounded_button);

        buttons[0] = (Button)findViewById(R.id.deck1);
        buttons[1] = (Button)findViewById(R.id.deck2);
        buttons[2] = (Button)findViewById(R.id.deck3);
        buttons[3] = (Button)findViewById(R.id.deck4);
        buttons[4] = (Button)findViewById(R.id.deck5);

        //deck2.setBackground(shape);



        initializeSubjectArray();
        initializeFontSizeArray();
        setButtonNames(buttons, subjectArray);
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

    //Go to Settings
    public void launchSettings(MenuItem item) {
        Intent intentSettings = new Intent(this, CardSettings.class);
        startActivity(intentSettings);
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
        //Toast.makeText(this,subjectName, Toast.LENGTH_LONG).show();
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
