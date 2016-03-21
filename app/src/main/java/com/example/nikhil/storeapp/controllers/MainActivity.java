package com.example.nikhil.storeapp.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikhil.storeapp.Adapters.MyCartListAdapter;
import com.example.nikhil.storeapp.R;
import com.example.nikhil.storeapp.database.AssetsSQLiteOpenHelper;
import com.example.nikhil.storeapp.database.DatabaseMetaData;
import com.example.nikhil.storeapp.database.InventoryTable;
import com.example.nikhil.storeapp.listeners.RecyclerViewListItemClickListener;
import com.example.nikhil.storeapp.model.ItemDetailsModel;

import java.util.ArrayList;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewListItemClickListener, View.OnClickListener {
    RecyclerView mainListView;
    Button cartButton;
    TextView cat1, cat2;
    ArrayList<ItemDetailsModel> list = new ArrayList<>();
    MyCartListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        findViews();
        setUpClickListeners();

        getListFromDB("Electronics");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mainListView.setLayoutManager(manager);

        adapter = new MyCartListAdapter(this, list, this, false);
        mainListView.setAdapter(adapter);

    }

    public void findViews() {
        cartButton = (Button) findViewById(R.id.cartButton);
        mainListView = (RecyclerView) findViewById(R.id.mainActivityListView);
        cat1 = (TextView) findViewById(R.id.ElectronicCatTextView);
        cat2 = (TextView) findViewById(R.id.FurnitureCatTextView);
        cat1.setTextColor(Color.parseColor("#ff0000"));
    }

    public void setUpClickListeners() {
        cat1.setOnClickListener(this);
        cat2.setOnClickListener(this);
        cartButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ElectronicCatTextView) {
            cat1.setTextColor(Color.parseColor("#ff0000"));
            cat2.setTextColor(Color.parseColor("#000000"));
            getListFromDB("Electronics");
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.FurnitureCatTextView) {
            cat2.setTextColor(Color.parseColor("#ff0000"));
            cat1.setTextColor(Color.parseColor("#000000"));
            getListFromDB("Furniture");
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.cartButton) {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onItemCLickedListener(int itemId, boolean toBeRemoved) {
        Intent i = new Intent(this, ItemDetailsActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("Bundle", getItemInfoFor(list.get(itemId).getItemId()));
        startActivity(i);
    }

    public void getListFromDB(String category) {
        if (list.size() > 0) list.clear();
        AssetsSQLiteOpenHelper assetsSQLiteOpenHelper = AssetsSQLiteOpenHelper.getInstance(this);
        try{
            Cursor c = InventoryTable.readFromInventoryTable(assetsSQLiteOpenHelper.getSqLiteDatabase());
            if(c!=null){
                while (c.moveToNext()) {
                    ItemDetailsModel model = new ItemDetailsModel();
                    model.setItemName(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_NAME)));
                    model.setImageByteArray(c.getBlob(c.getColumnIndex(DatabaseMetaData.INVENTORY_IMAGE)));
                    model.setItemCategory(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_CATEGORY)));
                    model.setItemPrice(Integer.parseInt(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_PRICE))));
                    model.setItemDescription(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_DESCRIPTION)));
                    model.setItemId(c.getInt(c.getColumnIndex(DatabaseMetaData.INVENTORY_ITEM_ID)));

                    if (model.getItemCategory().equalsIgnoreCase(category))
                        list.add(model);
                }
            }
        }catch(SQLiteException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ItemDetailsModel getItemInfoFor(int itemId) {
        AssetsSQLiteOpenHelper assetsSQLiteOpenHelper = AssetsSQLiteOpenHelper.getInstance(this);
        try{
            Cursor c = InventoryTable.readFromInventoryTable(assetsSQLiteOpenHelper.getSqLiteDatabase(), DatabaseMetaData.INVENTORY_ITEM_ID + " =? ", itemId);
            ItemDetailsModel model = null;
            while (c.moveToNext()) {
                model = new ItemDetailsModel();
                model.setItemName(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_NAME)));
                model.setImageByteArray(c.getBlob(c.getColumnIndex(DatabaseMetaData.INVENTORY_IMAGE)));
                model.setItemCategory(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_CATEGORY)));
                model.setItemPrice(Integer.parseInt(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_PRICE))));
                model.setItemDescription(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_DESCRIPTION)));
                model.setItemId(c.getInt(c.getColumnIndex(DatabaseMetaData.INVENTORY_ITEM_ID)));
            }
            return model;
        }catch(SQLiteException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
