package com.nelson.flashcards.study;

import android.database.Cursor;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class NoteCard extends ActionBarActivity {

    private static final int SWIPE_THRESHOLD = 20;
    private static final int SWIPE_VELOCITY_THRESHOLD = 20;

    private int size, index, previous;

    protected ArrayList<String> questions = new ArrayList<String>();
    protected ArrayList<String> answers = new ArrayList<String>();
    protected ArrayList<Integer> known = new ArrayList<Integer>();

    NoteCardDB db = new NoteCardDB(this);
    Random randomNum = new Random();
    GestureDetectorCompat gestDetector;
    TextView mainDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_card);
        mainDisplay = (TextView)findViewById(R.id.question_text);
        MotionEvent one;
        gestDetector = new GestureDetectorCompat(this, new MyGestureListener());
        loadData();
        size = questions.size();
        randomNum = new Random(size);
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
                break;
            case R.id.question_edit:
                break;
            case R.id.question_delete:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData(){
        db.open();
        Cursor c = db.getAllRecords();
        if (c.moveToFirst()){
            while(c.moveToNext()){
                questions.add(c.getString(1));
                answers.add(c.getString(2));
                known.add(c.getInt(3));
            }
        }
        db.close();
    }

    @Override
    public boolean onTouchEvent(MotionEvent one) {
        //Setting up for Gesture based events
        return gestDetector.onTouchEvent(one);
    }

    //Reveals answer to the Note Card
    public void onSwipeTop() {
       mainDisplay.setText(answers.get(index));
    }

    //Goes back to the question on the Note Card
    public void onSwipeBottom() {
        mainDisplay.setText(questions.get(index));
    }

    //Randomly goes to the next Note Card based on a Num Generator
    public void onSwipeLeft() {
        previous = index;
        index = randomNum.nextInt(size);
       mainDisplay.setText(questions.get(index));
    }

    public void onSwipeRight() {
        //Goes back to only one previous card
        index = previous;
        mainDisplay.setText(questions.get(index));
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
