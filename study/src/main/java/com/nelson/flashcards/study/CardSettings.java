/*
 * @Author Mike Nelson
 * 
 * CardSettings.java allows for file names and font sizes to be saved in SharedPreferences
 */

package com.nelson.flashcards.study;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class CardSettings extends Activity {
    private NoteCardDB db = new NoteCardDB(this);
    private ArrayList<String> fontSize = new ArrayList<String>();
    private float [] fonts = new float [11];
    private float defaultFont = 16;
    private Spinner fontSpinner;
    private Switch randomQuestion;
    private boolean checked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_settings);
        fontSpinner = (Spinner)findViewById(R.id.font_spinner);
        randomQuestion = (Switch)findViewById(R.id.switch1);
        initializeSettings();
        initializeFontSizes();

        //Font Spinner Initialization
        ArrayAdapter<String> fontAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_dropdown, fontSize);
        fontSpinner.setAdapter(fontAdapter);
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0) {
                    //does nothing, spinner is initialized
                } else {
                    updateFontSize((int)fonts[position], getRandomQuestionValue());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Switch handling
        if(getRandomQuestionValue() == 1)
            checked = true;
        else
            checked = false;

        randomQuestion.setChecked(checked);
        randomQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(getApplicationContext(), "Random Questions will be displayed", Toast.LENGTH_SHORT).show();
                    updateRandomQuestionOption(1);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Questions will be displayed in order", Toast.LENGTH_SHORT).show();
                    updateRandomQuestionOption(0);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initializeSettings() {
        db.open();
        Cursor cursor = db.getSettingsRecord(1);
           if(!cursor.moveToFirst()) {
             db.insertSettings((int)defaultFont, 0);
           }

        db.close();
    }

    public void initializeFontSizes () {
        fontSize.add("Select a Font Size");
        for(int i = 1; i < 11; i++)
        {
            fontSize.add(""+defaultFont);
            fonts[i] = defaultFont;
            defaultFont+=4;
        }
    }

    public void updateFontSize(final int fontSize, final int randomQuestion) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.open();
                db.updateSettingsRecord(1, fontSize, randomQuestion);
                db.close();
            }
        };
        runnable.run();
    }

    public void updateRandomQuestionOption(final int randomQuestion) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                db.open();
                db.updateRandomQuestion(randomQuestion);
                db.close();
            }
        };
        runnable.run();
    }

    public int getRandomQuestionValue() {
        int value = 0;
        db.open();
        Cursor cursor = db.getSettingsRecord(1);
        if(cursor.moveToFirst()){
            value = cursor.getInt(2);
        }
        return value;
    }


	@Override
	public void onBackPressed() {		
		super.onBackPressed();
		finish();
	}	

}



