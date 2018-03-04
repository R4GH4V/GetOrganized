package com.example.android.getorganized;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviewByid();
    }

    private void findviewByid() {
        btnstart= (Button) findViewById(R.id.getStarted);
    }

    public void maleClick(View view) {
    }

    public void femaleClick(View view) {
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
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);

        finish();
    }
}
