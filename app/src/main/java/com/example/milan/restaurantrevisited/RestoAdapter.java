package com.example.milan.restaurantrevisited;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Milan on 4-12-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {
    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.list_item, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView Title = view.findViewById(R.id.Title);
        Title.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        //Title.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
    }
}
