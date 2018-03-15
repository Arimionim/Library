package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class List {
    private ArrayList<Element> content;
    private int colorPrimary;
    private int colorDark;
    private int colorMainText;
    private int colorRate;
    private int colorCardBackground;
    private int theme;
    private Context context;
    private int title;

    List(Context current, int primaryColor, int additionalColor, int mainTextColor, int rateColor, int cardBackgroundColor, int themeToUse, int label){
        colorPrimary = primaryColor;
        colorDark = additionalColor;
        colorMainText = mainTextColor;
        colorRate = rateColor;
        colorCardBackground = cardBackgroundColor;
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

    ArrayList<Element> getList(){
        return content;
    }

    public Element get(int index){
        if (index < content.size()){
            return content.get(index);
        }
        else{
            Log.e("List", "try to get null element");
            return null;
        }
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

    int getColorPrimary(){
        return colorPrimary;
    }

    int getColorDark(){
        return colorDark;
    }

    int getColorRate(){
        return colorRate;
    }

    int getColorCardBackground(){
        return colorCardBackground;
    }

    int getColorMainText(){
        return colorMainText;
    }

    int getSize(){
        return content.size();
    }

    void setContext(Context newContext){
        context = newContext;
    }

    Context getContext(){
        return context;
    }
}
