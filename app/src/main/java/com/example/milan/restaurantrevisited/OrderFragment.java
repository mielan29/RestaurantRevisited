package com.example.milan.restaurantrevisited;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends DialogFragment {

    public ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        RestoDatabase db = RestoDatabase.getInstance(getContext());
        Cursor cursor = db.selectAll();
        lv = getView().findViewById(R.id.Order);
        Log.d("order is op dit moment", cursor.toString());
        Refresh();
    }

    public void Refresh() {
        RestoDatabase db = RestoDatabase.getInstance(getContext());
        Cursor cursor = db.selectAll();
        RestoAdapter adapter = new RestoAdapter(getContext(), cursor);
        lv.setAdapter(adapter);
    }

}
