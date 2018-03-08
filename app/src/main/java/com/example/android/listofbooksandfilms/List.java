package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class List {
    private ArrayList<Element> content;
    private int colorPrimary;
    private int colorAdditional;
    private int colorMainText;
    private int theme;
    private Context context;
    private int title;

    List(Context current, int primaryColor, int additionalColor, int mainTextColor, int themeToUse, int label){
        colorPrimary = primaryColor;
        colorAdditional = additionalColor;
        colorMainText = mainTextColor;
        theme = themeToUse;
        this.context = current;
        content = new ArrayList<>();
        title = label;
    }

    void add(Element newElement){
        if (newElement != null) {
            content.add(newElement);
        }
        else{
            Log.e("List", "try to add null element");
        }
    }

    ArrayList<Element> getContent(){
        return content;
    }

    int getPrimaryColor(){
        return ContextCompat.getColor(context, colorPrimary);
    }

    int getAdditionalColor() {
        return ContextCompat.getColor(context, colorAdditional);
    }

    int getTextColor(){
        return ContextCompat.getColor(context, colorMainText);
    }

    Element get(int index){
        if (index < content.size()){
            return content.get(index);
        }
        else{
            Log.e("List", "try to get null element");
            return null;
        }
    }

    int size(){
        return content.size();
    }

    int getTheme(){
        return theme;
    }

    int getTitle(){
        return title;
    }

    void clear(){
        content = new ArrayList<>();
    }
}
