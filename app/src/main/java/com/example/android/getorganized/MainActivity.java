package com.example.android.getorganized;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstart;
    private RadioButton eng,chi,male,female;
    private RadioGroup rgroup;
    private EditText email;
    private static final Integer checked = null;
    private  DatabaseHandler db;
    private String gender= null;
    public MainActivity() {
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            db= new DatabaseHandler(MainActivity.this);
            db.userdata();
            boolean check= db.userdata_check();
            if (check)
            {
                Intent in = new Intent(MainActivity.this, NavdrawerActivity.class);
                startActivity(in);
            }
            setContentView(R.layout.activity_main);
            findviewByid();
            setonclicklistener();
        }


    private void findviewByid() {
        btnstart = (Button) findViewById(R.id.getStarted);
        eng = (RadioButton) findViewById(R.id.eng);
        chi = (RadioButton) findViewById(R.id.chi);
        rgroup = (RadioGroup) findViewById(R.id.group);
        email=(EditText)findViewById(R.id.et_email);
    }

    private void setonclicklistener() {
        btnstart.setOnClickListener(this);
        eng.setOnClickListener(this);
        chi.setOnClickListener(this);
    }

    public void maleClick(View view) {gender="Male";}

    public void femaleClick(View view) {gender="Female";}


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.getStarted:

                if(email.getText()!=null)
                {
                    if(gender=="Male" || gender=="Female")
                    {
                        String mail =email.getText().toString().trim();
                        db.adduserdata(mail,gender);
                        Intent in = new Intent(MainActivity.this, NavdrawerActivity.class);
                        startActivity(in);
                    }
                    else
                    {
                        Toast.makeText(this, R.string.gender_error,Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                else
                {
                    if(email.getText()==null)
                    {
                        Toast.makeText(this, R.string.email_error,Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.eng:
                setLocale("en");
                break;

            case R.id.chi:
                setLocale("zh");
                break;
        }
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
}

