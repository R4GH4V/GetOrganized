package com.example.android.getorganized;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnstart;
    private RadioButton eng,chi,male,female;
    private RadioGroup rgroup;
    private EditText email;
    private static final Integer checked = null;
    private  DatabaseHandler db;
    private String gender= null;
    private Date date;
    public MainActivity() {
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            db= new DatabaseHandler(MainActivity.this);
            db.userdata();
            boolean check= db.userdata_check();
            if (check == true)
            {
                Intent in = new Intent(MainActivity.this, NavdrawerActivity.class);
                startActivity(in);
            }
            setContentView(R.layout.activity_main);
            findviewByid();
            setonclicklistener();

            int i= db.checking();

            Log.e("FUcking shit",String.valueOf(i));

            final Handler handler= new Handler();
            Timer timer = new Timer();
            int i1=0;

            TimerTask doasynctask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @SuppressWarnings("unchecked")
                        public void run() {

                            try{
                                    Date date = new Date();

                                    String check = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);

                                    boolean result= db.checking(check);

                                    if(result=true)
                                    {
                                        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        String channel_id="mychannel";
                                        CharSequence name =" channel";
                                        String Description= "THis is sparta";
                                        int importance =NotificationManager.IMPORTANCE_HIGH;

                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                                            NotificationChannel nchannel= new NotificationChannel(channel_id,name,importance);
                                            nchannel.setDescription(Description);
                                            nchannel.enableLights(true);
                                            nchannel.setLightColor(Color.RED);
                                            nchannel.enableVibration(true);
                                            nchannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                            nchannel.setShowBadge(false);
                                            manager.createNotificationChannel(nchannel);
                                        }


                                        NotificationCompat.Builder notification= new NotificationCompat.Builder(getBaseContext(),channel_id);
                                        notification.setContentTitle(String.valueOf(R.string.app_name));
                                        notification.setContentText("Check this out");
                                        notification.setSmallIcon(R.mipmap.ic_launcher_round);

                                        Intent notifyintent= new Intent(MainActivity.this,viewdate.class);
                                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
                                        stackBuilder.addParentStack(MainActivity.class);
                                        stackBuilder.addNextIntent(notifyintent);
                                        notifyintent.putExtra("selected",check);
                                        PendingIntent pendingIntent= stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                                        notification.setContentIntent(pendingIntent);
                                        manager.notify(0,notification.build());
                                    }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
            timer.schedule(doasynctask,0,86400000);
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

                Log.e("Gender",gender);
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

