package com.chrysalis.database;

import android.app.DownloadManager;
import com.chrysalis.database.provider.MyContentProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.ContentResolver;

import java.util.InputMismatchException;

/**
 * Created by ~chrysalis~ on 11/15/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper{

    private ContentResolver myCR;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    public static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";

    public MyDBHandler (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        myCR = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_PRODUCTNAME + " TEXT," + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct (Product product) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_QUANTITY, product.getQuantity());

        myCR.insert(MyContentProvider.CONTENT_URI, values);
        /*SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();*/
    }

    public Product findProduct (String productname) {
        String[] projection = {COLUMN_ID, COLUMN_PRODUCTNAME, COLUMN_QUANTITY};
        String selection = "productname = \"" + productname + "\"";

        Cursor cursor = myCR.query(MyContentProvider.CONTENT_URI, projection, selection, null, null);
        Product product = new Product();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setId(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        }
        else {
            product = null;
        }

        return product;

        /*String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME +
                " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            product.setId(Integer.parseInt(cursor.getString(0)));
            product.setProductName(cursor.getString(1));
            product.setQuantity(Integer.parseInt(cursor.getString(2)));
            cursor.close();
        }
        else {
            product = null;
        }

        db.close();
        return product;*/
    }

    public boolean deleteProduct (String productname) {
        boolean result = false;

        String selection = "productname = \"" + productname + "\"";
        int rowsDeleted = myCR.delete(MyContentProvider.CONTENT_URI, selection, null);

        if (rowsDeleted > 0) {
            result = true;
        }

        return result;


        /*boolean result = false;

        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME +
                " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = new Product();

        if (cursor.moveToFirst()) {
            product.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[] {String .valueOf(product.getId())});
            cursor.close();
            result = true;
        }

        db.close();
        return result;*/
    }
}
