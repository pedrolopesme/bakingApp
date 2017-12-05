package com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract.RecipeWidgetEntry;

public class RecipeWidgetDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "recipe-widgets.db";

    private static final int DATABASE_VERSION = 1;

    public RecipeWidgetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE =
                "CREATE TABLE " + RecipeWidgetEntry.TABLE_NAME + " (" +
                        RecipeWidgetEntry._ID + " INTEGER PRIMARY KEY, " +
                        RecipeWidgetEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        RecipeWidgetEntry.COLUMN_SERVINGS + " INT, " +
                        RecipeWidgetEntry.COLUMN_IMAGE + " TEXT NULL, " +
                        RecipeWidgetEntry.COLUMN_STEPSJSON + " TEXT NULL, " +
                        RecipeWidgetEntry.COLUMN_INGREDIENTSJSON + " TEXT NULL );";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
