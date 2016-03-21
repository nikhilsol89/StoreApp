package com.example.nikhil.storeapp.controllers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.storeapp.R;
import com.example.nikhil.storeapp.database.AssetsSQLiteOpenHelper;
import com.example.nikhil.storeapp.database.CartTable;
import com.example.nikhil.storeapp.database.DatabaseHelper;
import com.example.nikhil.storeapp.database.DatabaseMetaData;
import com.example.nikhil.storeapp.model.ItemDetailsModel;

import java.io.ByteArrayInputStream;


/**
 * Created by Nikhil on 3/20/2016.
 */
public class ItemDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cellItemImage;
    TextView cellItemName, cellItemPrice, cellItemCategory, cellItemDescription;
    Button addToCartButton, navigateToCartButton;
    ItemDetailsModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_item_details_view);
        findViews();
        Bundle b = getIntent().getExtras();
        model = (ItemDetailsModel) b.get("Bundle");
        cellItemName.setText(model.getItemName());
        cellItemPrice.setText(model.getItemPrice()+"");
        cellItemDescription.setText(model.getItemDescription());
        cellItemCategory.setText(model.getItemCategory());

        ByteArrayInputStream imageStream = new ByteArrayInputStream(model.getImageByteArray());
        cellItemImage.setImageBitmap(BitmapFactory.decodeStream(imageStream));

        setOnClickListeners();
    }

    public void findViews() {
        cellItemName = (TextView) findViewById(R.id.itemNameTextView);
        cellItemPrice = (TextView) findViewById(R.id.itemPriceTextView);
        cellItemCategory = (TextView) findViewById(R.id.itemCategoryTextView);
        cellItemDescription = (TextView) findViewById(R.id.myCartItemDescriptionTextView);
        cellItemImage = (ImageView) findViewById(R.id.cartItemImageview);
        addToCartButton = (Button) findViewById(R.id.AddtoCartButton);
        navigateToCartButton = (Button) findViewById(R.id.goToCart);
    }

    public void setOnClickListeners() {
        addToCartButton.setOnClickListener(this);
        navigateToCartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.AddtoCartButton) {
            ContentValues values = new ContentValues();
            values.put(DatabaseMetaData.CART_ITEM_ID,model.getItemId());

            Cursor c = CartTable.readFromCartTable(DatabaseHelper.getInstance(this).getWritableDatabase(),DatabaseMetaData.CART_ITEM_ID+" =? ",model.getItemId()+"");
            int size = -1;
            if(c!=null && c.getCount()==0) {
                size =1;
                values.put(DatabaseMetaData.CART_QUANTITY,size);
                CartTable.insertIntoCartTable(DatabaseHelper.getInstance(this).getWritableDatabase(),values);
            }else if(c!=null && c.getCount()>0) {
                size=c.getCount()+1;
                values.put(DatabaseMetaData.CART_QUANTITY,size);
                CartTable.updateCartTable(DatabaseHelper.getInstance(this).getWritableDatabase(),values,model.getItemId()+"");
            }
            addToCartButton.setText("Succesfully added");
            addToCartButton.setClickable(false);
        } else if (view.getId() == R.id.goToCart) {
            Intent i = new Intent(this,CartActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
