package com.example.android.listofbooksandfilms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Arslan on 03.03.2018.
 */

public class NavigationHelper {
    static void openNewActivity(Context context, Class classToOpen, String listTitle) {
        Intent openNew = new Intent(context, classToOpen);
        openNew.putExtra("listTitle", listTitle);
        context.startActivity(openNew);
    }

    static void openNewActivity(Context context, Class classToOpen, Element element, String listTitle, boolean enabled, boolean newElement) {
        Intent openNew = new Intent(context, classToOpen);
        openNew.putExtra("main", element.getMainText());
        openNew.putExtra("additional", element.getAdditionalText());
        openNew.putExtra("description", element.getDescriptionText());
        openNew.putExtra("rate", element.getRate());
        openNew.putExtra("listTitle", listTitle);
        openNew.putExtra("good", element.getIsGood());
        openNew.putExtra("enabled", enabled);
        openNew.putExtra("new", newElement);
        openNew.putExtra("id", element.getId());

        context.startActivity(openNew);
    }
}
