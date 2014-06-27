/*
 * @Author Mike Nelson
 * 
 * CardSettings.java allows for file names and font sizes to be saved in SharedPreferences
 */

package com.nelson.flashcards.study;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;


public class CardSettings extends Activity {
    NoteCardDB db = new NoteCardDB(this);
    ArrayList<Deck> deckArrayList = new ArrayList<Deck>();
    ArrayList<Float> fontSize = new ArrayList<Float>();
    float [] fonts = new float [10];
    float defaultFont = 16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_settings);
        ListView listView = (ListView)findViewById(R.id.settings_list);
        initializeSettings();
        initializeArrayListView();
        initializeFontSizes();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,deckArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displaySettings(deckArrayList.get(position), position);
                adapter.notifyDataSetInvalidated();
                view.setAlpha(1);
            }
        });

    }

    public void updateSettings(Deck deck) {
        db.open();
        db.updateSettingsRecord(deck.getRowId(), deck.getDeckSubject(), deck.getFontSize());
        db.close();
    }

    public void initializeSettings() {
        db.open();
        for (int i = 1; i < 6; i++) {
            Cursor cursor = db.getSettingsRecord(i);
            if(!cursor.moveToFirst()) {
                db.insertSettings("Not Assigned", 16);
            }
        }
        db.close();
    }

    public void initializeFontSizes () {
        for(int i = 0; i < 10; i++)
        {
            fontSize.add(defaultFont);
            fonts[i] = defaultFont;
            defaultFont+=4;
        }
    }

    public void initializeArrayListView () {
        db.open();
        for(int i = 1; i < 6; i++) {
            Cursor cursor = db.getSettingsRecord(i);
            Deck tmpDeck = new Deck(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            deckArrayList.add(tmpDeck);
        }
        db.close();
    }

    public void displaySettings(Deck deck, final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        dialog.setTitle("Subject: " + deck.getDeckSubject());
        TextView subjectText = new TextView(this);
        subjectText.setText("Change Note Card Subject: ");
        linearLayout.addView(subjectText);

        final Spinner spinner = new Spinner(this);

        String [] subjects = {"Multiplication", "Spanish", "Java", "Programming", "Music"};
        ArrayList<String> subjectList = new ArrayList<String>();
        for (int i = 0; i < subjects.length; i++) {
            subjectList.add(subjects[i]);
        }
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(subjectAdapter);

        linearLayout.addView(spinner);

        TextView fontText = new TextView(this);
        fontText.setText("Change Font Size: ");
        linearLayout.addView(fontText);
        final Spinner fontSpinner = new Spinner(this);
        ArrayAdapter<Float> adapterFont = new ArrayAdapter<Float> (this, android.R.layout.simple_spinner_item, fontSize);
        adapterFont.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(adapterFont);

        linearLayout.addView(fontSpinner);
        dialog.setView(linearLayout);

        dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subject = spinner.getSelectedItem().toString();
                String fontString = fontSpinner.getSelectedItem().toString();
                float tmpFont = Float.parseFloat(fontString);
                Deck tmpDeck = new Deck(deckArrayList.get(position).getRowId(), subject, (int)tmpFont);
                deckArrayList.set(position, tmpDeck);
                Toast.makeText(getApplicationContext(), "Deck: " + tmpDeck, Toast.LENGTH_SHORT).show();
                updateSettings(tmpDeck);
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        });

        dialog.show();
    }


	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}	
	

}



