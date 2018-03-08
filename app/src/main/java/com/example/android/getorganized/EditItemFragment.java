package com.example.android.getorganized;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/*
    EditItemFragment: to edit item
    shows after click "edit" button in ShowItemFragment
 */

public class EditItemFragment extends Fragment {

    private DatabaseHandler db;
    private ImageView image_iv;
    private EditText price_et;
    private EditText kind_spinner, category_spinner, season_spinner;  // ***** some will be spinner *****
    private Button save_btn, delete_btn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);


        // item_id is passed by intent from ShowItemFragment
        // get item's attributes in the database
        // set the spinners/edittexts to show them as the default value

        Intent intent = getActivity().getIntent();
        final Integer item_id = intent.getIntExtra("item_id", -1);

        db = new DatabaseHandler(getActivity());
        final Item item = db.getOneItem(item_id);

        image_iv = (ImageView)getActivity().findViewById(R.id.iv_showimg);
        kind_spinner = (EditText) getActivity().findViewById(R.id.editkind);  // ***** change to spinner*****
        category_spinner = (EditText) getActivity().findViewById(R.id.editcategory);// ***** change to spinner*****
        price_et = (EditText) getActivity().findViewById(R.id.editprice);
        season_spinner = (EditText) getActivity().findViewById(R.id.editseason);// ***** change to spinner*****


        byte[] b = item.getImage();
        image_iv.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
        kind_spinner.setText(item.getKind());  // ***** change to spinner*****
        category_spinner.setText(item.getCategory());  // ***** change to spinner*****
        price_et.setText(String.valueOf(item.getPrice()));
        season_spinner.setText(item.getSeason());  // ***** change to spinner*****


        // for "SAVE" button, update the database, then go back to ClosetFragment

        save_btn = (Button)getActivity().findViewById(R.id.btn_editsave);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.updateItem(item, item_id);

                Intent intent1 = new Intent(getActivity(), NavdrawerActivity.class);
                startActivity(intent1);
            }
        });


        // for "DELETE" button, show AlertDialog, yes -> delete in the database, then go back to ClosetFragment
        delete_btn = (Button)getActivity().findViewById(R.id.btn_editdelete);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete item")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteItem(item_id);
                                Intent intent2 = new Intent(getActivity(), NavdrawerActivity.class);
                                startActivity(intent2);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });

    }

}
