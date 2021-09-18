package com.example.drink_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String INGREDIENT_TABLE = "INGREDIENT_TABLE";
    //public static final String COLUMN_INGREDIENT_ID = "INGREDIENT_ID";
    public static final String COLUMN_INGREDIENT_NAME = "INGREDIENT_NAME";

    public DatabaseHandler(@Nullable Context context){
        super(context, "ingredientsDatabase.db", null, 1);
    }

    //skapar database om den inte redan existerar
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =
                "CREATE TABLE " + INGREDIENT_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_INGREDIENT_NAME + " TEXT )";

        sqLiteDatabase.execSQL(createTableStatement);
        System.out.println("i have created database");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Not used

    }

    //get all items from ingredient database and display in Recycler View
    //move to a repository??
    public List<Ingredient> viewAll (){

        List<Ingredient> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + INGREDIENT_TABLE;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(queryString, null);



        if (cursor.moveToFirst()){
            do{
                int ingredient_ID = cursor.getInt(0);
                String ingredient_name = cursor.getString(1);

                Ingredient ingredient = new Ingredient(ingredient_ID, ingredient_name);
                returnList.add(ingredient);
            }while(cursor.moveToNext());
        }else{
            //Do nothing?
        }
        cursor.close();
        database.close();
        System.out.println(returnList.size());

        return returnList;
    }

    public boolean addToDataBase(String ingredient_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_INGREDIENT_NAME, ingredient_name);

        /*cv.put(COLUMN_INGREDIENT_NAME, "Sugar");
        cv.put(COLUMN_INGREDIENT_NAME, "Vodka");
        cv.put(COLUMN_INGREDIENT_NAME, "Lime");
        cv.put(COLUMN_INGREDIENT_NAME, "Tequila");*/

        System.out.println(cv.size());

        long insert = db.insert(INGREDIENT_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        db.close();
        return true;
    }

    public void deleteIngredient(int ingredient_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + INGREDIENT_TABLE + " WHERE " +
                "ID = " + ingredient_id;
        /*Cursor cursor = db.rawQuery(queryString, null);
        db.rawQuery(queryString, null);

        return false;*/
        String idToString = String.valueOf(ingredient_id);
        db.delete(INGREDIENT_TABLE, "ID=?", new String[]{idToString});
        db.close();

    }
    //TODO delete from database
    //TODO update database
}
