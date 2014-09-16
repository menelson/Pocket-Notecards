/*
 * @Author Mike Nelson
 * 
 *
 */

package com.nelson.flashcards.study;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ImportFile extends ListActivity {

    private NoteCardDB db = new NoteCardDB(this);
    private FileAdapter adapter;
    private File currentDir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/");
        fill(currentDir);

    }

    private void fill(File file){
        File [] dirs = file.listFiles();
        this.setTitle("Current Dir: " + file.getName());
        List<FileItem> dir = new ArrayList<FileItem>();
        List<FileItem>files = new ArrayList<FileItem>();

        try {
            for(File f: dirs){
                if(f.isDirectory()){
                    File[] fileBuffer = f.listFiles();
                    int buffer;
                    if(fileBuffer != null) {
                        buffer = fileBuffer.length;
                    }
                    else buffer = 0;

                    String num_item = String.valueOf(buffer);

                    if(buffer == 0)
                        num_item = num_item + " item";
                    else
                        num_item = num_item + " items";

                    dir.add(new FileItem(f.getName(),num_item, "directory_image", f.getAbsolutePath()));
                }
                else{
                    files.add(new FileItem(f.getName(), f.length() + " Byte", "file_image", f.getAbsolutePath()));
                }

            }
        } catch (Exception e) {}
        Collections.sort(dir);
        Collections.sort(files);
        dir.addAll(files);

        if(!file.getName().equalsIgnoreCase("sdcard"))
          dir.add(0, new FileItem("..", "Parent Directory", "directory_image", file.getParent()));
        adapter = new FileAdapter(ImportFile.this, R.layout.import_file, dir);
        this.setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("Clicked: ", "Item Clicked");
        FileItem o = adapter.getFileItem(position);
        if(o.getImage().equalsIgnoreCase("directory_image") || o.getImage().equalsIgnoreCase("directory_up")){
            currentDir = new File(o.getPath());
            Log.d("Current Dir: ", currentDir.toString());
            fill(currentDir);
        }
        else
        {
            File selected = new File(o.getPath());
            Log.d("File clicked", ""+selected);
            writeFileToDB(o.getName().substring(0, o.getName().length()-4), selected);
        }
    }

    public void writeFileToDB(final String subject, final File file) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ReadFile fileReader = new ReadFile();
                ArrayList<Card> cards = fileReader.importFile(file, subject);
                db.open();
                for(int i = 0; i < cards.size(); i++) {
                    db.insertRecord(subject, cards.get(i).getQuestion(), cards.get(i).getAnswer(), 0);
                    Log.d("INFO", cards.get(i).getQuestion() + " Added");
                }
                db.close();

            }
        };

        new Thread(runnable).start();
        Toast.makeText(this, subject+" has been added to the database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.create_question, menu);
        return true;
    }
}
