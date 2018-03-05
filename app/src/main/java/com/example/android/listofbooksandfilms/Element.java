package com.example.android.listofbooksandfilms;

import android.util.Log;

public class Element {
    private String mainText, additionalText;
    private int rate;
    private boolean isGood;
    private final static int MAX_RATE = 5;

    Element(String inputMainText, String inputAdditionalText, int inputRate, boolean inputIsGood){
        additionalText = inputAdditionalText;
        mainText = inputMainText;
        rate = inputRate;
        isGood = inputIsGood;
    }

    public int getRate(){
        if (0 <= rate && rate <= MAX_RATE){
            return rate;
        }
        else{
            Log.v("Element", "rate is't right");
            return 0;
        }
    }

    public String getMainText(){
        return mainText;
    }

    public String getAdditionalText(){
        return additionalText;
    }

    public boolean getIsGood(){
        return isGood;
    }

}
