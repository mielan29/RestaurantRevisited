package com.example.milan.restaurantrevisited;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment  extends ListFragment {

    public List<String> MenuItems = new ArrayList<>();
    public ArrayAdapter adapter;
    private RestoDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = this.getArguments();
        String add = arguments.getString("category");
        Log.d("string add", add);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String rBody = null;

        final String url = "https://resto.mprog.nl/menu?category="+add;
        Log.d("string url", url);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json response", response.toString());
                        try {
                            JSONArray categories = response.getJSONArray("items");
                            Log.d("json", categories.toString());
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject subitem = categories.getJSONObject(i);
                                MenuItems.add(subitem.getString("name"));
                            }
                            updateAdaptor();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.response", error.toString());
                    }
                }
        );
        queue.add(getRequest);
    }

    public void updateAdaptor() {
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, MenuItems);
        Log.d("menu", MenuItems.toString());
        this.setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        db = RestoDatabase.getInstance(getContext());
        String name = l.getItemAtPosition(position).toString();
        db.check(name);
        Log.d("you Ordered", name);
    }
}
