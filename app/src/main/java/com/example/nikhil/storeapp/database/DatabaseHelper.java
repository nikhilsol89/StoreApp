package com.example.nikhil.storeapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static DatabaseHelper databaseHelperInstance;

    public SQLiteDatabase db;
    public static final String myDb = "Storedb";
    public static final String database_name = "store_database";
    public static final int DB_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, database_name, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelperInstance != null) {
            return databaseHelperInstance;
        } else {
            databaseHelperInstance = new DatabaseHelper(context);
            return databaseHelperInstance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(InventoryTable.createInventoryTable());
        db.execSQL(CartTable.createCartTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(InventoryTable.dropInventoryTable());
        db.execSQL(CartTable.dropCartTable());
        onCreate(db);
    }

    public long insertIntoDB(String tableName, ContentValues values) {
        db = getWritableDatabase();
        if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_INVENTORY)) {
            return InventoryTable.insertIntoInventoryTable(db, values);
        } else if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_CART)) {
            return CartTable.insertIntoCartTable(db, values);
        }
        return -1;
    }

    public void deleteFromDB(String tableName, String item_id) {
        db = getWritableDatabase();
        if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_CART)) {
            CartTable.deleteFromCartTable(db, item_id);
        }
    }

    public Cursor readFromDB(String tableName) {
        db = getReadableDatabase();
        Cursor c = null;
        if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_INVENTORY)) {
            c = InventoryTable.readFromInventoryTable(db);
        } else if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_CART)) {
            c = CartTable.readFromCartTable(db);
        }
        return c;
    }

    public long updateDB(String tableName, ContentValues values, String param) {
        db = getWritableDatabase();
        if (tableName.equalsIgnoreCase(DatabaseMetaData.TABLE_CART)) {
            return CartTable.updateCartTable(db, values, param);
        }
        return -1;

    }


}
