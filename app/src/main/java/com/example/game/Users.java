package com.example.game;

public class Users {
    public int id;
    private String fullName;
    private String date;
    private int score;




    public Users(String fullName, String date, int score) {
        this.fullName = fullName;
        this.date = date;
        this.score = score;
    }




    public int  getScore() {
        return score;
    }

    public void setScore(int  score) {
        this.score = score;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
