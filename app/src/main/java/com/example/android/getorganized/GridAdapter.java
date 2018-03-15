package com.example.android.getorganized;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Nisarg on 3/7/2018.
 */

public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<events> allevents;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate,List<events> events) {
        super( context,R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allevents=events;
    }
    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        mInflater = LayoutInflater.from(this.getContext());
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }
        if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        } else {
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));

        TextView event = (TextView) view.findViewById(R.id.event_id);
        Calendar calendar = Calendar.getInstance();
        DatabaseHandler db = new DatabaseHandler(getContext());
        for (int i = 0; i < allevents.size(); i++) {
            String abc = allevents.get(i).getDate();
            Date main = db.convertStringtodate(abc);
            calendar.setTime(main);
            if (dayValue == calendar.get(Calendar.DAY_OF_MONTH) && displayMonth == calendar.get(Calendar.MONTH) + 1
                    && displayYear == calendar.get(Calendar.YEAR)) {
                event.setBackgroundColor(R.color.colorPrimaryDarkest);
            }
        }
        return view;
    }
    @Override
    public int getCount() {
        return monthlyDates.size();
    }
    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }
    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }
}
