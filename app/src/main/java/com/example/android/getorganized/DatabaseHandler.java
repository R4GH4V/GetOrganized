package com.example.android.getorganized;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "DatabaseHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "itemsManager";  //"contactsManager"

    // Table name
    private static final String TABLE_ITEMS = "items";  //TABLE_CONTACTS="contacts"
    private static final String Table_user="userdata";
    private static final String Table_worn="worn";
    // Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_KIND = "kind";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SEASON = "season";

    // Table Userdata
    private static final String Email ="Email";
    private static final String Gender="Gender";

    // Table worn
    private static final String key ="Id";
    private static final String Date="Date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS + "("
                + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_IMAGE  + " BLOB, "
                + KEY_KIND + " TEXT, "
                + KEY_CATEGORY + " TEXT, "
                + KEY_PRICE + " UNSIGNED, "
                + KEY_SEASON + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_worn +"(" + key +" INTEGER , "+Date +" TEXT);");
    }


    public void userdata()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL(" CREATE TABLE IF NOT EXISTS " + Table_user + "("+ Email +" TEXT NOT NULL, "+ Gender +" TEXT NOT NULL);");
        db.close();
    }

    public boolean userdata_check()
    {
        String check= "SELECT * FROM "+ Table_user +";";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(check,null);

        if(cursor.moveToFirst()==false) return false;
        else return true;
    }

    public void adddatainworn(Integer id,String dat)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(key,id);
        values.put(Date,dat);
        db.insert(Table_worn,null,values);
        db.close();
    }
    public void adduserdata(String mail, String gen)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(Email,mail);
        values.put(Gender,gen);
        db.insert(Table_user,null,values);
        db.close();
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + Table_worn);
        db.execSQL("DROP TABLE IF EXISTS " + Table_user);
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
        db.close();
        return (result != -1);
    }


    // Getting All Items, for showing images in the gridview
    public List<Item> getAllItems(String kind, String category)
    {
        List<Item> itemList = new ArrayList<Item>();
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

    public List<Item> getAllItem(String date)
    {
        List<Item> list_items= new ArrayList<Item>();
        SQLiteDatabase db= this.getWritableDatabase();

        String wquery=" Select "+key+" from "+ Table_worn+ " WHERE "+Date+" = '"+date+"';";
        Cursor wcursor= db.rawQuery(wquery,null);

       if (wcursor != null && wcursor.moveToFirst()) {

           Integer id;
            do {
                id= Integer.parseInt(wcursor.getString(0));
                Item item =getOneItem(id);
                list_items.add(item);

            }while (wcursor.moveToNext());
        }
        db.close();
        return list_items;
    }
    public int test1(String date)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        String wquery=" Select "+key+" from "+ Table_worn+ " WHERE "+Date+" = '"+date+"';";
        Cursor wcursor= db.rawQuery(wquery,null);

        int r = wcursor.getCount();
        return r;
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

    public int getCount(String kind) {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery;
        if (kind.equals("All")) {  // all rows
            countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        } else {  // count of each kind
            countQuery = "SELECT  * FROM " + TABLE_ITEMS + " WHERE " + KEY_KIND + " = '" + kind + "'";
        }
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Get sum of columns of one kind
    public int getSum(String kind) {
        SQLiteDatabase db = this.getReadableDatabase();

        String sumQuery;
        if (kind.equals("All")) {
            sumQuery = "SELECT SUM(" + KEY_PRICE + ") FROM " + TABLE_ITEMS;
        } else {
            sumQuery = "SELECT SUM(" + KEY_PRICE + ") FROM " + TABLE_ITEMS +
                    " WHERE " + KEY_KIND + " = '" + kind + "'";
        }
        Cursor cursor = db.rawQuery(sumQuery, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public boolean deletedateitem(Integer id,String date)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        String query="Delete FROM "+Table_worn+" WHERE " + key+" = "+id + " AND "+Date+" = '"+date+"';";
        try {
            db.execSQL(query);
        }

        catch (SQLException s)
        {
            return false;
        }
        db.close();
        return true;
    }

    public int checking()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+Table_worn+";",null);
        int count =cursor.getCount();
        db.close();
        return count;
    }

    public boolean checking(String date)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ Table_worn+" where "+Date+" = '"+date+"';",null);
        int count = cursor.getCount();
        db.close();
        if(count>0) return true;
        else return false;
    }

    public boolean clothmatch(Integer id, String date)
    {
        SQLiteDatabase db= this.getWritableDatabase();

        String query = "Select * from "+ Table_worn+" where "+key+" = "+id+" AND "+Date+" = '"+date+"';";
        Cursor cursor= db.rawQuery(query,null);

        int a= cursor.getCount();
        if(a==1)return true;
        else return false;
    }
}