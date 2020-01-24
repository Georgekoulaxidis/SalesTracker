package com.example.salestracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.salestracker.GsonParsing.GsonProduct;
import com.example.salestracker.Model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SalesTracker.db";

    // User table name
    private static final String TABLE_USER = "User";
    // Favourites table name
    private static final String TABLE_FAVS = "Favourites";

    // User Table Columns names
    private static final String COLUMN_USER_IMAGE = "user_image";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    // Favourites Table Columns names
    private static final String COLUMN_USR_ID = "user_id";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_JSON = "product_json";

    private Gson gson;

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_IMAGE + " BLOB" +")";
    // create table favourites sql query
    private String CREATE_FAVS_TABLE = "CREATE TABLE " + TABLE_FAVS + "("
            + COLUMN_USR_ID + " INTEGER," + COLUMN_PRODUCT_ID + " TEXT,"
            + COLUMN_PRODUCT_JSON + " TEXT," + "PRIMARY KEY(" + COLUMN_USR_ID + ", "
            + COLUMN_PRODUCT_ID + "), FOREIGN KEY(" + COLUMN_USR_ID + ") REFERENCES "
            + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    // drop table favourites sql query
    private String DROP_FAVS_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAVS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FAVS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_FAVS_TABLE);
        onCreate(db);
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        Log.d("dennis","User checked!");

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public User checkUser(String email, String password) {
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD, COLUMN_USER_NAME, COLUMN_USER_IMAGE};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        db.close();

        if (cursorCount > 0) {
            User user = new User();
            if(cursor.moveToNext()) {
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_IMAGE)));
            }

            cursor.close();
            return user;
        }
        return null;
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_IMAGE, user.getImage());

        // Inserting Row
        long success = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d("dennis","User added!");

        if(success != -1) {
            return true;
        }
        return false;
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_IMAGE, user.getImage());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean addProductToFavs(/*FavsProduct fp*/int userId, GsonProduct.item fp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USR_ID, userId);
        values.put(COLUMN_PRODUCT_ID, fp.getItemId().get(0));
        String json = gson.toJson(fp);
        values.put(COLUMN_PRODUCT_JSON, json);

        // Inserting Row
        long success = db.insert(TABLE_FAVS, null, values);
        db.close();

        Log.d("dennis","Favourite Product added!");

        if(success != -1) {
            return true;
        }
        return false;
    }

    public /*List<FavsProduct>*/List<GsonProduct.item> getFavs(int userId) {
        String[] columns = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_JSON};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TABLE_FAVS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        db.close();

        if (cursorCount > 0) {
            /*ArrayList<FavsProduct> favs = new ArrayList<FavsProduct>();*/
            ArrayList<GsonProduct.item> favs = new ArrayList<>();
            while(cursor.moveToNext()) {
                GsonProduct.item fp = gson.fromJson(cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_JSON)), GsonProduct.item.class);

                favs.add(fp);
            }
            cursor.close();
            return favs;
        }
        return new ArrayList<GsonProduct.item>();
    }

    public boolean deleteProductFromFavs(int userId, String productId) {
        SQLiteDatabase db = this.getWritableDatabase();

        long success = db.delete(TABLE_FAVS, COLUMN_USR_ID + " = ?" + " AND " + COLUMN_PRODUCT_ID + " = ?",
                new String[]{String.valueOf(userId), productId});
        db.close();

        if(success != -1) {
            return true;
        }
        return false;
    }

}
