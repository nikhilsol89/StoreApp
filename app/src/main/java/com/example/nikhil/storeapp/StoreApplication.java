package com.example.nikhil.storeapp;

import android.app.Application;

import com.example.nikhil.storeapp.database.AssetsSQLiteOpenHelper;
import com.example.nikhil.storeapp.database.DatabaseHelper;

import java.io.IOException;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class StoreApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.getInstance(this);
        AssetsSQLiteOpenHelper.getInstance(this);
    }

}
