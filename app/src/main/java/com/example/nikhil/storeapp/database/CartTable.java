package com.example.nikhil.storeapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class CartTable {

    public static String createCartTable(){
        return "CREATE TABLE IF NOT EXISTS "+DatabaseMetaData.TABLE_CART+" ( "+
                DatabaseMetaData.CART_ITEM_ID+" text,"+
                DatabaseMetaData.CART_QUANTITY+" text" +
                ")";
    }

    public static String dropCartTable(){
        return "DROP TABLE IF EXISTS "+DatabaseMetaData.TABLE_CART;
    }

    public static long insertIntoCartTable(SQLiteDatabase database, ContentValues contentValues){
        return database.insert(DatabaseMetaData.TABLE_CART,null,contentValues);
    }

    public static void deleteFromCartTable(SQLiteDatabase database, String itemID){
        database.delete(DatabaseMetaData.TABLE_CART,DatabaseMetaData.CART_ITEM_ID+" =? ",new String[]{itemID});
    }

    public static Cursor readFromCartTable(SQLiteDatabase database){
        return database.query(DatabaseMetaData.TABLE_CART,null,null,null,null,null,null);
    }
    public static Cursor readFromCartTable(SQLiteDatabase database,String whereClause,String values){
        return database.query(DatabaseMetaData.TABLE_CART,null,whereClause,new String[]{values},null,null,null);
    }

    public static int updateCartTable(SQLiteDatabase database, ContentValues values, String itemId){
        return database.update(DatabaseMetaData.TABLE_CART,values,DatabaseMetaData.CART_ITEM_ID +" = ? ",new String[]{itemId});
    }
}
