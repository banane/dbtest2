package com.banane.dbtest2.common;

import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;


/**
 * Created by banane on 6/2/15.
 */
public class DBContentObserver extends ContentObserver {


    private Loader<Cursor> loader;

    public DBContentObserver(Handler handler, Loader<Cursor> loader) {
        super(handler);
        Log.e(Utils.TAG, getClass().getSimpleName() + " in constructor");

        this.loader = loader;
    }

    @Override
    public boolean deliverSelfNotifications() {
        Log.e(Utils.TAG, getClass().getSimpleName() + " deliverSelfNotifications");
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.e(Utils.TAG, getClass().getSimpleName() + " onChange");

        if (null != loader) {
            loader.onContentChanged();
        }
        super.onChange(selfChange);
    }
}
