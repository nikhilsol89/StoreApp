package com.example.nikhil.storeapp.controllers;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.nikhil.storeapp.Adapters.MyCartListAdapter;
import com.example.nikhil.storeapp.R;
import com.example.nikhil.storeapp.database.AssetsSQLiteOpenHelper;
import com.example.nikhil.storeapp.database.CartTable;
import com.example.nikhil.storeapp.database.DatabaseHelper;
import com.example.nikhil.storeapp.database.DatabaseMetaData;
import com.example.nikhil.storeapp.database.InventoryTable;
import com.example.nikhil.storeapp.listeners.RecyclerViewListItemClickListener;
import com.example.nikhil.storeapp.model.ItemDetailsModel;

import java.util.ArrayList;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class CartActivity extends AppCompatActivity implements RecyclerViewListItemClickListener {

    int totalPrice = 0;
    RecyclerView myCartList;
    TextView myPriceTextView;
    ArrayList<ItemDetailsModel> modelArrayList = new ArrayList<>();
    MyCartListAdapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity_main);
        findViews();

        readCartAddedData();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        myCartList.setLayoutManager(manager);

        adap = new MyCartListAdapter(this, modelArrayList, this, true);
        myCartList.setAdapter(adap);
    }

    public void findViews() {
        myCartList = (RecyclerView) findViewById(R.id.myCartListView);
        myPriceTextView = (TextView) findViewById(R.id.myTotalPriceTextView);
    }

    @Override
    public void onItemCLickedListener(int itemId, boolean toBeRemoved) {
        if (toBeRemoved) {
            CartTable.deleteFromCartTable(DatabaseHelper.getInstance(this).getWritableDatabase(), modelArrayList.get(itemId).getItemId() + "");
            readCartAddedData();
            adap.notifyDataSetChanged();
        } else {
            Intent i = new Intent(this, ItemDetailsActivity.class);
            i.putExtra("Bundle", getItemInfoFor(modelArrayList.get(itemId).getItemId(), modelArrayList.get(itemId)));
            startActivity(i);
        }
    }

    public void readCartAddedData() {
        totalPrice = 0;
        if (modelArrayList.size() > 0) modelArrayList.clear();
        try {
            Cursor c = CartTable.readFromCartTable(DatabaseHelper.getInstance(this).getWritableDatabase());
            if (c != null) {
                while (c.moveToNext()) {
                    ItemDetailsModel model = new ItemDetailsModel();
                    int modelId = c.getInt(c.getColumnIndex(DatabaseMetaData.CART_ITEM_ID));
                    getItemInfoFor(modelId, model);
                    model.setQuantity(c.getInt(c.getColumnIndex(DatabaseMetaData.CART_QUANTITY)));
                    totalPrice = totalPrice + (model.getQuantity() * model.getItemPrice());
                    modelArrayList.add(model);
                }
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        myPriceTextView.setText("Total Price for Items added in cart :" + totalPrice);
    }

    public ItemDetailsModel getItemInfoFor(int itemId, ItemDetailsModel model) {

        AssetsSQLiteOpenHelper assetsSQLiteOpenHelper = AssetsSQLiteOpenHelper.getInstance(this);
        try {
            Cursor c = InventoryTable.readFromInventoryTable(assetsSQLiteOpenHelper.getSqLiteDatabase(), DatabaseMetaData.INVENTORY_ITEM_ID + " =? ", itemId);
            if (c != null) {
                while (c.moveToNext()) {
                    model.setItemName(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_NAME)));
                    model.setImageByteArray(c.getBlob(c.getColumnIndex(DatabaseMetaData.INVENTORY_IMAGE)));
                    model.setItemCategory(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_CATEGORY)));
                    model.setItemPrice(Integer.parseInt(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_PRICE))));
                    model.setItemDescription(c.getString(c.getColumnIndex(DatabaseMetaData.INVENTORY_DESCRIPTION)));
                    model.setItemId(c.getInt(c.getColumnIndex(DatabaseMetaData.INVENTORY_ITEM_ID)));
                }
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
