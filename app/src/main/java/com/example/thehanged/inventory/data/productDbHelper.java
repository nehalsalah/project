
package com.example.thehanged.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.thehanged.inventory.data.ProductContract.productEntry;

/**
 * Database helper for products app. Manages database creation and version management.
 */
public class productDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = productDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "shelter.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link productDbHelper}.
     *
     * @param context of the app
     */
    public productDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the products table
        String SQL_CREATE_productS_TABLE = "CREATE TABLE " + ProductContract.productEntry.TABLE_NAME + " ("
                + ProductContract.productEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + productEntry.COLUMN_product_NAME + " TEXT NOT NULL, "
                + productEntry.COLUMN_Product_description + " TEXT, "
                + ProductContract.productEntry.COLUMN_product_Quantity + " INTEGER NOT NULL DEFAULT 0, "
                + productEntry.COLUMN_product_image + " TEXT, "
                + ProductContract.productEntry.COLUMN_product_price + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_productS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}