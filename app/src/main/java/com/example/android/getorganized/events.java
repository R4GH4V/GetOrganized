package com.example.android.getorganized;

import java.util.Date;

/**
 * Created by Nisarg on 3/13/2018.
 */

public class events {

    private int id;
    private String date;

    public events(){
    }

    public events(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
