package com.example.android.getorganized;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "itemsManager";  //"contactsManager"

    // Contacts table name
    private static final String TABLE_ITEMS = "items";  //TABLE_CONTACTS="contacts"

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    //private static final String KEY_FNAME = "fname";
    private static final String KEY_IMAGE = "image";  //KEY_POTO, "poto"
    private static final String KEY_KIND = "kind";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SEASON = "season";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ITEMS + "("
                + KEY_ID +" UNSIGNED NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + KEY_IMAGE  + " BLOB, "
                + KEY_KIND + " TEXT, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_PRICE + " UNSIGNED, "
                + KEY_SEASON + " TEXT);");   // need to complete this
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table items
    public void addItem(Item item){   //addContacts(Item contact)
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_KIND, item.getKind());   // add these functions in Item.java
        values.put(KEY_CATEGORY, item.getCategory());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_SEASON, item.getSeason());

        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }


    // Getting All Items
    public List<Item> getAllItems() {List<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                //item.setFName(cursor.getString(1));
                item.setImage(cursor.getBlob(1));
                item.setKind(cursor.getString(2));
                item.setCategory(cursor.getString(3));
                item.setPrice(Integer.parseInt(cursor.getString(4)));   // price: Int
                item.setSeason(cursor.getString(5));   // need to be completed

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return itemList;
    }


    // Updating single item
    public int updateItem(Item item, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_FNAME, item.getFName());
        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_KIND, item.getKind());
        values.put(KEY_CATEGORY, item.getCategory());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_SEASON, item.getSeason());

        // updating row
        return db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting single contactItem
    public void deleteItem(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }


    public Cursor dbViewData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEMS, null);
        return cursor;
    }

}