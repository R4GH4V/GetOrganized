package com.example.android.getorganized;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class add_cloth_calendar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseHandler db;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Bitmap bp;
    private byte[] image;
    private Spinner spinner11, spinner22;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.add);
        setContentView(R.layout.activity_add_cloth_calendar);

        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("selectdate");
        spinner11 = this.findViewById(R.id.spinner11);
        spinner22 = this.findViewById(R.id.spinner22);
        gridView = (GridView) this.findViewById(R.id.gridview1);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.Kind, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner11.setAdapter(adapter1);
        spinner11.setOnItemSelectedListener(this);
        db = new DatabaseHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        spinner22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sp2 = String.valueOf(spinner22.getSelectedItem());
                if (!sp2.equals("All") && !sp2.equals("全部")) {
                    showRecords("", sp2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            case R.id.spinner11:

                String sp1 = String.valueOf(spinner11.getSelectedItem());
                showRecords(sp1, "All");

                if (sp1.contentEquals("All") || sp1.contentEquals("全部")) {

                    spinner22.setEnabled(false);
                }
                if (sp1.contentEquals("Top") || sp1.contentEquals("上装")) {
                    spinner22.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Top, android.R.layout.simple_spinner_item);
                    spinner22.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Bottom") || sp1.contentEquals("下装")) {
                    spinner22.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Bottom, android.R.layout.simple_spinner_item);
                    spinner22.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Footwear") || sp1.contentEquals("鞋类")) {
                    spinner22.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Footwear, android.R.layout.simple_spinner_item);
                    spinner22.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Accessories") || sp1.contentEquals("配饰")) {
                    spinner22.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Accessories, android.R.layout.simple_spinner_item);
                    spinner22.setAdapter(adapter2);
                }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Convert and resize our image to 400dp for faster uploading our images to DB
    public Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(this.getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(this.getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    private void showRecords(String kind, String category) {  //change to gridView
        ArrayList<Item> itemArray = new ArrayList<Item>();
        List<Item> items = db.getAllItems(kind, category);
        for (Item item : items) {
            itemArray.add(item);
        }

        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, itemArray);//an arraylist of );
        gridView.setAdapter(gridAdapter);

        // set click listener for each image in the grid view, will open ShowItemFragment

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View v,
                                    final int position, long id) {

                final AlertDialog.Builder dialogbox = new AlertDialog.Builder(new ContextThemeWrapper(add_cloth_calendar.this,R.style.dailog));
                dialogbox.setTitle(R.string.add);
                dialogbox.setMessage(R.string.doyouwanttoaddthisitemforthisdate);
                dialogbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Item item = (Item) parent.getItemAtPosition(position);
                        Integer id = item.getID();

                        boolean check= db.clothmatch(id,date);
                        if(check==true) Toast.makeText(getBaseContext(),R.string.imagealreadyselectedforthisdate,Toast.LENGTH_LONG).show();
                        else db.adddatainworn(id, date);

                        Intent intent =new Intent();
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    }
                });
                dialogbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog alertDialog= dialogbox.create();
                alertDialog.show();
            }
        });
    }
}

    //Retrieve data from the database and set to the list view



