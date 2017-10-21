package com.pedrolopesme.android.bakingapp.modules.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.contract.RecipeWidgetContract;
import com.pedrolopesme.android.bakingapp.models.Ingredient;
import com.pedrolopesme.android.bakingapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Ingredients Widget Service
 */
public class RecipeWidgetService extends IntentService {

    private static final String LOG_TAG = RecipeWidgetService.class.getSimpleName();
    public static final String ACTION_UPDATE_RECIPE_WIDGET = "com.pedrolopesme.android.bakingapp.widget.update_recipe";

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    /**
     * Updates widget recipe
     *
     * @param context reference
     */
    public static void startActionUpdateRecipe(Context context) {
        Log.d(LOG_TAG, "Running start action update recipe");
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "Running on handle intent");
        if (intent != null) {
            final String action = intent.getAction();
            if (action.equals(ACTION_UPDATE_RECIPE_WIDGET)) {
                handleUpdateRecipeAction();
            }
        }
    }

    /**
     * Handle Update Recipe Action calls
     */
    private void handleUpdateRecipeAction() {
        Log.d(LOG_TAG, "Hanlding update recipe action");
        final Cursor cursor = null;
        try {
            Recipe recipe = new Recipe();
            recipe.setName("Receita teste");

            List<Ingredient> ingredientList = new ArrayList<>();
            Ingredient i1 = new Ingredient();
            i1.setIngredient("Ingrediente 1");
            i1.setMeasure("1 litro");
            i1.setQuantity(3);
            ingredientList.add(i1);

            Ingredient i2 = new Ingredient();
            i2.setIngredient("Ingrediente 2");
            i2.setMeasure("1 litro");
            i2.setQuantity(3);
            ingredientList.add(i2);

            Ingredient i3 = new Ingredient();
            i3.setIngredient("Ingrediente 2");
            i3.setMeasure("1 litro");
            i3.setQuantity(3);
            ingredientList.add(i3);

            recipe.setIngredients(ingredientList);

            getContentResolver().query(RecipeWidgetContract.URI_RECIPE_WIDGET, null, null, null, null);
            if (cursor != null) {
                final String name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeWidgetContract.RecipeWidgetEntry.COLUMN_NAME));
                recipe.setName(name);
            }

            AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = widgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
            RecipeWidgetProvider.updateRecipeWidgets(this, widgetManager, recipe, appWidgetIds);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Something went bad while trying to update recipe action", ex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
