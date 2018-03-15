package com.example.android.listofbooksandfilms;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.android.listofbooksandfilms.NavigationHelper.openNewActivity;

public class ListActivity extends AppCompatActivity {

    MyDatabaseHelper databaseHelper;
    private List list;
    private String listTitle;
    private RecyclerView mRecyclerView;
    private RecycleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new MyDatabaseHelper(ListActivity.this, "Database", 1);
        try {
            listTitle = getIntent().getExtras().getString("listTitle");
        } catch (NullPointerException e){
            Log.e("ListActivity OnCreate", "can not get listTitle");
        }

        if (isRequested(getString(R.string.books_list_title))){
            list = new List(this, R.color.booksPrimary, R.color.booksDark,
                    R.color.booksMainText, R.color.booksRate,
                    R.color.booksCardBackground, R.style.BooksTheme, R.string.books_list_title);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            list = new List(this, R.color.filmsPrimary, R.color.filmsDark,
                    R.color.filmsMainText, R.color.filmsRate,
                    R.color.filmsCardBackground,R.style.FilmsTheme, R.string.films_list_title);
        }
        else{
            list = new List(this, android.R.color.black, android.R.color.black,
                    android.R.color.black, android.R.color.black,
                    android.R.color.black, R.style.AppTheme_NoActionBar, R.string.app_name);
            return;
        }
        list.setContext(this);
        setTheme(list.getTheme());

        setContentView(R.layout.activity_list);
        setContent();
        setTitle(list.getTitle());

        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(ListActivity.this, AddNewElement.class,new Element("", "", "", 0, false, 0), listTitle, true, true);
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

        list.clear();
        if (cursor.moveToFirst()) {
            int mainColIndex = cursor.getColumnIndex("main");
            int additionalColIndex = cursor.getColumnIndex("additional");
            int descriptionColIndex = cursor.getColumnIndex("description");
            int rateColIndex = cursor.getColumnIndex("rate");
            int goodColIndex = cursor.getColumnIndex("good");
            int idColIndex = cursor.getColumnIndex("id");
            do {
                list.add(new Element(cursor.getString(mainColIndex), cursor.getString(additionalColIndex), cursor.getString(descriptionColIndex), cursor.getInt(rateColIndex), cursor.getInt(goodColIndex) == 1, cursor.getInt(idColIndex)));
            } while (cursor.moveToNext());
        }
        else{
            Log.e("if", "smth go wrong " + cursor.getColumnCount());
        }

        cursor.close();


        //fill listView
        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecycleAdapter(list);

        mAdapter.setOnItemClickListener(new RecycleAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                openNewActivity(ListActivity.this, AddNewElement.class, list.get(position), listTitle, false, false);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        final LayoutInflater inflater = getLayoutInflater();
        final View elementView = inflater.inflate(R.layout.element_view, null);

        LinearLayout elementLayout = elementView.findViewById(R.id.element_view);

        ((TextView)((RelativeLayout) elementLayout.getChildAt(1)).getChildAt(0)).setTextColor(getResources().getColor(list.getColorMainText()));
        ((TextView)((RelativeLayout) elementLayout.getChildAt(1)).getChildAt(1)).setTextColor(getResources().getColor(list.getColorDark()));
        ((TextView) elementLayout.getChildAt(2)).setTextColor(getResources().getColor(list.getColorPrimary()));

    }

    static String setRate(int rate){
        StringBuilder newRate = new StringBuilder();
        for (int i = 1; i <= 5; i++){
            if (i <= rate){
                newRate.append("\u25CF");
            }
            else{
                newRate.append("\u25CB");
            }
        }
        return newRate.toString();
    }

    boolean isRequested(String request){
        return listTitle.equals(request);
    }
}
