package com.pedrolopesme.android.bakingapp.modules.steps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.step.StepActivity;

/**
 * Controls navigation between Steps Master Navigation Fragments/Activities
 */
public class StepsNavigation {

    private final String TAG_LOG = this.getClass().getSimpleName();
    //    public static final String TAG_RECIPES_FRAGMENT = "recipesFragment";
    public static final String TAG_STEP_FRAGMENT = "stepFragment";

    public enum Panels {
        ONE, TWO
    }

    private Panels mPanels;
    private Context context;
    private FragmentManager fragmentManager;
    private Recipe recipe;

    public StepsNavigation(Panels mPanel, Context context, FragmentManager fragmentManager, Recipe recipe) {
        this.mPanels = mPanel;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.recipe = recipe;
    }

    /**
     * Navigate to the recipe detail according to panels state
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
     */
    private void navigateToActivity(Step step) {
        Log.d(TAG_LOG, "Navigation to activity ");
        Intent stepActivityIntent = new Intent(context, StepActivity.class);
        stepActivityIntent.putExtra(StepActivity.RECIPE_BUNDLE_KEY, recipe);
        stepActivityIntent.putExtra(StepActivity.STEP_BUNDLE_KEY, step);
        context.startActivity(stepActivityIntent);
    }

    /**
     * Replace recipe fragment
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
