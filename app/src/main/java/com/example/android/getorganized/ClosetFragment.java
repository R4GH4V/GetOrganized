package com.example.android.getorganized;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/2/2018.
 */

public class ClosetFragment extends Fragment implements OnItemSelectedListener {

    Spinner spinner1,spinner2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_closet,null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spinner1 = (Spinner) getView().findViewById(R.id.spinner1);
        spinner2 = (Spinner) getView().findViewById(R.id.spinner2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.Kind, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String sp1= String.valueOf(spinner1.getSelectedItem());
        Toast.makeText(getActivity(), sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("Top") || sp1.contentEquals("最佳")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.Top, android.R.layout.simple_spinner_item);
            spinner2.setAdapter(adapter2);
        }
        if(sp1.contentEquals("Bottom") || sp1.contentEquals("底部")){
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.Bottom, android.R.layout.simple_spinner_item);
            spinner2.setAdapter(adapter2);
        }
        if(sp1.contentEquals("Footwear") || sp1.contentEquals("鞋")){
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.Footwear, android.R.layout.simple_spinner_item);
            spinner2.setAdapter(adapter2);
        }
        if(sp1.contentEquals("Accessories") || sp1.contentEquals("饰品")){
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.Accessories, android.R.layout.simple_spinner_item);
            spinner2.setAdapter(adapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
    }
}


