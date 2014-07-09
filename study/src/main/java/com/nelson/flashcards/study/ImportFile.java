/*
 * @Author Mike Nelson
 * 
 * CreateQuestion.java writes questions & answers to the correct file
 */

package com.nelson.flashcards.study;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ImportFile extends Activity {
	
	private ArrayList<String> question = new ArrayList<String>();
	private ArrayList<String> answer = new ArrayList<String>();	
	private String quest;
	private String answ;
	protected String fileName;
	protected String folder = "PocketNoteCards";
	private String type1 = "Question";
	private String type2 = "Answer";
	private String empty = "";	
	protected Intent intent2 = getIntent();	//does nothing?
    protected String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/";
    protected int length = filePath.length();
    public File appDirectory = new File(filePath);
    ListView listView;
    NoteCardDB db = new NoteCardDB(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_file);

        listView = (ListView)findViewById(R.id.file_list);

        if(!android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "External Media Not Mounted", Toast.LENGTH_LONG).show();
        }

        if(!appDirectory.exists()) {
            appDirectory.mkdir();
            Toast.makeText(this, "Dir created: " + folder, Toast.LENGTH_SHORT).show();
        }

        File [] fileArray = populateFileArray();
        final ArrayList<File> fileArrayList = initializeFiles(fileArray, fileArray.length);
        final ArrayList<String> fileNameArrayList = getFileNames(fileArrayList);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fileNameArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String test = fileNameArrayList.get(position);
                //Toast.makeText(getApplicationContext(), test, Toast.LENGTH_SHORT).show();
                writeFileToDB(fileNameArrayList.get(position), fileArrayList.get(position));

            }
        });

    }

    public File [] populateFileArray () {
        File [] tmpFileArray;
        tmpFileArray = appDirectory.listFiles();

        return tmpFileArray;
    }

    public ArrayList<File> initializeFiles (File [] files, int size) {
        ArrayList<File> tmpList = new ArrayList<File>();
        for(int i = 0; i < size; i++)
            tmpList.add(files[i]);
        return tmpList;
    }

    public ArrayList<String> getFileNames (ArrayList<File> files) {
        ArrayList<String> fileNames = new ArrayList<String>();
        String tmpString;
        for(int i = 0; i < files.size(); i++) {
            tmpString = files.get(i).getAbsolutePath().toString();
            fileNames.add(tmpString.substring(length, tmpString.length()-4));
        }

        return fileNames;
    }

    public void writeFileToDB(String subject, File file) {
        FileReader fileReader = new FileReader();
        ArrayList<Card> cards = fileReader.importFile(file, subject);
        db.open();
        for(int i = 0; i < cards.size(); i++) {
            db.insertRecord(subject, cards.get(i).getQuestion(), cards.get(i).getAnswer(), 0);
            Log.d("INFO", cards.get(i).getQuestion() + " Added");
        }
        db.close();
        Toast.makeText(this, subject+" has been added to the database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.create_question, menu);
        return true;
    }
}
