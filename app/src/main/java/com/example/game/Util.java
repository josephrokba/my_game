package com.example.game;

import android.util.Log;

public class Util {
    public static Question generteQuestion() {
        String[][] temp = new String[3][3];
        int startNumber = (int) (Math.random() * 10) + 1;
        int incNumber = (int) (Math.random() * 5) + 1;
        int nextNumber = 0;
        int iIndex = (int) Math.floor(Math.random() * (3));
        int jIndex = (int) Math.floor(Math.random() * (3));
        Log.e("iIndex = " + iIndex, ",jIndex = " + jIndex);
        int number = -1;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                nextNumber = startNumber + incNumber;
                if (i == iIndex && j == jIndex) {
                    temp[i][j] = "??";
                    number = nextNumber;
                } else {
                    temp[i][j] = nextNumber + "";
                }
                incNumber += 2;
                startNumber = nextNumber;
            }
        }
        return new Question(temp, number);
    }
}
