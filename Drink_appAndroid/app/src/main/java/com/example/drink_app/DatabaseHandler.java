package com.example.drink_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String INGREDIENT_TABLE = "INGREDIENT_TABLE";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";

    public DatabaseHandler(@Nullable Context context){
        super(context, "ingredientsDatabase.db", null, 1);
    }

    //Creates a database if one doesn't already exist
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + INGREDIENT_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INGREDIENT_NAME +  " TEXT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Not used
    }

    //Get all items from ingredient database and display in Recycler View
    public List<Ingredient> viewAll (){

        List<Ingredient> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + INGREDIENT_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int ingredient_ID = cursor.getInt(0);
                String ingredient_name = cursor.getString(1);

                Ingredient ingredient = new Ingredient(ingredient_ID, ingredient_name, false);
                returnList.add(ingredient);
            }while(cursor.moveToNext());
        }else{
            //If cursor fails do something
        }
        cursor.close();
        database.close();

        return returnList;
    }

    public boolean addToDataBase(String ingredient_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INGREDIENT_NAME, ingredient_name);

        long insert = db.insert(INGREDIENT_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        db.close();
        return true;
    }

    public void deleteIngredient(int... ingredient_id) {    //Can handle multiple inputs
            SQLiteDatabase db = this.getWritableDatabase();
            for(int i: ingredient_id){
                db.delete(INGREDIENT_TABLE, "ID=?", new String[]{String.valueOf(i)});
            }
            db.close();
    }

    public boolean updateIngredient(String old_name, String new_name){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_INGREDIENT_NAME, new_name);

            long update = db.update(INGREDIENT_TABLE, cv, COLUMN_INGREDIENT_NAME + "=? ", new String[]{old_name});
            if(update == -1){
                return false;
            }
            db.close();
            return true;

    }

}
