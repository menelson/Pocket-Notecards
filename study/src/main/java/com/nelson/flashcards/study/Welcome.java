package com.nelson.flashcards.study;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;


public class Welcome extends ActionBarActivity {
    protected String folder = "PocketNoteCards";
    protected String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
    public File appDirectory = new File(filePath);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

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

    //Go Study dude
    public void launchStudyActivity(View view) {
        Intent intentCreate = new Intent(this, Study.class);
        startActivity(intentCreate);
    }

    //Go to Create Question Screen
    public void launchCreateQuestion(MenuItem item) {
        Intent intentCreate = new Intent(this, CreateQuestion.class);
        startActivity(intentCreate);
    }

    public void launchCreateActivity(View view) {
        Intent intentCreate = new Intent(this, CreateQuestion.class);
        startActivity(intentCreate);
    }

    //Go to Settings via Menu
    public void launchSettings(MenuItem item) {
        Intent intentSettings = new Intent(this, CardSettings.class);
        startActivity(intentSettings);
    }

    //Go to settings via textview click
    public void launchSettingsActivity(View view) {
        Intent intent = new Intent(this, CardSettings.class);
        startActivity(intent);
    }

    //Import via menu
    public void launchFileImport(MenuItem item) {
        Intent intent = new Intent(this, ImportFile.class);
        startActivity(intent);
    }

    //Import via Textview
    public void launchImportActivity(View view) {
        Intent intent = new Intent(this, ImportFile.class);
        startActivity(intent);
    }

}
