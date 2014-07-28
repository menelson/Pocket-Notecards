package com.nelson.flashcards.study;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class NoteCard extends ActionBarActivity {

    private static final int SWIPE_THRESHOLD = 20;
    private static final int SWIPE_VELOCITY_THRESHOLD = 20;

    private int size, index, previous;
    protected ArrayList<Card> noteCards = new ArrayList<Card>();

    private NoteCardDB db = new NoteCardDB(this);
    private Random randomNum = new Random();
    private GestureDetectorCompat gestDetector;
    private TextView mainDisplay;
    private String title;
    private int fontSize;
    private boolean randomOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_card);
        mainDisplay = (TextView)findViewById(R.id.question_text);
        gestDetector = new GestureDetectorCompat(this, new MyGestureListener());
        title = getIntent().getExtras().getString("Subject");
        fontSize = getFontSize();//35;
        mainDisplay.setTextSize(fontSize);
        loadData(title);
        index = 0;
        size = noteCards.size();
        randomNum = new Random(size);
        randomOption = randomOption();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_card, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.question_add:
                displayPopUp("Subject: " + noteCards.get(index).getTitle(),
                        "Enter the Question","Enter the Answer", 1 );
                break;
            case R.id.question_edit:
                displayPopUp("Subject: " + noteCards.get(index).getTitle(),
                        noteCards.get(index).getQuestion()
                        ,noteCards.get(index).getAnswer(), 2);
                break;
            case R.id.question_delete:
                displayPopUp("Delete this Question and Answer",noteCards.get(index).getQuestion()
                        ,noteCards.get(index).getAnswer(), 3);
                break;
            case R.id.settings_list:
                launchSettings(item);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Go to Settings
    public void launchSettings(MenuItem item) {
        Intent intentSettings = new Intent(this, CardSettings.class);
        startActivity(intentSettings);
    }

    public void loadData(String title){
        Card card;
        db.open();
        Cursor c = db.getAllRecords(title);
        if (c.moveToFirst()){
            card = new Card(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
            noteCards.add(card);
            while(c.moveToNext()){
                card = new Card(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
                noteCards.add(card);
            }
        }
        db.close();
    }

    public void displayPopUp(String title, String qPrompt, String aPrompt, final int addDelUp){
        AlertDialog.Builder popUp = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.DialogTheme));
        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        popUp.setTitle(title);

        //TextView and EditText for question add/edit
        TextView qText = new TextView(this);
        qText.setText(qPrompt);
        qText.setTextColor(getResources().getColor(R.color.black));
        linLayout.addView(qText);
        final EditText questEdit = new EditText(this);
        questEdit.setHint(qPrompt);

        linLayout.addView(questEdit);

        //TextView and EditText for answer add/edit
        TextView aText = new TextView(this);
        aText.setText(aPrompt);
        aText.setTextColor(getResources().getColor(R.color.black));
        linLayout.addView(aText);
        final EditText answerEdit = new EditText(this);
        answerEdit.setHint(aPrompt);
        linLayout.addView(answerEdit);
        popUp.setView(linLayout);

        popUp.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (addDelUp) {
                    case 1:
                        addQuestion(noteCards.get(index).getTitle(),
                                questEdit.getText().toString(),
                                answerEdit.getText().toString(), 0);
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Card tmpCard = new Card(noteCards.get(index).getRowId(),
                                noteCards.get(index).getTitle(),
                                noteCards.get(index).getQuestion(),
                                noteCards.get(index).getAnswer(),
                                noteCards.get(index).getKnown());
                        tmpCard.setQuestion(questEdit.getText().toString());
                        tmpCard.setAnswer(answerEdit.getText().toString());
                        tmpCard.setKnown(0);
                        editQuestion(tmpCard.getRowId(), tmpCard.getTitle(), tmpCard.getQuestion(),
                                tmpCard.getAnswer(), tmpCard.getKnown());
                        Toast.makeText(getApplicationContext(), "Updated" + tmpCard, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        deleteQuestion(noteCards.get(index).getRowId());
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        popUp.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String result = "Cancelled, Nothing Changed";
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

            }
        });
        popUp.show();
    }

    public void addQuestion (String t, String q, String a, int k) {
        db.open();
        long i = db.insertRecord(t, q, a, k);
        db.close();
        Card newCard = new Card((int)i, t, q, a, k);
        noteCards.add(newCard);
        size++;
    }

    public void editQuestion (int idx, String t, String q, String a, int k) {
        Card newCard = new Card(idx, t, q, a, k);
        db.open();
        db.updateRecord(idx, t, q, a ,k);
        db.close();
        noteCards.set(index, newCard);
    }

    public void deleteQuestion (long idx) {
        db.open();
        db.deleteRecord(idx);
        db.close();
        noteCards.remove(index);
        size--;
    }

    public int getFontSize(){
        int fontSize = 0;
        db.open();
        Cursor cursor = db.getSettingsRecord(1);
        if(cursor.moveToFirst()){
            fontSize = cursor.getInt(1);
        }
        db.close();
        Log.d("Font Size: ", ""+fontSize);

        return fontSize;
    }

    public boolean randomOption() {
        boolean tmpBool;// = false;
        int option = 0;

        db.open();
        Cursor cursor = db.getSettingsRecord(1);
        if(cursor.moveToFirst()){
            option = cursor.getInt(2);
        }
        db.close();

        if(option == 1){
            tmpBool = true;
            Log.d("tmpBool", ""+tmpBool);
        } else {
            tmpBool = false;
            Log.d("tmpBool", ""+tmpBool);
        }

        return tmpBool;
    }

    @Override
    public boolean onTouchEvent(MotionEvent one) {
        //Setting up for Gesture based events
        return gestDetector.onTouchEvent(one);
    }

    //Reveals answer to the Note Card
    public void onSwipeTop() {
       mainDisplay.setText(noteCards.get(index).getAnswer());
    }

    //Goes back to the question on the Note Card
    public void onSwipeBottom() {
        mainDisplay.setText(noteCards.get(index).getQuestion());
    }

    //Randomly goes to the next Note Card based on a Num Generator
    public void onSwipeLeft() {
        if(randomOption) {
            previous = index;
            index = randomNum.nextInt(size);
            Log.d("Swipe Left", "Random On");
        } else {
            index++;
            Log.d("Swipe Left", "Random Off");
        }
        mainDisplay.setText(noteCards.get(index).getQuestion());
    }

    public void onSwipeRight() {
        //Goes back to only one previous card
        if(randomOption == true){
            index = previous;
        }else {
            index--;
        }
        mainDisplay.setText(noteCards.get(index).getQuestion());
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent start, MotionEvent finish,
                               float xVelocity, float yVelocity) {

            boolean result = false;
            try {
                float diffY = finish.getY() - start.getY();
                float diffX = finish.getX() - start.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(xVelocity) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(yVelocity) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }
}
