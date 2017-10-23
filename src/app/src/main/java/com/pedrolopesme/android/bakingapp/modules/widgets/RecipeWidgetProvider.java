package com.pedrolopesme.android.bakingapp.modules.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.formatters.IngredientsFormatter;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesActivity;

/**
 * Implementation of Ingredient Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String LOG_TAG = RecipeWidgetProvider.class.getSimpleName();


    public static void updateRecipeWidgets(Context context, AppWidgetManager widgetManager, Recipe recipe, int[] widgetIds) {
        Log.i(LOG_TAG, "Running Recipe Widget Provider updateRecipeWidgets ");
        for (int widgetId : widgetIds) {
            if (recipe != null)
                updateRecipeWidget(context, widgetManager, recipe, widgetId);
            else
                updateRecipeNotFoundWidget(context, widgetManager, widgetId);
        }
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          Recipe recipe, int appWidgetId) {

        Log.i(LOG_TAG, "Running Recipe Widget Provider Update App Widget");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_provider);

        views.setTextViewText(R.id.tv_recipe_ingredients, new IngredientsFormatter(recipe.getIngredients()).simpleListFormat());

        // Setting up click event
        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.rl_recipe_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeNotFoundWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Log.i(LOG_TAG, "Running Recipe Widget Provider Update App Widget");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe_not_found_provider);

        // Setting up click event
        Intent intent = new Intent(context, RecipesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.rl_recipe_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.i(LOG_TAG, "Updating Recipe Recipe Widgets");
        RecipeWidgetService.startActionUpdateRecipe(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

