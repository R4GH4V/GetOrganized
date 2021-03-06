package com.example.android.getorganized;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


/*
    AddItemFragment: to add an item to the database
    shows after taking/choosing pictures
    attached to ItemActivity
 */

public class AddItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String DEBUG_TAG = "AddItemFragment";

    private DatabaseHandler db;
    private byte[] image;
    private Spinner kind_sp, category_sp, season_sp;
    private EditText price_et;
    private Button add_btn;
    private String kind, category, season,worn;
    private Integer price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        price_et = getActivity().findViewById(R.id.et_addprice);

        // kind spinner
        kind_sp = getActivity().findViewById(R.id.sp_addkind);
        category_sp = getActivity().findViewById(R.id.sp_addcategory);
        ArrayAdapter adapter_kind = ArrayAdapter.createFromResource(getActivity(), R.array.Kind_no_all, android.R.layout.simple_spinner_item);
        adapter_kind.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kind_sp.setAdapter(adapter_kind);
        kind_sp.setOnItemSelectedListener(this);

        // season spinner
        season_sp = getActivity().findViewById(R.id.sp_addseason);
        ArrayAdapter adapter_se = ArrayAdapter.createFromResource(getActivity(), R.array.Season, android.R.layout.simple_spinner_item);
        adapter_se.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        season_sp.setAdapter(adapter_se);

    }

    // for Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1= String.valueOf(kind_sp.getSelectedItem());
        if(sp1.contentEquals("Top") || sp1.contentEquals("上装")) {
            ArrayAdapter adapter_ca = ArrayAdapter.createFromResource(getActivity(),R.array.Top_no_all, android.R.layout.simple_spinner_item);
            category_sp.setAdapter(adapter_ca);
        }
        if(sp1.contentEquals("Bottom") || sp1.contentEquals("下装")){
            ArrayAdapter adapter_ca = ArrayAdapter.createFromResource(getActivity(),R.array.Bottom_no_all, android.R.layout.simple_spinner_item);
            category_sp.setAdapter(adapter_ca);
        }
        if(sp1.contentEquals("Footwear") || sp1.contentEquals("鞋类")){
            ArrayAdapter adapter_ca = ArrayAdapter.createFromResource(getActivity(),R.array.Footwear_no_all, android.R.layout.simple_spinner_item);
            category_sp.setAdapter(adapter_ca);
        }
        if(sp1.contentEquals("Accessories") || sp1.contentEquals("配饰")){
            ArrayAdapter adapter_ca = ArrayAdapter.createFromResource(getActivity(),R.array.Accessories_no_all, android.R.layout.simple_spinner_item);
            category_sp.setAdapter(adapter_ca);
        }
    }

    // for Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        db = new DatabaseHandler(getActivity());

        byte[] byteArray = getActivity().getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        image = profileImage(bmp);

        add_btn = (Button)getActivity().findViewById(R.id.btn_add);
        add_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addItem();
            }
        });
    }


    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }


    // click add button
    public void addItem() {
        kind = kind_sp.getSelectedItem().toString();
        category = category_sp.getSelectedItem().toString();
        if (price_et.getText().length() != 0)
            price = Integer.parseInt(price_et.getText().toString());
        else
            price = 0;
        season = season_sp.getSelectedItem().toString();

        if (db.addItem(new Item(image, kind, category, price, season))) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.savedsuccessfully, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.notsaved, Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(getActivity(), ClosetActivity.class);
        startActivity(intent);

        /*
        // to view the whole database in an AlertDialog
        Cursor res = db.dbViewData();
        if (res.getCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("Error");
            builder.setMessage("Nothing found");
            builder.show();

            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID: " + res.getString(0) + "\n");
            buffer.append("image byte[]: " + res.getBlob(1) + "\n");
            buffer.append("kind: " + res.getString(2) + "\n");
            buffer.append("category: " + res.getString(3) + "\n");
            buffer.append("price: " + res.getString(4) + "\n");
            buffer.append("season: " + res.getString(5) + "\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("All Stored Data:");
        builder.setMessage(buffer.toString());
        builder.show();
        */


    }


}