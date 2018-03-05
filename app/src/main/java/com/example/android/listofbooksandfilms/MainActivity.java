package com.example.android.listofbooksandfilms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.android.listofbooksandfilms.NavigationHelper.openNewActivity;
import static com.example.android.listofbooksandfilms.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        TextView booksView = findViewById(R.id.books_main_page_view);
        booksView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(MainActivity.this, ListActivity.class, getString(R.string.books_list_title));
            }
        });

        TextView filmsView = findViewById(R.id.films_main_page_view);
        filmsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity(MainActivity.this, ListActivity.class, getString(R.string.films_list_title));
            }
        });
    }
}