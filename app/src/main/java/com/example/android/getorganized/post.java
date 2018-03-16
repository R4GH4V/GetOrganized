package com.example.android.getorganized;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class post extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseHandler db;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Bitmap bp;
    private byte[] image;
    private Spinner spinner111, spinner222;
    private StorageReference mstorageref;
    private DatabaseReference mdatabaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Post");
        setContentView(R.layout.activity_post);

        spinner111 = this.findViewById(R.id.spinner111);
        spinner222 = this.findViewById(R.id.spinner222);
        gridView = (GridView) this.findViewById(R.id.gridview11);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.Kind, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner111.setAdapter(adapter1);
        spinner111.setOnItemSelectedListener(this);
        db = new DatabaseHandler(this);
        mstorageref= FirebaseStorage.getInstance().getReference("uploads");
        mdatabaseref= FirebaseDatabase.getInstance().getReference("uploads");
    }

    @Override
    protected void onStart() {
        super.onStart();

        spinner222.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sp2 = String.valueOf(spinner222.getSelectedItem());
                if (!sp2.equals("All")) {
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

            case R.id.spinner111:

                String sp1 = String.valueOf(spinner111.getSelectedItem());
                showRecords(sp1, "All");

                if (sp1.contentEquals("All") || sp1.contentEquals("全部")) {

                    spinner222.setEnabled(false);
                }
                if (sp1.contentEquals("Top") || sp1.contentEquals("上装")) {
                    spinner222.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Top, android.R.layout.simple_spinner_item);
                    spinner222.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Bottom") || sp1.contentEquals("下装")) {
                    spinner222.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Bottom, android.R.layout.simple_spinner_item);
                    spinner222.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Footwear") || sp1.contentEquals("鞋类")) {
                    spinner222.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Footwear, android.R.layout.simple_spinner_item);
                    spinner222.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Accessories") || sp1.contentEquals("配饰")) {
                    spinner222.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.Accessories, android.R.layout.simple_spinner_item);
                    spinner222.setAdapter(adapter2);
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

                final AlertDialog.Builder dialogbox = new AlertDialog.Builder(new ContextThemeWrapper(post.this,R.style.dailog));
                dialogbox.setTitle(R.string.post);
                dialogbox.setMessage(R.string.doyouwanttoaddthisitemforthisdate);
                dialogbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Item item = (Item) parent.getItemAtPosition(position);

                        StorageReference image= mstorageref.child("added-"+System.currentTimeMillis());

                        UploadTask uploadTask=image.putBytes(item.getImage());
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                }, 500);
                                Toast.makeText(post.this, R.string.uploadsucess, Toast.LENGTH_LONG).show();

                                String user=db.getuser();
                                upload upload = new upload(user,taskSnapshot.getDownloadUrl().toString());
                                String uploadId = mdatabaseref.push().getKey();
                                mdatabaseref.child(uploadId).setValue(upload);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(post.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            }
                        });

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
