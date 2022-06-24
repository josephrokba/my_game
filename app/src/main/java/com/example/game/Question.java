package com.example.game;

public class Question {
    private String[][] data;
    private int hiddenNumber;

    public Question(String[][] question, int hiddenNumber) {
        this.data = question;
        this.hiddenNumber = hiddenNumber;
    }

    public String[][] getData() {
        return data;
    }

    public void setQuestion(String[][] question) {
        this.data = question;
    }

    public int getHiddenNumber() {
        return hiddenNumber;
    }

    public void setHiddenNumber(int hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }
}

