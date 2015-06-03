package com.banane.dbtest2.activity;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.banane.dbtest2.R;
import com.banane.dbtest2.common.CursorLoader;
import com.banane.dbtest2.common.DatabaseHelper;
import com.banane.dbtest2.common.Utils;
import com.banane.dbtest2.common.DBContentObserver;
import com.banane.dbtest2.model.Word;


public class MainActivity  extends Activity implements LoaderCallbacks<Cursor> {

    private ListView mListView;
    private DatabaseHelper dbHelper;
    private SimpleCursorAdapter cursorAdapter;

    private int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Utils.TAG,getClass().getSimpleName() + " ***** in onCreate()");
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);

        dbHelper = DatabaseHelper.getInstance(this);

        cursorAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.listview_item_layout,
                null,
                    new String[] { DatabaseHelper.KEY_LANGUAGE_CODE, DatabaseHelper.KEY_LOCAL_WORD, DatabaseHelper.KEY_FOREIGN_WORD},
                new int[] { R.id.language_code , R.id.local_word, R.id.foreign_word }, 0);

        mListView.setAdapter(cursorAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.e(Utils.TAG,getClass().getSimpleName() + ":::: onCreateLoader");

        CursorLoader cursorLoader = new CursorLoader(this, dbHelper);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        Log.e(Utils.TAG,getClass().getSimpleName() + ":::: onLoadFinished");

        cursorAdapter.swapCursor(c);

        /**
         * Registering content observer for this cursor, When this cursor value will be change
         * This will notify our loader to reload its data*/
        DBContentObserver contentObserver = new DBContentObserver(new Handler(), loader);
        c.registerContentObserver(contentObserver);
        c.setNotificationUri(getContentResolver(), Word.CONTENT_URI);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.e(Utils.TAG,getClass().getSimpleName() +  ":::: onLoaderReset");

        cursorAdapter.swapCursor(null);
    }


    @Override
    protected void onStart(){
        super.onStart();
        Log.d(Utils.TAG, getClass().getSimpleName() + " on start");
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

    }


    @Override
    protected void onPause(){
        super.onPause();
//        getContentResolver().unregisterContentObserver(contentObserver);
    }
}
