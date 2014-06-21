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
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;


public class CardSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_settings);
        ListView listView = (ListView)findViewById(R.id.settings_list);
        NoteCardDB db = new NoteCardDB(this);

        Deck deck1 = new Deck(1, "Multiplication", 22);
        Deck deck2 = new Deck(2, "null", 24);
        Deck deck3 = new Deck(3, "Java", 26);
        Deck deck4 = new Deck(4, "Programming", 27);
        Deck deck5 = new Deck(5, "Music", 28);

        final ArrayList<Deck> deckArrayList = new ArrayList<Deck>();

        deckArrayList.add(deck1);
        deckArrayList.add(deck2);
        deckArrayList.add(deck3);
        deckArrayList.add(deck4);
        deckArrayList.add(deck5);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,deckArrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displaySettings(deckArrayList.get(position));
            }
        });


    }

    public void displaySettings(final Deck deck) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        dialog.setTitle("Subject: " + deck.getDeckSubject());
        TextView subjectText = new TextView(this);
        subjectText.setText("Change Note Card Subject: ");
        linearLayout.addView(subjectText);
        Spinner spinner = new Spinner(this);
        String [] subjects = {"Multiplication", "Spanish", "Java", "Programming", "Music"};
        ArrayList<String> subjectList = new ArrayList<String>();
        for (int i = 0; i < subjects.length; i++) {
            subjectList.add(subjects[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        linearLayout.addView(spinner);

        TextView fontText = new TextView(this);
        fontText.setText("Change Font Size: ");
        linearLayout.addView(fontText);

        //Setting up Font Sizes
        final Spinner textSpinner = new Spinner(this);
        ArrayList<Float> fontSize = new ArrayList<Float>();
        float [] fonts = new float [10];
        float defaultFont = 16;
        for(int i = 0; i < 10; i++)
        {
            fontSize.add(defaultFont);
            fonts[i] = defaultFont;
            defaultFont+=4;
        }
        ArrayAdapter<Float> adapterFont = new ArrayAdapter<Float> (this, android.R.layout.simple_spinner_item, fontSize);
        adapterFont.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textSpinner.setAdapter(adapterFont);
        linearLayout.addView(textSpinner);
        dialog.setView(linearLayout);
        deck.setDeckSubject(spinner.getSelectedItem().toString());
        String tmpFontSize = textSpinner.getSelectedItem().toString();
        deck.setFontSize((int)Float.parseFloat(tmpFontSize));
        dialog.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NoteCardDB db = new NoteCardDB(getBaseContext());
                //db.updateSettingsRecord(deck.getRowId(), deck.getDeckSubject(), deck.getFontSize());
                db.insertSettings(deck.getDeckSubject(), deck.getFontSize());
            }
        });
        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Returns to activity
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



