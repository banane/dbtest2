package com.banane.dbtest2.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.banane.dbtest2.R;
import com.banane.dbtest2.common.DatabaseHelper;
import com.banane.dbtest2.common.Utils;
import com.banane.dbtest2.model.Word;

/**
 * Created by banane on 6/2/15.
 */
public class AddActivity extends FragmentActivity {
    String[] formValues = new String[3];
    EditText localWordET, foreignWordET, languageCodeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Utils.TAG, getClass().getSimpleName() + " in onCreate()");
        setContentView(R.layout.add_activity);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.add:
                addWord(v); // should get context from view
                break;
            default:
                break;
        }
    }

    public void addWord(View v){
        Log.d(Utils.TAG, getClass().getSimpleName() + " in addWord()");

        languageCodeET = (EditText)findViewById(R.id.language_code);
        formValues[0] = languageCodeET.getText().toString();
        localWordET = (EditText)findViewById(R.id.local_word);
        formValues[1] = localWordET.getText().toString();
        foreignWordET = (EditText)findViewById(R.id.foreign_word);
        formValues[2] = foreignWordET.getText().toString();
        Boolean success = true;
        for(String value : formValues){
            if(value.length() ==0){
                success = false;
            }
        }
        if(success){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.KEY_LANGUAGE_CODE,formValues[0]);
            contentValues.put(DatabaseHelper.KEY_LOCAL_WORD, formValues[1]);
            contentValues.put(DatabaseHelper.KEY_FOREIGN_WORD, formValues[2]);
            getContentResolver().insert(Word.CONTENT_URI, contentValues);
            getContentResolver().notifyChange(Word.CONTENT_URI, null);
            showPopUp();
        } else {
            Toast.makeText(this, "Please enter in all fields.", Toast.LENGTH_LONG);
        }
    }

    public void showPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setPositiveButton("view list",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }
        );
        builder.setNegativeButton("add another",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        foreignWordET.setText("");
                        localWordET.setText("");
                        languageCodeET.setText("");
                    }
                }
        );
        builder.show();
    }

}
