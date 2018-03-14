package com.example.android.getorganized;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class viewdate extends AppCompatActivity implements View.OnClickListener{

    private String main;
    private DatabaseHandler databaseHandler;
    GridView gridView;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdate);

        add=(Button)findViewById(R.id.add_cloth);
        add.setOnClickListener(this);
        Bundle b= getIntent().getExtras();
        main=b.getString("selected");

        databaseHandler= new DatabaseHandler(this);
        ArrayList<Item> itemArrayList2= new ArrayList<Item>();
        List<Item> items =databaseHandler.getAllItem(main);

        for(Item item: items)
        {
            itemArrayList2.add(item);
        }

        gridView=(GridView)this.findViewById(R.id.viewdate_gridview);
        GridViewAdapter adapter = new GridViewAdapter(this,R.layout.grid_item_layout,itemArrayList2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialogbox= new AlertDialog.Builder(new ContextThemeWrapper(viewdate.this,R.style.dailog));
                dialogbox.setTitle(R.string.delete);
                dialogbox.setMessage(R.string.doyouwanttoaddthisitemforthisdate);
                dialogbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Item item= (Item)parent.getItemAtPosition(position);
                        Integer i= item.getID();
                        databaseHandler.deletedateitem(i,main);
                        finish();
                        startActivity(getIntent());
                    }
                });
                dialogbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogbox.show();
            }
        });
    }

    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.add_cloth:

                Intent intent= new Intent(this,add_cloth_calendar.class);
                intent.putExtra("selectdate",main);
                startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 1:
                finish();
                startActivity(getIntent());
        }
    }
}
