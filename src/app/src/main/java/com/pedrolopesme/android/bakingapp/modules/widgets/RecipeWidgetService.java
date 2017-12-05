package com.pedrolopesme.android.bakingapp.modules.widgets;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.integration.dao.RecipeWidgetDao;
import com.pedrolopesme.android.bakingapp.models.Recipe;

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
        Log.i(LOG_TAG, "Running start action update recipe");
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(LOG_TAG, "Running on handle intent");
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
        Log.i(LOG_TAG, "Handling update recipe action");
        final Cursor cursor = null;
        try {
            RecipeWidgetDao widgetDao = new RecipeWidgetDao(getContentResolver());
            Recipe recipe = widgetDao.getRecipeWidget();
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
