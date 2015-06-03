package com.banane.dbtest2.common;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.banane.dbtest2.model.Word;

/**
 * Created by banane on 6/2/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /** Database name */
    private static String DATABASE_NAME = "dbtest2";

    /** Version number of the database */
    private static int DATABASE_VERSION = 1;

    /** Field 1 of the table cust_master, which is the primary key */
    public static final String KEY_ROW_ID = "_id";


    /** Field 2 of the table cust_master, stores the customer code */
    public static final String KEY_LANGUAGE_CODE = "language_code";

    /** Field 3 of the table cust_master, stores the customer name */
    public static final String KEY_LOCAL_WORD = "local_word";

    /** Field 4 of the table cust_master, stores the phone number of the customer */
    public static final String KEY_FOREIGN_WORD = "foreign_word";

    /** A constant, stores the the table name */
    private static final String DATABASE_TABLE = "word_master";

    public static final String[] ALL_COLUMN_NAMES = {KEY_LANGUAGE_CODE , KEY_LOCAL_WORD , KEY_FOREIGN_WORD};


    /** An instance variable for SQLiteDatabase */
//    public static final Uri URI_TABLE_WORDS = Uri.parse("sqlite://com.banane.dbtest2/table/" + DATABASE_TABLE);

    // Database helper instance
    private static DatabaseHelper _instance;

    private Context context;

    /** Constructor */
    /**
     * @param context constructor
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * @param context
     * @return get databasehelper instance
     */
    public static DatabaseHelper getInstance(Context context) {
        if (null == _instance) {
            _instance = new DatabaseHelper(context);
        }
        return _instance;
    }

    /** This is a callback method, invoked when the method
     * getReadableDatabase() / getWritableDatabase() is called
     * provided the database does not exists
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Utils.TAG,getClass().getSimpleName() + " in onCreate");
        String sql =     "create table "+ DATABASE_TABLE + " ( "
                + KEY_ROW_ID + " integer primary key autoincrement , "
                + KEY_LANGUAGE_CODE + " text  , "
                + KEY_LOCAL_WORD + "  text  , "
                + KEY_FOREIGN_WORD + "  text  ) " ;

        db.execSQL(sql);
        String colsSepComma =  KEY_LANGUAGE_CODE + "," + KEY_LOCAL_WORD + "," + KEY_FOREIGN_WORD;

        sql = "insert into " + DATABASE_TABLE + " ( " +colsSepComma + " ) "
                + " values ( 'fr', 'hat','le chapeau' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + colsSepComma + " ) "
                + " values ( 'de', 'kitchen','Küche' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + colsSepComma + " ) "
                + " values ( 'ru', 'breakfast','завтрак' )";
        db.execSQL(sql);

        sql = "insert into " + DATABASE_TABLE + " ( " + colsSepComma + " ) "
                + " values ( 'se', 'sink' , 'diskbank' )";
        db.execSQL(sql);

    }

    public Uri insert(ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(Utils.TAG,getClass().getSimpleName() + " in insert");
        String colsSepComma =  KEY_LANGUAGE_CODE + "," + KEY_LOCAL_WORD + "," + KEY_FOREIGN_WORD;

        long newId = db.insert(DATABASE_TABLE, null, values);
        Uri uri = ContentUris.withAppendedId(Word.CONTENT_URI, newId);
        return uri;
    }

    /** Returns all the customers in the table */
    public Cursor getAllWords(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(DATABASE_TABLE, new String[] { KEY_ROW_ID,  KEY_LANGUAGE_CODE , KEY_LOCAL_WORD, KEY_FOREIGN_WORD } ,
                null, null, null, null,
                KEY_LOCAL_WORD + " asc ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
