package com.nelson.flashcards.study;

/**
 * Created by Mike on 6/14/14.
 */
public class Card {
    private int rowId, known;
    private String question, answer;

    public Card (int r, String q, String a, int k) {
        rowId = r;
        question = q;
        answer = a;
        known = k;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setKnown(int known) {
        this.known = known;
    }

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
        return "RowID: " + rowId + "\nQuestion: " + question
                + "\nAnswer: " + answer + "\nKnown: " + known;
    }
}
