package com.example.android.getorganized;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

// any activity that extends NavDrawerBaseActivity will have the navigation drawer

public class MenuActivity extends NavDrawerBaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, new MenuFragment());
        ft.commit();
    }

    public void transitTo(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        ft.replace(R.id.screen_area, new MenuFragment());
        ft.commit();
    }
}
