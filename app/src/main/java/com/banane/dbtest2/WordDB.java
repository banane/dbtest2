package com.banane.dbtest2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by banane on 6/2/15.
 */
public class WordDB extends SQLiteOpenHelper {
    /** Database name */
    private static String DBNAME = "dbtest2";

    /** Version number of the database */
    private static int VERSION = 1;

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

    /** An instance variable for SQLiteDatabase */
    private SQLiteDatabase mDB;

    /** Constructor */
    public WordDB(Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
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

    /** Returns all the customers in the table */
    public Cursor getAllWords(){
        return mDB.query(DATABASE_TABLE, new String[] { KEY_ROW_ID,  KEY_LANGUAGE_CODE , KEY_LOCAL_WORD, KEY_FOREIGN_WORD } ,
                null, null, null, null,
                KEY_LOCAL_WORD + " asc ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
