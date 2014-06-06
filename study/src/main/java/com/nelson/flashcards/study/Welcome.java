package com.nelson.flashcards.study;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
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
                View v;
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
        Intent intent = new Intent(this, NoteCard.class);
        startActivity(intent);
    }

    //Get Stored NoteCard Table Name
    public void getNoteCardTableName() {

    }
}
