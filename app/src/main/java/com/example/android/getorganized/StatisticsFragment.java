package com.example.android.getorganized;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;


// Menu - Statistics

public class StatisticsFragment extends Fragment {

    private static final String DEBUG_TAG = "StatisticsFragment";

    private DatabaseHandler db;
    private TextView item_count, top_count, bottom_count, footwear_count, access_count;
    private TextView item_value, top_value, bottom_value, footwear_value, access_value;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.statistics);
        Log.d(DEBUG_TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_statistics,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "onViewCreated");
        db = new DatabaseHandler(getActivity());

        /*
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, "Toolbar", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), MenuActivity.class);
                        startActivity(i);
                    }
                }
        );
        */
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG, "onStart");

        item_count = (TextView) getActivity().findViewById(R.id.tv_itemcount);
        top_count = (TextView) getActivity().findViewById(R.id.tv_topcount);
        bottom_count = (TextView) getActivity().findViewById(R.id.tv_bottomcount);
        footwear_count = (TextView) getActivity().findViewById(R.id.tv_footwearcount);
        access_count = (TextView) getActivity().findViewById(R.id.tv_accesscount);
        item_value = (TextView) getActivity().findViewById(R.id.tv_itemvalue);
        top_value = (TextView) getActivity().findViewById(R.id.tv_topvalue);
        bottom_value = (TextView) getActivity().findViewById(R.id.tv_bottomvalue);
        footwear_value = (TextView) getActivity().findViewById(R.id.tv_footwearvalue);
        access_value = (TextView) getActivity().findViewById(R.id.tv_accessvalue);

        Locale current = getResources().getConfiguration().locale;
        String language = current.getLanguage();
        if (language.equals("zh")) {
            item_count.setText(Integer.toString(db.getCount("All")));
            top_count.setText(Integer.toString(db.getCount("上装")));
            bottom_count.setText(Integer.toString(db.getCount("下装")));
            footwear_count.setText(Integer.toString(db.getCount("鞋类")));
            access_count.setText(Integer.toString(db.getCount("配饰")));
            item_value.setText(Integer.toString(db.getSum("All")));
            top_value.setText(Integer.toString(db.getSum("上装")));
            bottom_value.setText(Integer.toString(db.getSum("下装")));
            footwear_value.setText(Integer.toString(db.getSum("鞋类")));
            access_value.setText(Integer.toString(db.getSum("配饰")));
        }
        else {
            item_count.setText(Integer.toString(db.getCount("All")));
            top_count.setText(Integer.toString(db.getCount("Top")));
            bottom_count.setText(Integer.toString(db.getCount("Bottom")));
            footwear_count.setText(Integer.toString(db.getCount("Footwear")));
            access_count.setText(Integer.toString(db.getCount("Accessories")));
            item_value.setText(Integer.toString(db.getSum("All")));
            top_value.setText(Integer.toString(db.getSum("Top")));
            bottom_value.setText(Integer.toString(db.getSum("Bottom")));
            footwear_value.setText(Integer.toString(db.getSum("Footwear")));
            access_value.setText(Integer.toString(db.getSum("Accessories")));
        }
    }
}
