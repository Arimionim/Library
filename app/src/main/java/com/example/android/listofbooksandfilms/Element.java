package com.example.android.listofbooksandfilms;

import android.util.Log;

public class Element {
    private String mainText, additionalText, descriptionText;
    private int rate;
    private boolean isGood;
    private final static int MAX_RATE = 5;
    private int id;

    Element(String inputMainText, String inputAdditionalText,
            String inputDescriptionText, int inputRate,
            boolean inputIsGood, int inputId){
        additionalText = inputAdditionalText;
        mainText = inputMainText;
        rate = inputRate;
        isGood = inputIsGood;
        descriptionText = inputDescriptionText;
        id = inputId;
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

    public int getId(){
        return id;
    }

    public String getMainText(){
        return mainText;
    }

    public String getAdditionalText(){
        return additionalText;
    }

    public String getDescriptionText(){
        return descriptionText;
    }

    public boolean getIsGood(){
        return isGood;
    }

}
