package com.example.android.getorganized;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ClosetFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DatabaseHandler db;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private Bitmap bp;
    private byte[] image;

    Spinner spinner1,spinner2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // change the title in the toolbar
        getActivity().setTitle(R.string.closet);

        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_closet, container, false);
    }

    //ActionBar Button Logic:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        //ActionBar Click handling
        if(id==R.id.ac_camera)
        {
            callCamera();
        }
        if(id==R.id.ac_gallery)
        {
            callGallery();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // super.onActivityCreated(savedInstanceState);

        spinner1 = getView().findViewById(R.id.spinner1);
        spinner2 = getView().findViewById(R.id.spinner2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.Kind, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        //setContentView(R.layout.fragment_closet);

        db = new DatabaseHandler(getActivity());

        // show all items at first
        //showRecords("All", "");
    }

    @Override
    public void onStart() {
        super.onStart();

        // for category spinner
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sp2 = String.valueOf(spinner2.getSelectedItem());
                if(!sp2.equals("All")) {
                    showRecords("", sp2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

    }

    // for kind spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch(adapterView.getId()) {

            case R.id.spinner1:

                String sp1 = String.valueOf(spinner1.getSelectedItem());
                showRecords(sp1, "All");

                if (sp1.contentEquals("All") || sp1.contentEquals("全部")) {

                    spinner2.setEnabled(false);
                }
                if (sp1.contentEquals("Top") || sp1.contentEquals("上装")) {
                    spinner2.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.Top, android.R.layout.simple_spinner_item);
                    spinner2.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Bottom") || sp1.contentEquals("下装")) {
                    spinner2.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.Bottom, android.R.layout.simple_spinner_item);
                    spinner2.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Footwear") || sp1.contentEquals("鞋类")) {
                    spinner2.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.Footwear, android.R.layout.simple_spinner_item);
                    spinner2.setAdapter(adapter2);
                }
                if (sp1.contentEquals("Accessories") || sp1.contentEquals("配饰")) {
                    spinner2.setEnabled(true);
                    ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.Accessories, android.R.layout.simple_spinner_item);
                    spinner2.setAdapter(adapter2);
                }

            //case R.id.spinner2:
            //    String spi1 = String.valueOf(spinner1.getSelectedItem());
            //    String sp2 = String.valueOf(spinner2.getSelectedItem());
            //    showRecords(spi1, sp2);   // in gridview, show images within this kind and category

        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Do nothing
    }


    public void callGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 2);
    }

    public void callCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.setType("image/*");
        startActivityForResult(cameraIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch(requestCode) {
            case 1:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte byteArray1[] = stream.toByteArray();

                    Intent intent1 = new Intent(getActivity(), ItemActivity.class);
                    intent1.putExtra("image", byteArray1);
                    intent1.putExtra("add_show_edit", "add");
                    startActivity(intent1);

                }
                break;

//                if(resultCode == RESULT_OK){
//                    Uri takenImage = data.getData();
//
//                    if(takenImage !=null) {
//
//                        bp = decodeUri(takenImage, 400);
//                        //image = profileImage(bp);   // image in byte[], passed to AddItemActivity
//
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                        byte[] byteArray = stream.toByteArray();
//
//                        Intent intent = new Intent(this, AddItemActivity.class);
//                        intent.putExtra("image", byteArray);
//                        startActivity(intent);
//                    }
//                }
//                break;

            case 2:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                        //image = profileImage(bp);   // image in byte[], passed to AddItemActivity

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray2 = stream.toByteArray();

                        Intent intent2 = new Intent(getActivity(), ItemActivity.class);
                        intent2.putExtra("image", byteArray2);
                        intent2.putExtra("add_show_edit", "add");
                        startActivity(intent2);

                    }
                    break;
                }
        }
    }


    //Convert and resize our image to 400dp for faster uploading our images to DB
    public Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

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
            return BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }


    //Retrieve data from the database and set to the list view
    private void showRecords(String kind, String category){  //change to gridView
        ArrayList<Item> itemArray = new ArrayList<Item>();
        List<Item> items = db.getAllItems(kind, category);
        for (Item item : items) {
            itemArray.add(item);
        }

        gridView = (GridView) getActivity().findViewById(R.id.gridview);

        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, itemArray);//an arraylist of );
        gridView.setAdapter(gridAdapter);

        // set click listener for each image in the grid view, will open ShowItemFragment

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                // pass the item id as an intent to ItemActivity
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("item_id", item.getID());
                intent.putExtra("add_show_edit", "show");
                startActivity(intent);
            }
        });
    }

}

