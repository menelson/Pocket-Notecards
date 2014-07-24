package com.nelson.flashcards.study;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mike on 7/17/14.
 */
public class Study extends Activity {

    protected NoteCardDB db = new NoteCardDB(this);
    protected String folder = "PocketNoteCards";
    protected String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
    public File appDirectory = new File(filePath);
    private ArrayList<String> subjectList = new ArrayList<String>();
    private String subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ListView listView = (ListView)findViewById(R.id.subject_list);

        getUniqueNoteCardSubject();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subject = subjectList.get(position);
                launchNoteCard(view, subject);
            }
        });

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
    public void onResume() {
        super.onResume();

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
    public void launchNoteCard(View view, String subject) {
        String subjectName = subject;
        int fontSize = 12;

        Intent intent = new Intent(this, NoteCard.class);
        intent.putExtra("Subject", subjectName);
        intent.putExtra("Font", fontSize);
        startActivity(intent);
    }

    public void getUniqueNoteCardSubject() {
        db.open();
        Cursor cursor = db.getNoteCardTitles();
        if (cursor.moveToFirst()) {
            subjectList.add(cursor.getString(1));
            while (cursor.moveToNext()) {
                subjectList.add(cursor.getString(1));
            }
        }
        db.close();
    }
}
