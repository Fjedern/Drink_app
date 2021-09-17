package com.example.drink_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String INGREDIENT_TABLE = "INGREDIENT_TABLE";
    //public static final String COLUMN_INGREDIENT_ID = "INGREDIENT_ID";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";

    public DatabaseHandler(@Nullable Context context){
        super(context, "projectAppDevelopment", null, 1);
    }

    //skapar database om den inte redan existerar
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + INGREDIENT_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INGREDIENT_NAME + " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Not used

    }

    //TODO add to database
    //TODO delete from database
    //TODO update database
}
