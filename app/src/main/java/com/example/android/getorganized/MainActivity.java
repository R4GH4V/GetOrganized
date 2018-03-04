package com.example.android.getorganized;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstart;
    private RadioButton eng,chi;
    private RadioGroup rgroup;
    private static final Integer checked = null;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String current = Locale.getDefault().getDisplayLanguage();
        Log.e("current",current);
        setContentView(R.layout.activity_main);
        findviewByid();
        setonclicklistener();
    }


    private void findviewByid() {
        btnstart= (Button) findViewById(R.id.getStarted);
        eng =(RadioButton)findViewById(R.id.eng);
        chi =(RadioButton)findViewById(R.id.chi);
        rgroup=(RadioGroup)findViewById(R.id.group);
    }

    private void setonclicklistener() {
        btnstart.setOnClickListener(this);
        eng.setOnClickListener(this);
        chi.setOnClickListener(this);
    }
    
    public void englishClick(View view) {
        setLocale("en");
    }

    public void chineseClick(View view) {
        setLocale("zh");
    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.getStarted:
                Intent in=new Intent(MainActivity.this,navdrawer.class);
                startActivity(in);
                break;

            case R.id.eng:
                setLocale("en");
                break;

            case R.id.chi:
                setLocale("zh");
                break;
        }
    }
    
    public void setLocale(String lang)
    {
        Locale locale= new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
}
