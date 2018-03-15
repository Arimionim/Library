package com.example.android.listofbooksandfilms;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.android.listofbooksandfilms.ListActivity.setRate;

public class AddNewElement extends AppCompatActivity {

    String listTitle;
    int rate;
    boolean enabled;
    boolean isNewElement;
    int id;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            listTitle = getIntent().getExtras().getString("listTitle");
            enabled = getIntent().getExtras().getBoolean("enabled");
            isNewElement = getIntent().getExtras().getBoolean("new");
            id = getIntent().getExtras().getInt("id");
            databaseHelper = new MyDatabaseHelper(AddNewElement.this, "Database", 1);
        } catch (NullPointerException e){
            Log.e("ListActivity OnCreate", "error with declaration");
        }

        setContent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_element);

        if (isRequested(getString(R.string.books_list_title))){
            setColor(R.color.booksPrimary, R.color.booksDark);
            if (isNewElement) {
                ((TextView) findViewById(R.id.new_element_title)).setText(R.string.new_book_title);
                ((TextView) findViewById(R.id.new_element_accept_button)).setText(R.string.new_book_add_button);
            }
            else{
                ((TextView) findViewById(R.id.new_element_title)).setText(R.string.books_list_title);
                ((TextView) findViewById(R.id.new_element_accept_button)).setText(R.string.save);
            }
            ((TextView) findViewById(R.id.good_title)).setText(R.string.new_book_good_title);
            ((TextView) findViewById(R.id.rate_title)).setText(R.string.new_book_rate_title);
            ((TextView) findViewById(R.id.new_main_text)).setHint(R.string.new_book_main_text_hint);
            ((TextView) findViewById(R.id.new_additional_text)).setHint(R.string.new_book_additional_text_hint);
            ((TextView) findViewById(R.id.new_description_text)).setHint(R.string.new_book_description_hint);
            ((TextView) findViewById(R.id.new_element_decline_button)).setText(R.string.cancel);
            ((TextView) findViewById(R.id.new_element_delete_button)).setText(R.string.new_book_delete_button);
        }
        else if (isRequested(getString(R.string.films_list_title))){
            setColor(R.color.filmsPrimary, R.color.filmsDark);
            if (isNewElement) {
                ((TextView) findViewById(R.id.new_element_title)).setText(R.string.new_films_title);
                ((TextView) findViewById(R.id.new_element_accept_button)).setText(R.string.new_films_add_button);
            }
            else{
                ((TextView) findViewById(R.id.new_element_title)).setText(R.string.films_list_title);
                ((TextView) findViewById(R.id.new_element_accept_button)).setText(R.string.ok);
            }
            ((TextView) findViewById(R.id.good_title)).setText(R.string.new_films_good_title);
            ((TextView) findViewById(R.id.rate_title)).setText(R.string.new_films_rate_title);
            ((TextView) findViewById(R.id.new_main_text)).setHint(R.string.new_films_main_text_hint);
            ((TextView) findViewById(R.id.new_additional_text)).setHint(R.string.new_films_additional_text_hint);
            ((TextView) findViewById(R.id.new_description_text)).setHint(R.string.new_films_description_hint);
            ((TextView) findViewById(R.id.new_element_decline_button)).setText(R.string.cancel);
            ((TextView) findViewById(R.id.new_element_delete_button)).setText(R.string.new_films_delete_button);
        }

        TextView declineButton = findViewById(R.id.new_element_decline_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNewElement) {
                    finish();
                }
                else{
                    enabled = false;
                    updateEnabled(enabled);
                    updateContent();
                }
            }
        });

        TextView acceptButton = findViewById(R.id.new_element_accept_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((TextView)findViewById(R.id.new_main_text)).getTextSize() == 0){

                }
                push(databaseHelper);
                if (isNewElement) {
                    finish();
                }
                else{
                    enabled = false;
                    updateEnabled(enabled);
                }
            }
        });

        TextView editButton = findViewById(R.id.new_element_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enabled = true;
                updateEnabled(true);
            }
        });

        TextView deleteButton = findViewById(R.id.new_element_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
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
                rate = (int)(xC / (wight / 5)) + 1;
                rateView.setText(setRate(rate));
                return false;
            }
        });

        rateView.setText(setRate(getIntent().getExtras().getInt("rate")));
        ((CheckBox) findViewById(R.id.good_checkbox)).setChecked(getIntent().getExtras().getBoolean("good"));
        ((TextView) findViewById(R.id.new_main_text)).setText((getIntent().getExtras().getString("main")));
        ((TextView) findViewById(R.id.new_additional_text)).setText((getIntent().getExtras().getString("additional")));
        ((TextView) findViewById(R.id.new_description_text)).setText((getIntent().getExtras().getString("description")));

        updateEnabled(enabled);
    }

    private void setColor(int mainColor, int darkColor) {
        ((TextView)findViewById(R.id.rate_view)).setTextColor(getResources().getColor(mainColor));
        ((TextView)findViewById(R.id.new_element_title)).setTextColor(getResources().getColor(darkColor));
        ((TextView)findViewById(R.id.rate_title)).setTextColor(getResources().getColor(darkColor));
        ((TextView)findViewById(R.id.good_title)).setTextColor(getResources().getColor(darkColor));
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
        if (isNewElement) {
            if (isRequested(getString(R.string.books_list_title))) {
                database.insert("Books", null, newElement);
            } else if (isRequested(getString(R.string.films_list_title))) {
                database.insert("Films", null, newElement);
            }
        }
        else{
            if (isRequested(getString(R.string.books_list_title))) {
                database.update("Books", newElement, "id = ?", new String[] {
                        id + ""
                });
            }
                else if (isRequested(getString(R.string.films_list_title))) {
                database.update("Films", newElement, "id = ?", new String[] {
                        id + ""
                });
            }
        }
    }

    private void updateEnabled(boolean enabled){
        RelativeLayout activity = findViewById(R.id.new_element_view);
        for (int i = 0; i < activity.getChildCount(); i++){
            activity.getChildAt(i).setEnabled(enabled);
        }
        activity.getChildAt(6).setEnabled(true);
        activity.getChildAt(6).setClickable(enabled);
        activity.getChildAt(7).setEnabled(true);
        activity.getChildAt(8).setEnabled(true);
        activity.getChildAt(10).setEnabled(!enabled);
        activity.getChildAt(11).setEnabled(enabled);

        if (enabled){
            activity.getChildAt(7).setVisibility(View.VISIBLE);
            activity.getChildAt(8).setVisibility(View.VISIBLE);
            activity.getChildAt(10).setVisibility(View.INVISIBLE);
            activity.getChildAt(11).setVisibility(View.VISIBLE);
        }
        else{
            activity.getChildAt(7).setVisibility(View.INVISIBLE);
            activity.getChildAt(8).setVisibility(View.INVISIBLE);
            activity.getChildAt(10).setVisibility(View.VISIBLE);
            activity.getChildAt(11).setVisibility(View.INVISIBLE);
        }
        if (isNewElement){
            activity.getChildAt(11).setVisibility(View.INVISIBLE);
            activity.setEnabled(false);
        }
    }

    void delete(){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        if (isRequested(getString(R.string.books_list_title))) {
            database.delete("Books", "id = " + id, null);
        }
        else if (isRequested(getString(R.string.films_list_title))) {
            database.delete("Films", "id = " + id, null);
        }
    }

    void updateContent(){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor;
        if (isRequested(getString(R.string.books_list_title))) {
            cursor = database.query("Books", null, "id = ?", new String[] {id + ""}, null, null, null);
        }
        else if (isRequested(getString(R.string.films_list_title))) {
            cursor = database.query("Films", null,  "id = ?", new String[] {id + ""}, null, null, null);
        }
        else{
            Log.e("Add, up", "unknown list");
            return;
        }

        if (cursor.moveToFirst()) {
            int mainColIndex = cursor.getColumnIndex("main");
            int additionalColIndex = cursor.getColumnIndex("additional");
            int descriptionColIndex = cursor.getColumnIndex("description");
            int rateColIndex = cursor.getColumnIndex("rate");
            int goodColIndex = cursor.getColumnIndex("good");

            ((CheckBox) findViewById(R.id.good_checkbox)).setChecked(cursor.getInt(goodColIndex) == 1);
            ((TextView) findViewById(R.id.new_main_text)).setText(cursor.getString(mainColIndex));
            ((TextView) findViewById(R.id.new_additional_text)).setText(cursor.getString(additionalColIndex));
            ((TextView) findViewById(R.id.new_description_text)).setText(cursor.getString(descriptionColIndex));
            ((TextView) findViewById(R.id.rate_view)).setText(setRate(cursor.getInt(rateColIndex)));
        }
        else{
            Log.e("Add, update", "can't set cursor");
        }

        cursor.close();
    }
}
