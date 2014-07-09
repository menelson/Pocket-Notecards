package com.nelson.flashcards.study;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mike on 7/7/14.
 */
public class ReadFile {

    protected String fileDelimeter = "\\|";

    public ArrayList<Card> importFile(File file, String title) {

        String line = "";
        BufferedReader bufferedReader = null;
        ArrayList<Card> noteCards = new ArrayList<Card>();


        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            while(bufferedReader.ready()) {
                line = bufferedReader.readLine();
                String [] data = line.split(fileDelimeter);
                Card tmpCard = new Card(0, title, data[0], data[1], 0); //Dummy values for rowID and known
                noteCards.add(tmpCard);

            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } //end catch
            } //end if
        } //end finally

        return noteCards;
    } //end importFile
}
