package com.pedrolopesme.android.bakingapp.modules.steps;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Step;

/**
 * Controls navigation between Steps Master Navigation Fragments/Activities
 */
public class StepsNavigation {

    private final String TAG_LOG = this.getClass().getSimpleName();
//    public static final String TAG_RECIPES_FRAGMENT = "recipesFragment";
//    public static final String TAG_RECIPE_FRAGMENT = "recipeFragment";

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
        Log.d(TAG_LOG, "Navigating to step " + step);
        if (mPanels.equals(Panels.ONE))
            navigateToActivity(step);
        else
            navigateToFragment(step);
    }

    /**
     * Starts a new activity
     *
     * @param step item
     */
    private void navigateToActivity(Step step) {
        Log.d(TAG_LOG, "Navigation to activity ");
//        Intent recipeActivityIntent = new Intent(context, RecipeActivity.class);
//        recipeActivityIntent.putExtra(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
//        context.startActivity(recipeActivityIntent);
    }

    /**
     * Replace recipe fragment
     *
     * @param step item
     */
    private void navigateToFragment(Step step) {
        Log.d(TAG_LOG, "Navigation to fragment");
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);
//
//        RecipeFragment recipeFragment = new RecipeFragment();
//        recipeFragment.setArguments(bundle);
//
//        fragmentManager
//                .beginTransaction()
//                .replace(R.id.fl_recipe_container, recipeFragment, TAG_RECIPE_FRAGMENT)
//                .commit();
    }

    public Context getContext() {
        return context;
    }
}
