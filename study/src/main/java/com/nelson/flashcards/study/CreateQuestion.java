/*
 * @Author Mike Nelson
 * 
 * CreateQuestion.java writes questions & answers to the correct file
 */

package com.nelson.flashcards.study;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateQuestion extends Activity {

	private String question;
	private String answer;
	protected Intent intent2 = getIntent();	//does nothing?

    NoteCardDB db = new NoteCardDB(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question);

        final EditText editQuestion = (EditText) findViewById(R.id.editText2);        
        final EditText editAnswer = (EditText) findViewById(R.id.editText3);

        Button add = (Button) findViewById(R.id.button1);
        add.setOnClickListener(new View.OnClickListener(){
        		public void onClick(View view){
                    question = editQuestion.getText().toString();
        			answer = editAnswer.getText().toString();

                    //Adding to database
                    db.open();
                    long id = db.insertRecord(question, answer, 0);
                    db.close();
        			
        			((EditText) findViewById(R.id.editText2)).setText("");
            		((EditText) findViewById(R.id.editText3)).setText("");
                    Toast.makeText(CreateQuestion.this, "Question and Answer Added", Toast.LENGTH_LONG).show();

                }
        	});
        
        //Set onClick to write ArrayList and return to main menu
        Button done = (Button) findViewById(R.id.button2);
        done.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		finish();
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.create_question, menu);
        return true;
    }

}
