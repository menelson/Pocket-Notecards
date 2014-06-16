/*
 * @Author Mike Nelson
 * 
 * CreateQuestion.java writes questions & answers to the correct file
 */

package com.nelson.flashcards.study;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImportFile extends Activity {
	
	private ArrayList<String> question = new ArrayList<String>();
	private ArrayList<String> answer = new ArrayList<String>();	
	private String quest;
	private String answ;
	protected String fileName;
	protected String folder = "Note Cards";
	private String type1 = "Question";
	private String type2 = "Answer";
	private String empty = "";	
	protected Intent intent2 = getIntent();	//does nothing?



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_question);
        
        //final EditText editFile = (EditText) findViewById(R.id.editText1);
        final EditText editQuestion = (EditText) findViewById(R.id.editText2);        
        final EditText editAnswer = (EditText) findViewById(R.id.editText3);
                
       
        Button add = (Button) findViewById(R.id.button1);
        add.setOnClickListener(new View.OnClickListener(){
        		public void onClick(View view){
        //			fileName = editFile.getText().toString();
        			quest = editQuestion.getText().toString();
        			answ = editAnswer.getText().toString();        			
        			
        			//Adding to respective ArrayLists
        			question.add(quest);
        			answer.add(answ);        			
        			
        			((EditText) findViewById(R.id.editText2)).setText("");
            		((EditText) findViewById(R.id.editText3)).setText("");
            		
        		}
        	});
        
        //Set onClick to write ArrayList and return to main menu
        Button done = (Button) findViewById(R.id.button2);
        done.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        		{
        		   // sd card mounted
        		}  
        		
        		File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
    					+ "/" + folder + "/" + fileName + "/");
        		File questFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
        				+ "/" + folder + "/" + fileName + "/" + fileName+type1 + ".txt");
        		File answFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
        				+ "/" + folder + "/" + fileName + "/" + fileName+type2 + ".txt");        		

        		if(!direct.exists())
        		{
        		    if(direct.mkdir())  { 
        		    	/*directory is created;*/  
        		    	}
        		}
        		
        		if(editQuestion.getText().toString() != empty)
        		{
        			quest = editQuestion.getText().toString();
        			answ = editAnswer.getText().toString();
        			
        			//Adding to respective ArrayLists
        			question.add(quest);
        			answer.add(answ);        			
        		}
        		
        		try {
        				 //Writing to Question File
    					 FileWriter fwriter = new FileWriter(questFile.getAbsoluteFile(), false);
    			         BufferedWriter bwriter = new BufferedWriter(fwriter);
    					 
    			         for(int i = 0; i < question.size(); i++)
    			         {
    			        	 String sData = question.get(i);
    			             bwriter.write(sData);
    			             bwriter.newLine();    			        	 
    			         }
    			        bwriter.close();
    			        
    			        //Writing to Answer File
    			        fwriter = new FileWriter(answFile.getAbsoluteFile(), false);
   			         	bwriter = new BufferedWriter(fwriter);
   			         	
   			         	for(int i = 0; i < answer.size(); i++)
   			         	{
   			         		String sData = answer.get(i);
   			         		bwriter.write(sData);
   			         		bwriter.newLine();   			        	 
   			         	}
   			         	bwriter.close();   			         	
   			         	
        		
        			}catch (FileNotFoundException e) {
        				e.printStackTrace();
        				}catch (IOException e) {
        					e.printStackTrace();
        				}        		
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
