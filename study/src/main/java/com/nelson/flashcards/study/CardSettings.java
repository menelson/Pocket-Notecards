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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class CardSettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_settings);
        ListView listView = (ListView)findViewById(R.id.settings_list);

        Deck deck1 = new Deck(1, "Multiplication", 22);
        Deck deck2 = new Deck(2, "Spanish", 24);
        Deck deck3 = new Deck(3, "Java", 26);
        Deck deck4 = new Deck(4, "Programming", 27);
        Deck deck5 = new Deck(5, "Music", 28);

        final ArrayList<Deck> deckArrayList = new ArrayList<Deck>();

        deckArrayList.add(deck1);
        deckArrayList.add(deck2);
        deckArrayList.add(deck3);
        deckArrayList.add(deck4);
        deckArrayList.add(deck5);

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,deckArrayList);
        listView.setAdapter(adapter);

    }


	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}	
	

}



