package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {


    private List list;
    private String listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            listTitle = getIntent().getExtras().getString("listTitle");
        } catch (NullPointerException e){
            Log.e("ListActivity OnCreate", "can not get listTitle");
        }

        setContent();
        setTheme(list.getTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        for (int i = 0; i < list.size(); i++){
            addRow(list.get(i));
        }
    }

    private void setContent() {
        if (isRequested(getString(R.string.books_list_title))){
            list = new List(this, R.color.booksPrimary, R.color.booksDark, R.color.booksMainText, R.style.BooksTheme);
            list.add(new Element("First", "additional", 5, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 5, false));
            list.add(new Element("Second", "additional", 3, true));
            list.add(new Element("First", "additional", 1, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 0, false));
            list.add(new Element("Second", "additional", 3, true));
            list.add(new Element("First", "additional", 2, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 4, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 5, false));
            list.add(new Element("Second", "additional", 3, true));
            list.add(new Element("First", "additional", 1, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 0, false));
            list.add(new Element("Second", "additional", 3, true));
            list.add(new Element("First", "additional", 2, false));
            list.add(new Element("Second", "additional", 3, false));
            list.add(new Element("First", "additional", 4, false));
            list.add(new Element("Second", "additional", 3, false));
        }
        else if (isRequested(getString(R.string.films_list_title))){
            list = new List(this, R.color.filmsPrimary, R.color.filmsDark, R.color.filmsMainText, R.style.FilmsTheme);
            list.add(new Element("Third", "additional", 5, false));
            list.add(new Element("Fourth", "additional", 3, false));
        }
        else{
            Log.e("List activity", "requested unknown list");
            list = new List(this, android.R.color.black, android.R.color.black, android.R.color.black, R.style.AppTheme_NoActionBar);
        }
    }

    public void addRow(Element content) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tableRow = (TableRow) inflater.inflate(R.layout.activity_element_view, null);

        if (content.getIsGood()){
            ImageView isGood = (ImageView) tableRow.getChildAt(0);
            isGood.setVisibility(View.VISIBLE);
        }

        TextView mainText = (TextView) ((RelativeLayout)tableRow.getChildAt(1)).getChildAt(0);
        mainText.setText(content.getMainText());

        TextView additionalText = (TextView)  ((RelativeLayout)tableRow.getChildAt(1)).getChildAt(1);
        additionalText.setText(content.getAdditionalText());
        if (isRequested(getString(R.string.books_list_title))) {
            additionalText.setTextColor(list.getAdditionalColor());
        }
        else if (isRequested(getString(R.string.films_list_title))) {
            additionalText.setTextColor(list.getAdditionalColor());
        }

        ImageView isGood = (ImageView) tableRow.getChildAt(2);
        isGood.setImageResource(R.drawable.ic_panorama_fish_eye_black_18dp_0 + content.getRate());

        tableLayout.addView(tableRow);
    }

    boolean isRequested(String request){
        return listTitle.equals(request);
    }
}
