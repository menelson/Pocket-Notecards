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
                /*int id = item.getItemId();
                if (id == R.id.action_settings) {
                return true;  }*/
                switch(item.getItemId()) {
                    case R.id.action_settings:
                        break;
                    case R.id.create_cards:
                        //launchCreateQuestion();
                        break;
                    default:
                        break;
                }
        return super.onOptionsItemSelected(item);
    }

    //Go to Create Question Screen
    public void launchCreateQuestion(View v) {
        //Intent intentCreate = new Intent(this, CreateQuestion.class);
        //startActivity(intentCreate);
    }

    //Go to Settings
    public void launchSettings(View view) {
        //Intent intentSettings = new Intent(this, CardSettings.class);
        //startActivity(intentSettings);
    }
}
