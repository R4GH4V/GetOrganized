package com.example.android.getorganized;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


// Menu - Statistics

public class StatisticsFragment extends Fragment {

    private DatabaseHandler db;
    private TextView item_count, top_count, bottom_count, footwear_count, access_count;
    private TextView item_value, top_value, bottom_value, footwear_value, access_value;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.statistics);
        return inflater.inflate(R.layout.fragment_statistics,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        item_count = (TextView) getActivity().findViewById(R.id.tv_itemcount);
        item_count.setText(Integer.toString(db.getCount("All")));

        top_count = (TextView) getActivity().findViewById(R.id.tv_topcount);
        top_count.setText(Integer.toString(db.getCount("Top")));

        bottom_count = (TextView) getActivity().findViewById(R.id.tv_bottomcount);
        bottom_count.setText(Integer.toString(db.getCount("Bottom")));

        footwear_count = (TextView) getActivity().findViewById(R.id.tv_footwearcount);
        footwear_count.setText(Integer.toString(db.getCount("Footwear")));

        access_count = (TextView) getActivity().findViewById(R.id.tv_accesscount);
        access_count.setText(Integer.toString(db.getCount("Accessories")));

        item_value = (TextView) getActivity().findViewById(R.id.tv_itemvalue);
        item_value.setText(Integer.toString(db.getSum("All")));

        top_value = (TextView) getActivity().findViewById(R.id.tv_topvalue);
        top_value.setText(Integer.toString(db.getSum("Top")));

        bottom_value = (TextView) getActivity().findViewById(R.id.tv_bottomvalue);
        bottom_value.setText(Integer.toString(db.getSum("Bottom")));

        footwear_value = (TextView) getActivity().findViewById(R.id.tv_footwearvalue);
        footwear_value.setText(Integer.toString(db.getSum("Footwear")));

        access_value = (TextView) getActivity().findViewById(R.id.tv_accessvalue);
        access_value.setText(Integer.toString(db.getSum("Accessories")));
    }
}
