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
import android.widget.Spinner;


public class CardSettings extends Activity {
	
	public static final String PREFS = "noteCardPrefs";
	public static final String FONT = "fontPrefs";
	
	protected File folder = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/Note Cards/");
	private List<String> fileList = new ArrayList<String>();
	protected Spinner spinText1, spinText2, spinText3, spinText4, spinText5;
	protected Spinner spinFont1, spinFont2, spinFont3, spinFont4, spinFont5;
	private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/Note Cards/";
	private int pathLength = filePath.length();
	private ArrayList<Float> fontSize = new ArrayList<Float>();
	private float [] fonts = new float [10];
	private int cardLength = 1;
	protected String [] fileName = new String [cardLength];

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.card_settings);		
		
		File [] cards = folder.listFiles();
		
		if(cards.length > 0) {
			cardLength = cards.length;
		}else
			cardLength = 1;
			
		
		fileName = new String [cardLength];		
		
		//Shortening File path for ease of use
		for(int i = 0; i < cards.length; i++) {
			String temp = cards[i].toString();
			fileName[i] = temp.substring(pathLength);
		}
		
		for(int i = 0; i < fileName.length; i++) {
			fileList.add(fileName[i]);
		}
		
		//Setting up Font Sizes
		float defaultFont = 16;
		for(int i = 0; i < 10; i++)
		{
			fontSize.add(defaultFont);
			fonts[i] = defaultFont;
			defaultFont+=4;
		}
		
		//Set up Spinners by Id
		spinText1 = (Spinner) findViewById(R.id.spinner1);
		spinText2 = (Spinner) findViewById(R.id.spinDeck2);
		spinText3 = (Spinner) findViewById(R.id.spinDeck3);
		spinText4 = (Spinner) findViewById(R.id.spinDeck4);
		spinText5 = (Spinner) findViewById(R.id.spinDeck5);
		spinFont1 = (Spinner) findViewById(R.id.spinner2);
		spinFont2 = (Spinner) findViewById(R.id.spinFont2);
		spinFont3 = (Spinner) findViewById(R.id.spinFont3);
		spinFont4 = (Spinner) findViewById(R.id.spinFont4);
		spinFont5 = (Spinner) findViewById(R.id.spinFont5);
		
		//Create File adapter for Spinner file
		ArrayAdapter<String> adapterFile = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fileList);
		adapterFile.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		//Create Font adapter for Spinner
		ArrayAdapter<Float> adapterFont = new ArrayAdapter<Float> (this, android.R.layout.simple_spinner_item, fontSize);
		
		//Set adapter for Spinners
		spinText1.setAdapter(adapterFile);
		spinText2.setAdapter(adapterFile);
		spinText3.setAdapter(adapterFile);
		spinText4.setAdapter(adapterFile);
		spinText5.setAdapter(adapterFile);
		spinFont1.setAdapter(adapterFont);
		spinFont2.setAdapter(adapterFont);
		spinFont3.setAdapter(adapterFont);
		spinFont4.setAdapter(adapterFont);
		spinFont5.setAdapter(adapterFont);				
		
		//Set on select listeners
		spinText1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences noteCardPrefs = getSharedPreferences(PREFS, 0);
				SharedPreferences.Editor prefsEdit = noteCardPrefs.edit();
				prefsEdit.putString("deck_1_file", spinText1.getSelectedItem().toString());
				prefsEdit.commit();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinFont1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences fontPrefs = getSharedPreferences(FONT, 0);
				SharedPreferences.Editor prefsEdit = fontPrefs.edit();
				float font =  fonts[position];
				prefsEdit.putFloat("font_1", font);
				prefsEdit.commit();				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinText2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {				
				SharedPreferences noteCardPrefs = getSharedPreferences(PREFS, 0);
				SharedPreferences.Editor prefsEdit = noteCardPrefs.edit();
				prefsEdit.putString("deck_2_file",spinText2.getSelectedItem().toString());
				prefsEdit.commit();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinFont2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences fontPrefs = getSharedPreferences(FONT, 0);
				SharedPreferences.Editor prefsEdit = fontPrefs.edit();
				float font =  fonts[position];
				prefsEdit.putFloat("font_2", font);
				prefsEdit.commit();	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinText3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {				
				SharedPreferences noteCardPrefs = getSharedPreferences(PREFS, 0);
				SharedPreferences.Editor prefsEdit = noteCardPrefs.edit();
				prefsEdit.putString("deck_3_file",spinText3.getSelectedItem().toString());
				prefsEdit.commit();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinFont3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences fontPrefs = getSharedPreferences(FONT, 0);
				SharedPreferences.Editor prefsEdit = fontPrefs.edit();
				float font =  fonts[position];
				prefsEdit.putFloat("font_3", font);
				prefsEdit.commit();	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinText4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {				
				SharedPreferences noteCardPrefs = getSharedPreferences(PREFS, 0);
				SharedPreferences.Editor prefsEdit = noteCardPrefs.edit();
				prefsEdit.putString("deck_4_file",spinText4.getSelectedItem().toString());
				prefsEdit.commit();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinFont4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences fontPrefs = getSharedPreferences(FONT, 0);
				SharedPreferences.Editor prefsEdit = fontPrefs.edit();
				float font =  fonts[position];
				prefsEdit.putFloat("font_4", font);
				prefsEdit.commit();	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinText5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {				
				SharedPreferences noteCardPrefs = getSharedPreferences(PREFS, 0);
				SharedPreferences.Editor prefsEdit = noteCardPrefs.edit();
				prefsEdit.putString("deck_5_file",spinText5.getSelectedItem().toString());
				prefsEdit.commit();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
		
		spinFont5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				SharedPreferences fontPrefs = getSharedPreferences(FONT, 0);
				SharedPreferences.Editor prefsEdit = fontPrefs.edit();
				float font =  fonts[position];
				prefsEdit.putFloat("font_5", font);
				prefsEdit.commit();	
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// Do Nothing				
			}
			
		});
	}

	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}	
	

}



