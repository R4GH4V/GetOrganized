package com.example.android.getorganized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class ShopGridViewAdapter extends BaseAdapter {

    //Add the images here, in the same order you give the Label and pageURL.
    private Integer[] shopImages = {
            R.raw.blazers1,
            R.raw.blazers2,
            R.raw.blazers3,
            R.raw.coats1,
            R.raw.coats2,
            R.raw.coats3,
            R.raw.dress1,
            R.raw.dress2,
            R.raw.dress3,
            R.raw.shirtsm1,
            R.raw.shirtsm2,
            R.raw.shirtsm3,
            R.raw.shirtsf1,
            R.raw.shirtsf2,
            R.raw.shirtsf3,
            R.raw.sweaterm1,
            R.raw.sweaterm2,
            R.raw.sweaterm3
    };

    private Context context;
    private String[] imageLabels;

    private ShopGridViewAdapter(Context con, String[] labs) {
        this.context=con;
        this.imageLabels = labs;
    }



    @Override
    public int getCount() {
        return shopImages.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v;
        if (convertView == null) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.shop_grid_item, viewGroup, false);
        }
        else{
            v=convertView;
        }

        TextView textHeading = v.findViewById(R.id.shopHeading);
        ImageView thumbnailImage = v.findViewById(R.id.shopImage);
        thumbnailImage.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
        textHeading.setText(imageLabels[position]);
        thumbnailImage.setImageResource(shopImages[position]);
        return v;
    }
}