package com.banane.dbtest2.model;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.banane.dbtest2.common.DatabaseHelper;
import com.banane.dbtest2.common.Utils;

/**
 * Created by banane on 6/2/15.
 */
public class Word extends ContentProvider {
    public static final String PROVIDER_NAME = "com.banane.dbtest2.word";

    /** A uri to do operations on cust_master table. A content provider is identified by its uri */
    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME + "/words" );

    /** Constants to identify the requested operation */
    private static final int WORDS = 1;

    private static final UriMatcher uriMatcher ;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            uriMatcher.addURI(PROVIDER_NAME, "words", WORDS);
    }

    /** This content provider does the database operations by this object */
    DatabaseHelper databaseHelper;

    /** A callback method which is invoked when the content provider is starting up */
    @Override
    public boolean onCreate() {
        databaseHelper = DatabaseHelper.getInstance(getContext());
        Log.d(Utils.TAG, getClass().getSimpleName() + " onCreate()");
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /** A callback method which is by the default content uri */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if(uriMatcher.match(uri)==WORDS){
            Log.d(Utils.TAG, getClass().getSimpleName() + " query match uri:" + uri);

            return databaseHelper.getAllWords();

        }else{
            Log.d(Utils.TAG, getClass().getSimpleName() + " query no match uri:"+ uri);
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(Utils.TAG, getClass().getSimpleName() + " in cr delete uri:"+ uri);
        databaseHelper.delete(uri, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(uriMatcher.match(uri)==WORDS) {

            databaseHelper.insert(values);
            getContext().getContentResolver().notifyChange(uri, null);
            Log.d(Utils.TAG,getClass().getSimpleName() + " insert " + uri);

        }
        return uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }




}

