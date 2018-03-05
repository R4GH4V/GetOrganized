package com.example.android.getorganized;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SubmenuActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        if(position==0) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.submenu_container, new StatisticsFragment());
            ft.commit();
        }
        if(position==1) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.submenu_container, new SettingsFragment());
            ft.commit();
        }
        if(position==2) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.submenu_container, new HelpFragment());
            ft.commit();
        }
        if(position==3) {
            //Feedback, dialog fragment
        }

    }


}
