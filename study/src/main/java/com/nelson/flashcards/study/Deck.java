package com.nelson.flashcards.study;

/**
 * Created by Mike on 6/18/14.
 */
public class Deck {
    private int rowId, fontSize;
    private String deckSubject;

    public Deck(int rowId, String deckSubject, int fontSize) {
        this.rowId = rowId;
        this.deckSubject = deckSubject;
        this.fontSize = fontSize;
    }

    public String getDeckSubject() {
        return deckSubject;
    }

    public void setDeckSubject(String subject) { deckSubject = subject; }

    public int getRowId() { return rowId; }

    public int getFontSize() { return fontSize; }

    public void setFontSize(int font) { fontSize = font; }

    @Override
    public String toString() {
        return "Deck: " + rowId + "\nSubject: " + deckSubject + "\nFont Size: " + fontSize;
    }
}
