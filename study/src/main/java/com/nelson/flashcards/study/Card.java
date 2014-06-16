package com.nelson.flashcards.study;

/**
 * Created by Mike on 6/14/14.
 */
public class Card {
    protected int rowId, known;
    protected String title, question, answer;

    public Card (int r, String t, String q, String a, int k) {
        rowId = r;
        title = t;
        question = q;
        answer = a;
        known = k;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setTitle (String title) { this.title = title;}

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setKnown(int known) {
        this.known = known;
    }

    public String getTitle() { return title; }

    public String getQuestion(){
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getRowId() {
        return rowId;
    }

    public int getKnown() {
        return known;
    }

    @Override
    public String toString() {
        return "RowID: " + rowId + "Title: " + title + "\nQuestion: " + question
                + "\nAnswer: " + answer + "\nKnown: " + known;
    }
}
