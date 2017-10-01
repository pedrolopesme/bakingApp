package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipeFragment;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeActivity;

/**
 * Controls navigation between Recipes Master Navigation Fragments/Activities
 */
public class RecipesNavigation  {

    public static final String TAG_RECIPES_FRAGMENT = "recipesFragment";
    public static final String TAG_RECIPE_FRAGMENT = "recipeFragment";

    public enum Panels {
        ONE, TWO
    }

    private final Panels mPanels;
    private final Context context;
    private final FragmentManager fragmentManager;

    public RecipesNavigation(Panels mPanel, Context context, FragmentManager fragmentManager) {
        this.mPanels = mPanel;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    /**
     * Navigate to the recipe detail according to panels state
     *
     * @param recipe item
     */
    public void navigate(Recipe recipe) {
        if (mPanels.equals(Panels.ONE))
            navigateToActivity(recipe);
        else
            navigateToFragment(recipe);
    }

    /**
     * Starts a new activity
     *
     * @param recipe item
     */
    private void navigateToActivity(Recipe recipe) {
        context.startActivity(new Intent(context, RecipeActivity.class));
    }

    /**
     * Replace recipe fragment
     *
     * @param recipe item
     */
    private void navigateToFragment(Recipe recipe) {
        RecipeFragment recipeFragment = new RecipeFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fl_recipe_container, recipeFragment, TAG_RECIPE_FRAGMENT)
                .commit();
    }

    public Context getContext() {
        return context;
    }
}
