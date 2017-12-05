package com.pedrolopesme.android.bakingapp.modules.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.step.StepActivity;
import com.pedrolopesme.android.bakingapp.modules.step.StepFragment;

/**
 * Controls navigation between Steps Master Navigation Fragments/Activities
 */
public class StepsNavigation {

    private final String TAG_LOG = this.getClass().getSimpleName();
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
        Bundle bundle = new Bundle();
        bundle.putInt(StepFragment.COLUMNS_BUNDLE_NAME, 2);
        bundle.putParcelable(StepActivity.RECIPE_BUNDLE_KEY, recipe);
        bundle.putParcelable(StepActivity.STEP_BUNDLE_KEY, step);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);

        fragmentManager
                .beginTransaction()
                .replace(R.id.fl_step_container, stepFragment, StepFragment.TAG_STEP_FRAGMENT)
                .commit();
    }

    public Context getContext() {
        return context;
    }
}
