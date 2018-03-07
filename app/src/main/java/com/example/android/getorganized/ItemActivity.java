package com.example.android.getorganized;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        String add_or_edit = intent.getStringExtra("add_or_edit");

        if (add_or_edit.equals("add")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.item_fragment_container, new AddItemFragment());
            ft.commit();
        }

        //if (add_or_edit.equals("edit")) {
        //    FragmentManager fragmentManager = getSupportFragmentManager();
        //    FragmentTransaction ft = fragmentManager.beginTransaction();
        //    ft.replace(R.id.item_fragment_container, new EditItemFragment());
        //    ft.commit();
        //}
    }
}
