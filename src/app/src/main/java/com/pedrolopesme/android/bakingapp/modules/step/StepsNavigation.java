package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipeFragment;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeActivity;

/**
 * Controls navigation between Recipes Master Navigation Fragments/Activities
 */
public class StepsNavigation {

    public static final String TAG_RECIPES_FRAGMENT = "recipesFragment";
    public static final String TAG_RECIPE_FRAGMENT = "recipeFragment";

    public enum Panels {
        ONE, TWO
    }

    private Panels mPanels;
    private Context context;
    private FragmentManager fragmentManager;

    public StepsNavigation() {
    }

    public StepsNavigation(Panels mPanel, Context context, FragmentManager fragmentManager) {
        this.mPanels = mPanel;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    /**
     * Navigate to the recipe detail according to panels state
     *
     * @param step item
     */
    public void navigate(Step step) {
//        if (mPanels.equals(Panels.ONE))
//            navigateToActivity(recipe);
//        else
//            navigateToFragment(recipe);
    }

    /**
     * Starts a new activity
     *
     * @param recipe item
     */
    private void navigateToActivity(Recipe recipe) {
        Intent recipeActivityIntent = new Intent(context, RecipeActivity.class);
        recipeActivityIntent.putExtra(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
        context.startActivity(recipeActivityIntent);
    }

    /**
     * Replace recipe fragment
     *
     * @param recipe item
     */
    private void navigateToFragment(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fl_recipe_container, recipeFragment, TAG_RECIPE_FRAGMENT)
                .commit();
    }

    public Context getContext() {
        return context;
    }
}
