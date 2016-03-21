package com.example.nikhil.storeapp.database;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class DatabaseMetaData {

    /*table inventory stores item details*/
    public static final String TABLE_INVENTORY = "inventoryTable";

    public static final String INVENTORY_ITEM_ID = "item_id";
    public static final String INVENTORY_NAME = "name";
    public static final String INVENTORY_CATEGORY = "category";
    public static final String INVENTORY_PRICE = "price";
    public static final String INVENTORY_IMAGE = "image";
    public static final String INVENTORY_BRAND = "brand";
    public static final String INVENTORY_DESCRIPTION = "description";

    /*table cart stores the item added to cart*/
    public static final String TABLE_CART = "cartTable";
    public static final String CART_ITEM_ID = "item_id"; //refers to the primary key item_id of inventory table
    public static final String CART_QUANTITY = "quantity";
}
