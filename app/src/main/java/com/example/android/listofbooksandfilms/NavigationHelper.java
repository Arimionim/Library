package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Arslan on 03.03.2018.
 */

public class NavigationHelper {
    static void openNewActivity(Context context, Class classToOpen, String listTitle) {
        Intent openNew = new Intent(context, classToOpen);
        openNew.putExtra("listTitle", listTitle);
        context.startActivity(openNew);
    }

    static void openNewActivity(Context context, Class classToOpen) {
        Intent openNew = new Intent(context, classToOpen);
        context.startActivity(openNew);
    }
}
