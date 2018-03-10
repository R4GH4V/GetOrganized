package com.example.android.getorganized;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class ShopFragment extends Fragment {
    @Nullable

    String[] labels;
    String[] pages;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.shop);
        return inflater.inflate(R.layout.fragment_shop,null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Getting the resources from values->shop_arrays and putting them in String arrays.
        Resources res= getResources();
        labels=res.getStringArray(R.array.headings);
        pages=res.getStringArray(R.array.pages);



        GridView grid= view.findViewById(R.id.shopGrid);
        ShopGridViewAdapter myAdapter = new ShopGridViewAdapter(getActivity(), labels);
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