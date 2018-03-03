package com.example.android.getorganized;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnstart=(Button) findViewById(R.id.getStarted);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,navdrawer.class);
                startActivity(in);
            }
        });
    }

    public void maleClick(View view) {
    }

    public void femaleClick(View view) {
    }

    public void englishClick(View view) {
    }

    public void chineseClick(View view) {
    }
}
