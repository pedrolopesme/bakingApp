package com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeWidgetContract {

    /**
     * The authority of this content provider.
     */
    public static final String AUTHORITY = "com.pedrolopesme.android.bakingapp.provider";

    /**
     * The recipe widget path
     */
    public static final String URI_RECIPE_WIDGET_PATH = "recipe-widget";

    /**
     * The URI for the Recipe Widget Provider
     */
    public static final Uri URI_RECIPE_WIDGET = Uri.parse(
            "content://" + AUTHORITY + "/" + URI_RECIPE_WIDGET_PATH);

    /**
     * Entry Columns
     **/
    public static final class RecipeWidgetEntry implements BaseColumns {

        public static final String TABLE_NAME = "recipe_widget";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "servings";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_STEPSJSON = "stepsJson";
        public static final String COLUMN_INGREDIENTSJSON = "ingredientsJson";

    }

}
