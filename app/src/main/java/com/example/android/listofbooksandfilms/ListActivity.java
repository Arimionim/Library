package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import static com.example.android.listofbooksandfilms.NavigationHelper.openNewActivity;

public class ListActivity extends AppCompatActivity {

    MyDatabaseHelper databaseHelper;
    private List list;
    private String listTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new MyDatabaseHelper(ListActivity.this, "Database", 1);

        try {
            listTitle = getIntent().getExtras().getString("listTitle");
        } catch (NullPointerException e){
            Log.e("ListActivity OnCreate", "can not get listTitle");
        }

        if (isRequested(getString(R.string.books_list_title))){
            list = new List(this, R.color.booksPrimary, R.color.booksDark, R.color.booksMainText, R.style.BooksTheme, R.string.books_list_title);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            list = new List(this, R.color.filmsPrimary, R.color.filmsDark, R.color.filmsMainText, R.style.FilmsTheme, R.string.films_list_title);
        }
        else{
            list = new List(this, android.R.color.black, android.R.color.black, android.R.color.black, R.style.AppTheme_NoActionBar, R.string.app_name);
            return;
        }
        setTheme(list.getTheme());

        setContentView(R.layout.activity_list);
        setContent();
        setTitle(list.getTitle());

        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(ListActivity.this, AddNewElement.class, listTitle);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContent();
    }

    private void setContent() {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor;
        if (isRequested(getString(R.string.books_list_title))){
            cursor = database.query("Books", null, null, null, null, null, null);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            cursor = database.query("Films", null, null, null, null, null, null);
        }
        else{
            Log.e("List activity", "requested unknown list");
            return;
        }

        ((TableLayout)findViewById(R.id.table)).removeAllViewsInLayout();
        list.clear();
        if (cursor.moveToFirst()) {
            int mainColIndex = cursor.getColumnIndex("main");
            int additionalColIndex = cursor.getColumnIndex("additional");
            int descriptionColIndex = cursor.getColumnIndex("description");
            int rateColIndex = cursor.getColumnIndex("rate");
            int goodColIndex = cursor.getColumnIndex("good");
            do {
                list.add(new Element(cursor.getString(mainColIndex), cursor.getString(additionalColIndex), cursor.getString(descriptionColIndex), cursor.getInt(rateColIndex), cursor.getInt(goodColIndex) == 1));
            } while (cursor.moveToNext());
        }
        else{
            Log.e("if", "smth go wrong " + cursor.getColumnCount());
        }

        cursor.close();

        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        for (int i = 0; i < list.size(); i++){
            addRow(tableLayout, list.get(i));
        }
    }

    public void addRow(TableLayout tableLayout, Element content) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tableRow = (TableRow) inflater.inflate(R.layout.element_view, null);
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

        TextView rateView = (TextView) tableRow.getChildAt(2);
        rateView.setTextColor(list.getPrimaryColor());
        setRate(rateView, content.getRate());
        tableLayout.addView(tableRow);
    }

    static void setRate(TextView rateView, int rate){
        StringBuilder newRate = new StringBuilder();
        for (int i = 1; i <= 5; i++){
            if (i <= rate){
                newRate.append("\u25CF");
            }
            else{
                newRate.append("\u25CB");
            }
        }
        rateView.setText(newRate.toString());
    }

    boolean isRequested(String request){
        return listTitle.equals(request);
    }
}
