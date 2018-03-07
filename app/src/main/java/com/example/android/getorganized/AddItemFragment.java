package com.example.android.getorganized;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static java.lang.Integer.getInteger;


public class AddItemFragment extends Fragment {

    private DatabaseHandler db;
    private byte[] image;
    private EditText kind_et;
    private EditText category_et;
    private EditText price_et;
    private EditText season_et;
    private Button add_btn;
    private String kind;
    private String category;
    private int price;
    private String season;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        db = new DatabaseHandler(getActivity());
        kind_et = getActivity().findViewById(R.id.et_kind);
        category_et = getActivity().findViewById(R.id.et_category);
        price_et = getActivity().findViewById(R.id.et_price);
        season_et = getActivity().findViewById(R.id.et_season);

        byte[] byteArray = getActivity().getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        image = profileImage(bmp);

        add_btn = (Button)getActivity().findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
            }
        });
    }


    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }


    public void addItem() {   // clicked add button
        kind = kind_et.getText().toString();
        category = category_et.getText().toString();
        price = getInteger(price_et.getText().toString(), 0);
        season = season_et.getText().toString();

        if (db.addItem(new Item(image, kind, category, price, season))) {
            Toast.makeText(getActivity().getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Not saved!!!", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(getActivity(), NavdrawerActivity.class);
        startActivity(intent);
    }


}