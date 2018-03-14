package com.example.android.getorganized;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import static android.content.ContentValues.TAG;


public class ShopFragment extends Fragment {
    @Nullable
    private String[] pages;
    private DatabaseHandler db;

    private String gender;
    private int top_c,bottom_c,foot_c,acc_c;




    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.shop);
        setRetainInstance(true);
        db = new DatabaseHandler(getActivity());
        return inflater.inflate(R.layout.fragment_shop,null);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gender =MainActivity.getGender();
        top_c=db.getCount("Top");
        bottom_c=db.getCount("Bottom");
        foot_c=db.getCount("Footwear");
        acc_c=db.getCount("Accessories");
    }

    public void setPages(){
        Resources res= getResources();
        if(gender==null){
            pages = res.getStringArray(R.array.unisex);
        }
        else if(gender=="Male" && top_c==0) {
            pages = res.getStringArray(R.array.male_top_zero);
        }
        else if (gender=="Male" && bottom_c==0){
            pages = res.getStringArray(R.array.male_bottom_zero);
        }
        else if (gender=="Male" && foot_c==0){
            pages = res.getStringArray(R.array.male_foot_zero);
        }
        else if(gender=="Male" && acc_c==0){
            pages = res.getStringArray(R.array.male_acc_zero);
        }
        else if(gender=="Female" && top_c==0){
            pages = res.getStringArray(R.array.female_top_zero);
        }
        else if(gender=="Female" && bottom_c==0){
            pages = res.getStringArray(R.array.female_bottom_zero);
        }
        else if(gender=="Female" && foot_c==0){
            pages = res.getStringArray(R.array.female_foot_zero);
        }
        else if (gender=="Female" && acc_c==0){
            pages = res.getStringArray(R.array.female_acc_zero);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Getting the resources from values->shop_arrays and putting them in String arrays.
        setPages();
        Log.d(TAG, "onViewCreated:  "+gender);
        GridView grid= getView().findViewById(R.id.shopGrid);
        ShopGridViewAdapter myAdapter = new ShopGridViewAdapter(getActivity());
        grid.setAdapter(myAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent webPageIntent = new Intent(getActivity(), ShopDisplayActivity.class);
                webPageIntent.putExtra("WEB_PAGE", pages[ position ] );
                startActivity( webPageIntent );
            }
        });


    }
}