package com.pedrolopesme.android.bakingapp.integration.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract;
import com.pedrolopesme.android.bakingapp.models.Recipe;

import static com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract.RecipeWidgetEntry;

public class RecipeWidgetDao extends BaseDao implements IRecipeWidgetDao {

    private final String LOG_TAG = this.getClass().getSimpleName();

    public RecipeWidgetDao(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public void insert(final Recipe recipe) {
        Log.i(LOG_TAG, "Inserting recipe to be used as widget");
        ContentValues values = new ContentValues();
        values.put(RecipeWidgetEntry._ID, recipe.getId());
        values.put(RecipeWidgetEntry.COLUMN_NAME, recipe.getName());
        values.put(RecipeWidgetEntry.COLUMN_IMAGE, recipe.getImage());
        values.put(RecipeWidgetEntry.COLUMN_SERVINGS, recipe.getServings());
        getContentResolver().insert(RecipeWidgetContract.URI_RECIPE_WIDGET, values);
    }

    @Override
    public void delete() {
        Log.i(LOG_TAG, "Delete recipe widgets");
        getContentResolver().delete(RecipeWidgetContract.URI_RECIPE_WIDGET, null, null);
    }

    @Override
    public Recipe getRecipeWidget() {
        Log.i(LOG_TAG, "Getting recipe widget");
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(RecipeWidgetContract.URI_RECIPE_WIDGET, null, null, null, null);

            if (cursor != null && cursor.moveToNext()) {
                final String name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeWidgetEntry.COLUMN_NAME));
                final String image = cursor.getString(cursor.getColumnIndexOrThrow(RecipeWidgetEntry.COLUMN_IMAGE));
                final int servings = cursor.getInt(cursor.getColumnIndexOrThrow(RecipeWidgetEntry.COLUMN_SERVINGS));

                Recipe recipe = new Recipe();
                recipe.setName(name);
                recipe.setImage(image);
                recipe.setServings(servings);
                return recipe;
            }

        } catch (Exception ex) {
            Log.e(LOG_TAG, "Something went wrong while retrieving recipe widget", ex);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
