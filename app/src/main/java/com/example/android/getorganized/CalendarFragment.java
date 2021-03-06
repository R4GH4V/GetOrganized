package com.example.android.getorganized;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 3/2/2018.
 */

public class CalendarFragment extends Fragment{
    private static final String TAG = CalendarFragment.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context=getContext();
    private GridAdapter mAdapter;
    private static String abc="";
    private Button set,see;
    private DatabaseHandler databaseHandler;
    private static String selecteddate;

    public CalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.calendar);
        View view = inflater.inflate(R.layout.fragment_calendar,null);
        previousButton = (ImageView)view.findViewById(R.id.previous_month);
        nextButton = (ImageView)view.findViewById(R.id.next_month);
        currentDate = (TextView)view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView)view.findViewById(R.id.calendar_grid);

        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        return view;
    }

    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }
    private void setGridCellClickEvents(){
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Date main = (Date) parent.getItemAtPosition(position);

                selecteddate= DateFormat.getDateInstance(DateFormat.MEDIUM).format(main);

                TextView field = (TextView) view.findViewById(R.id.calendar_date_id);

                Intent intent= new Intent(getContext(),viewdate.class);
                intent.putExtra("selected",selecteddate);
                startActivity(intent);


            }
        });
    }

    @SuppressLint("LongLogTag")
    private void setUpCalendarAdapter(){
        List<Date> dayValueInCells = new ArrayList<Date>();
        List<events> event = new ArrayList<events>();
        DatabaseHandler db= new DatabaseHandler(getContext());
        event.addAll(db.getevents());

        Log.e("Size of list in calendar: ",String .valueOf(event.size()));
        Calendar mCal = (Calendar)cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while(dayValueInCells.size() < MAX_CALENDAR_COLUMN){
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        mAdapter = new GridAdapter(getContext(), dayValueInCells, cal,event);
        calendarGridView.setAdapter(mAdapter);
    }

}
