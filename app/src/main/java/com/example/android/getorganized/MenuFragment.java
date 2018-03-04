package com.example.android.getorganized;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

// Menu screen

public class MenuFragment extends ListFragment {

    int[] icons = new int[]{
            R.drawable.ic_statistics,
            R.drawable.ic_settings,
            R.drawable.ic_help,
            R.drawable.ic_feedback
    };

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fill the map
        return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_menu, null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String[] menu_array = getResources().getStringArray(R.array.menu_array);
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < menu_array.length; i ++) {
            map = new HashMap<String, String>();
            map.put("menu_text", menu_array[i]);
            map.put("menu_icon", Integer.toString(icons[i]));

            data.add(map);
        }
        // keys in map
        String[] from = {"menu_text", "menu_icon"};
        // id of Views
        int[] to = {R.id.menu_text, R.id.menu_icon};

        // adapter
        adapter = new SimpleAdapter(getActivity(), data, R.layout.menu_listview_layout, from, to);
        setListAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();;

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    ((MenuActivity) MenuFragment.this.getActivity()).transitTo(new StatisticsFragment());
                }
                if(position == 1) {
                    ((MenuActivity) MenuFragment.this.getActivity()).transitTo(new SettingsFragment());
                }
                if(position == 2) {
                    ((MenuActivity) MenuFragment.this.getActivity()).transitTo(new HelpFragment());
                }
            }
        });

    }
}
