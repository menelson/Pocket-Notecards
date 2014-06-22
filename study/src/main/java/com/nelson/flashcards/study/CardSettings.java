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
    NoteCardDB db = new NoteCardDB(this);
    Deck deck1 = new Deck(1, "Multiplication", 22);
    Deck deck2 = new Deck(2, "null", 24);
    Deck deck3 = new Deck(3, "Java", 26);
    Deck deck4 = new Deck(4, "Programming", 27);
    Deck deck5 = new Deck(5, "Music", 28);

    ArrayList<Deck> deckArrayList = new ArrayList<Deck>();




	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_settings);
        ListView listView = (ListView)findViewById(R.id.settings_list);

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
                Deck deck = displaySettings(deckArrayList.get(position), adapter, view);
                //deckArrayList.set(position, deck);
                //adapter.notifyDataSetChanged();
                //view.getRootView();
                //long id2 = db.insertSettings("Test", 2);

            }
        });
        Toast.makeText(this, deck1.getDeckSubject(),Toast.LENGTH_LONG).show();
        Toast.makeText(this, deck1.getFontSize(),Toast.LENGTH_LONG).show();



    }

    public void updateSettings(Deck deck) {
        NoteCardDB db = new NoteCardDB(this);
        db.insertSettings(deck.getDeckSubject(), deck.getFontSize());
    }

    public Deck displaySettings(Deck deck, ArrayAdapter adapter, View view) {
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
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, subjectList);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(subjectAdapter);
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
                //adapter.notifyDataSetChanged();
                //view.setAlpha(1);
                //Toast.makeText(this, "Deck: " + deck, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();


        return deck;

    }


	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}	
	

}



