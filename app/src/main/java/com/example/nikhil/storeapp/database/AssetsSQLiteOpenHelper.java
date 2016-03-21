package com.example.nikhil.storeapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Nikhil on 3/20/2016.
 */
public class AssetsSQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    private static String DB_NAME = "store.sqlite";
    private static String DB_PATH = "/data/data/com.example.nikhil.storeapp/databases/";
    public SQLiteDatabase sqLiteDatabase;
    public static AssetsSQLiteOpenHelper assetsSQLiteOpenHelper;

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public static AssetsSQLiteOpenHelper getInstance(Context context) {
        if (assetsSQLiteOpenHelper != null) {
            return assetsSQLiteOpenHelper;
        } else {
            assetsSQLiteOpenHelper = new AssetsSQLiteOpenHelper(context);
            return assetsSQLiteOpenHelper;
        }
    }

    private AssetsSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            try {
                createdatabase();
                opendatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (dbexist) {
        } else {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        String mypath = DB_PATH + DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
