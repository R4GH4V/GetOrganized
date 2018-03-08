package com.example.android.getorganized;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "DatabaseHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "itemsManager";  //"contactsManager"

    // Table name
    private static final String TABLE_ITEMS = "items";  //TABLE_CONTACTS="contacts"

    // Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_IMAGE = "image";
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
                + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_IMAGE  + " BLOB, "
                + KEY_KIND + " TEXT, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_PRICE + " UNSIGNED, "
                + KEY_SEASON + " TEXT);");
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Insert values to the table items
    // return a boolean, true -> successfully inserted, false -> not inserted
    public boolean addItem(Item item){   //addContacts(Item contact)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        Log.d(DEBUG_TAG, "season before adding: " + item.getSeason());

        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_KIND, item.getKind());
        values.put(KEY_CATEGORY, item.getCategory());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_SEASON, item.getSeason());


        long result = db.insert(TABLE_ITEMS, null, values);
        return (result != -1);
        //db.close();
    }


    // Getting All Items, for showing images in the gridview
    public List<Item> getAllItems(String kind, String category) {List<Item> itemList = new ArrayList<Item>();
        Log.d(DEBUG_TAG, "kind, category: " + kind + category);

        String selectQuery;
        if (kind.equals("All")) { // Select All Query
            selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
        } else if (category.equals("All")){
            selectQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE " + KEY_KIND + " = '" + kind + "'";
        } else {
            selectQuery = "SELECT  * FROM " + TABLE_ITEMS +
                    " WHERE " + KEY_CATEGORY + " = '" + category + "'";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setImage(cursor.getBlob(1));
                item.setKind(cursor.getString(2));
                item.setCategory(cursor.getString(3));
                item.setPrice(Integer.parseInt(cursor.getString(4)));
                item.setSeason(cursor.getString(5));

                // Adding item to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        return itemList;
    }


    // Updating single item
    public boolean updateItem(Item item, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d(DEBUG_TAG, "kind before update: " + item.getKind());

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, item.getImage());
        values.put(KEY_KIND, item.getKind());
        values.put(KEY_CATEGORY, item.getCategory());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_SEASON, item.getSeason());

        // updating row
        db.update(TABLE_ITEMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

        Log.d(DEBUG_TAG, "kind after update: " + item.getKind());

        return true;
    }


    // Deleting single contactItem
    public void deleteItem(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?", new String[] { String.valueOf(Id) });
        db.close();
    }


    // Viewing all data
    public Cursor dbViewData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ITEMS, null);
        return cursor;
    }


    // Retrieve one Item according to the unique id
    public Item getOneItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ITEMS,
                new String[] {KEY_ID, KEY_IMAGE, KEY_KIND, KEY_CATEGORY, KEY_PRICE, KEY_SEASON},
                KEY_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        Item item = new Item();

        if (cursor != null && cursor.moveToFirst()) {

            item.setID(Integer.parseInt(cursor.getString(0)));
            item.setImage(cursor.getBlob(1));
            item.setKind(cursor.getString(2));
            item.setCategory(cursor.getString(3));
            item.setPrice(Integer.parseInt(cursor.getString(4)));
            item.setSeason(cursor.getString(5));

            cursor.close();
        }

        return item;
    }
}