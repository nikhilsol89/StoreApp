package com.example.nikhil.storeapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class InventoryTable {

    public static String createInventoryTable() {
        return "CREATE TABLE IF NOT EXISTS " + DatabaseMetaData.TABLE_INVENTORY + " ( " +
                DatabaseMetaData.INVENTORY_ITEM_ID + " integre primary key autoincrement," +
                DatabaseMetaData.INVENTORY_NAME + " text," +
                DatabaseMetaData.INVENTORY_CATEGORY + " text," +
                DatabaseMetaData.INVENTORY_PRICE + " text," +
                DatabaseMetaData.INVENTORY_IMAGE + " blob," +
                DatabaseMetaData.INVENTORY_BRAND + " text," +
                DatabaseMetaData.INVENTORY_DESCRIPTION + " text" +
                ")";
    }

    public static String dropInventoryTable() {
        return "DROP TABLE IF EXISTS " + DatabaseMetaData.TABLE_INVENTORY;
    }

    public static long insertIntoInventoryTable(SQLiteDatabase database, ContentValues contentValues) {
        return database.insert(DatabaseMetaData.TABLE_INVENTORY, null, contentValues);
    }

    public static Cursor readFromInventoryTable(SQLiteDatabase database) {
        return database.query(DatabaseMetaData.TABLE_INVENTORY, null, null, null, null, null, null);
    }

    public static Cursor readFromInventoryTable(SQLiteDatabase database, String whereClause, int itemId) {
        return database.query(DatabaseMetaData.TABLE_INVENTORY, null, whereClause, new String[]{itemId + ""}, null, null, null);
    }
}
