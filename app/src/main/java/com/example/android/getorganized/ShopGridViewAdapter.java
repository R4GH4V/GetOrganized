package com.example.android.getorganized;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;



public class ShopGridViewAdapter extends BaseAdapter {
    private DatabaseHandler db;
    private String gender=MainActivity.getGender();
    private int top_c,bottom_c,foot_c,acc_c;




    //Add the images here, in the same order you give the Label and pageURL.
    public Integer[] shopImages={
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


    public ShopGridViewAdapter(Context con) {
        this.context=con;
    }


    public void assign_shopImages(){
        if(gender=="Male" && top_c==0) {
            shopImages = new Integer[]{
                    R.raw.blazers1,
                    R.raw.blazers2,
                    R.raw.blazers3,
                    R.raw.coats1,
                    R.raw.coats2,
                    R.raw.coats3,
                    R.raw.sweaterm1,
                    R.raw.sweaterm2,
                    R.raw.sweaterm3,
                    R.raw.shirtsm1,
                    R.raw.shirtsm2,
                    R.raw.shirtsm3,
                    R.raw.tshirt1,
                    R.raw.tshirts2,
                    R.raw.tshirts3,
                    R.raw.athleticfoot1,
                    R.raw.athleticfoot2,
                    R.raw.athleticfoot3
            };
        }
        else if(gender=="Male" && bottom_c==0) {
            shopImages = new Integer[]{
                    R.raw.jeans1,
                    R.raw.jeans2,
                    R.raw.jeans3,
                    R.raw.shorts1,
                    R.raw.shorts2,
                    R.raw.shorts3,
                    R.raw.trousers1,
                    R.raw.trousers2,
                    R.raw.trousers3,
                    R.raw.bags1,
                    R.raw.bags2,
                    R.raw.bags3,
                    R.raw.belt1,
                    R.raw.belt2,
                    R.raw.belt3,
                    R.raw.sandals1,
                    R.raw.sandals2,
                    R.raw.sandals3
            };
        }
        else if(gender=="Male" && foot_c==0) {
            shopImages = new Integer[]{
                    R.raw.athleticfoot1,
                    R.raw.athleticfoot2,
                    R.raw.athleticfoot3,
                    R.raw.sandals1,
                    R.raw.sandals2,
                    R.raw.sandals3,
                    R.raw.shirtsm1,
                    R.raw.shirtsm2,
                    R.raw.shirtsm3,
                    R.raw.tshirt1,
                    R.raw.tshirts2,
                    R.raw.tshirts3,
                    R.raw.jeans1,
                    R.raw.jeans2,
                    R.raw.jeans3,
                    R.raw.sweaterm1,
                    R.raw.sweaterm2,
                    R.raw.sweaterm3
            };
        }
        else if(gender=="Male" && acc_c==0) {
            shopImages = new Integer[]{
                    R.raw.bags1,
                    R.raw.bags2,
                    R.raw.bags3,
                    R.raw.belt1,
                    R.raw.belt2,
                    R.raw.belt3,
                    R.raw.gloves1,
                    R.raw.gloves2,
                    R.raw.gloves3,
                    R.raw.hat1,
                    R.raw.hat2,
                    R.raw.hat3,
                    R.raw.sunglasses1,
                    R.raw.sunglasses2,
                    R.raw.sunglasses3,
                    R.raw.scarves1,
                    R.raw.scarves2,
                    R.raw.scarves3
            };
        }
        else if(gender=="Female" && top_c==0) {
            shopImages = new Integer[]{
                    R.raw.blazers1,
                    R.raw.blazers2,
                    R.raw.blazers3,
                    R.raw.dress1,
                    R.raw.dress2,
                    R.raw.dress3,
                    R.raw.sweaterf1,
                    R.raw.sweaterf2,
                    R.raw.sweaterf3,
                    R.raw.shirtsf1,
                    R.raw.shirtsf2,
                    R.raw.shirtsf3,
                    R.raw.tshirt1,
                    R.raw.tshirts2,
                    R.raw.tshirts3,
                    R.raw.coats1,
                    R.raw.coats2,
                    R.raw.coats3
            };
        }
        else if(gender=="Female" && bottom_c==0) {
            shopImages = new Integer[]{
                    R.raw.skirts1,
                    R.raw.skirts2,
                    R.raw.skirts3,
                    R.raw.shorts1,
                    R.raw.shorts2,
                    R.raw.shorts3,
                    R.raw.jeans1,
                    R.raw.jeans2,
                    R.raw.jeans3,
                    R.raw.trousers1,
                    R.raw.trousers2,
                    R.raw.trousers3,
                    R.raw.shirtsf1,
                    R.raw.shirtsf2,
                    R.raw.shirtsf3,
                    R.raw.jewellery1,
                    R.raw.jewellery2,
                    R.raw.jewellery3
            };
        }
        else if(gender=="Female" && foot_c==0)
        {
            shopImages = new Integer[]{
                    R.raw.heels1,
                    R.raw.heels2,
                    R.raw.heels3,
                    R.raw.bootsf1,
                    R.raw.bootsf2,
                    R.raw.bootsf3,
                    R.raw.flatsf1,
                    R.raw.flatsf2,
                    R.raw.flatsf3,
                    R.raw.sandals1,
                    R.raw.sandals2,
                    R.raw.sandals3,
                    R.raw.athleticfoot1,
                    R.raw.athleticfoot2,
                    R.raw.athleticfoot3,
                    R.raw.sweaterf1,
                    R.raw.sweaterf2,
                    R.raw.sweaterf3
            };
        }
        else if(gender=="Female" && acc_c==0)
        {
            shopImages = new Integer[]{
                    R.raw.jewellery1,
                    R.raw.jewellery2,
                    R.raw.jewellery3,
                    R.raw.watch1,
                    R.raw.watch2,
                    R.raw.watch3,
                    R.raw.scarves1,
                    R.raw.scarves2,
                    R.raw.scarves3,
                    R.raw.sunglasses1,
                    R.raw.sunglasses2,
                    R.raw.sunglasses3,
                    R.raw.socks1,
                    R.raw.socks2,
                    R.raw.socks3,
                    R.raw.bags1,
                    R.raw.bags2,
                    R.raw.bags3
            };
        }
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
        db=new DatabaseHandler(context);
        top_c=db.getCount("Top");
        bottom_c=db.getCount("Bottom");
        foot_c=db.getCount("Footwear");
        acc_c=db.getCount("Accessories");
        View v;
        if (convertView == null) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.shop_grid_item, viewGroup, false);
        }
        else{
            v=convertView;
        }

        assign_shopImages();
        ImageView thumbnailImage = v.findViewById(R.id.shopImage);
        thumbnailImage.setLayoutParams(new LinearLayout.LayoutParams(600, 600));
        thumbnailImage.setImageResource(shopImages[position]);
        return v;
    }
}