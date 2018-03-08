package com.example.android.listofbooksandfilms;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.example.android.listofbooksandfilms.ListActivity.setRate;

public class AddNewElement extends AppCompatActivity {

    String listTitle;
    int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            listTitle = getIntent().getExtras().getString("listTitle");
        } catch (NullPointerException e){
            Log.e("ListActivity OnCreate", "can not get listTitle");
        }

        setContent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_element);

        if (isRequested(getString(R.string.books_list_title))){
            ((TextView )findViewById(R.id.rate_view)).setTextColor(getResources().getColor(R.color.booksPrimary));
        }
        else if (isRequested(getString(R.string.films_list_title))){
            ((TextView )findViewById(R.id.rate_view)).setTextColor(getResources().getColor(R.color.filmsPrimary));
        }

        TextView declineButton = findViewById(R.id.new_element_decline_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView acceptButton = findViewById(R.id.new_element_accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper databaseHelper;
                databaseHelper = new MyDatabaseHelper(AddNewElement.this, "Database", 1);
                push(databaseHelper);
                finish();
            }
        });

        rate = 0;
        final TextView rateView = findViewById(R.id.rate_view);

        rateView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float xC = motionEvent.getX();
                float wight = rateView.getWidth();
                Log.e("new", "");
                rate = (int)(xC / (wight / 5)) + 1;
                setRate(rateView, rate);

                return false;
            }
        });

        setRate(rateView, 0);
    }

    void setContent(){
        if (isRequested(getString(R.string.books_list_title))){
            setTheme(R.style.BooksTheme);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            setTheme(R.style.FilmsTheme);
        }
    }

    boolean isRequested(String request){
        return listTitle.equals(request);
    }

    void push(MyDatabaseHelper databaseHelper){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues newElement = new ContentValues();
        RelativeLayout activity = findViewById(R.id.new_element_view);

        newElement.put("main", (((EditText) activity.getChildAt(1)).getText()).toString());
        newElement.put("additional", (((EditText) activity.getChildAt(2)).getText()).toString());
        newElement.put("description", (((EditText) activity.getChildAt(9)).getText()).toString());
        newElement.put("good", ((CheckBox) activity.getChildAt(6)).isChecked());
        newElement.put("rate", rate);

        if (isRequested(getString(R.string.books_list_title))){
            database.insert("Books", null, newElement);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            database.insert("Films", null, newElement);
        }
    }
}
