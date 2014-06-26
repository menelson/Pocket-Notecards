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
import android.widget.Toast;


public class Welcome extends ActionBarActivity {
    protected Button deck1, deck2, deck3, deck4, deck5;
    private String [] subjectArray = new String [5];
    NoteCardDB db = new NoteCardDB(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Resources res = getResources();
        Drawable shape = res.getDrawable(R.drawable.rounded_button);

        deck1 = (Button)findViewById(R.id.deck1);
        deck2 = (Button)findViewById(R.id.deck2);
        deck3 = (Button)findViewById(R.id.deck3);
        deck4 = (Button)findViewById(R.id.deck4);
        deck5 = (Button)findViewById(R.id.deck5);

        deck2.setBackground(shape);

        Button [] buttons = {deck1, deck2, deck3, deck4, deck5};

        //subjectArray[0] = "Multiplication";
        //subjectArray[1] = "Java";
        //subjectArray[2] = "Spanish";
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
    //Null Pointer Exception
    public void launchSettings(MenuItem item) {
        Intent intentSettings = new Intent(this, CardSettings.class);
        startActivity(intentSettings);
    }

    //Launch NoteCard Activity based on Deck Selected.
    public void launchNoteCard(View view) {
        String subjectName = "";
        switch (view.getId()) {
            case R.id.deck1:
                subjectName = subjectArray[0];
                break;
            case R.id.deck2:
                subjectName = subjectArray[1];
                break;
            case R.id.deck3:
                subjectName = subjectArray[2];
                break;
            case R.id.deck4:
                subjectName = subjectArray[3];
                break;
            case R.id.deck5:
                subjectName = subjectArray[4];
                break;
            default:
                break;
        }
        //Intent intent = new Intent(this, NoteCard.class);
        //intent.putExtra("Subject", subjectName);
        //startActivity(intent);
        Toast.makeText(this,subjectName, Toast.LENGTH_LONG).show();
    }

    //Get Stored NoteCard Table Name
    public String getNoteCardSubject(long rowID) {
        String subject = null;
        db.open();
        Cursor cursor = db.getSettingsRecord(rowID);
        if(cursor == null)
            subject = "Not Assigned";
        else {
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    subject = cursor.getString(1);
                }
            }
        }
        return subject;
    }

    public void setButtonNames(Button [] buttons, String [] subjectArray) {
        for(int i = 0; i < subjectArray.length; i++) {
            if(subjectArray[i] == null) {
                buttons[i].setText("Not Working");
            } else {
                buttons[i].setText(subjectArray[i]);
            }
        }
    }

    public void initializeSubjectArray(){
        for(int i = 0; i < 5; i++)
            subjectArray[i] = getNoteCardSubject((long)i+1);
    }




}
