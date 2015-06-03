package com.banane.dbtest2.common;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.banane.dbtest2.common.DatabaseHelper;
import com.banane.dbtest2.common.Utils;
import com.banane.dbtest2.model.Word;

/**
 * Created by banane on 6/3/15.
 */
public class CursorLoader extends AsyncTaskLoader<Cursor> {

    private Context context;
    private DatabaseHelper dbHelper;

    public CursorLoader(Context context, DatabaseHelper dbHelper) {
        super(context);
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onStartLoading() {
        Log.e(Utils.TAG,getClass().getSimpleName() + "onStartLoading");

        super.onStartLoading();
    }

    @Override
    public Cursor loadInBackground() {
        Log.e(Utils.TAG,getClass().getSimpleName() + ":::: loadInBackground");

        //Cursor c = dbHelper.getAllWords();
        Cursor c = context.getContentResolver().query(Word.CONTENT_URI, dbHelper.ALL_COLUMN_NAMES, null, null, dbHelper.KEY_LOCAL_WORD + " asc ", null);
        return c;
    }

    @Override
    public void deliverResult(Cursor data) {
        Log.e(Utils.TAG,getClass().getSimpleName() + ":::: deliverResult");

        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        Log.e(Utils.TAG,getClass().getSimpleName() + ":::: onStopLoading");

        super.onStopLoading();
    }
}
