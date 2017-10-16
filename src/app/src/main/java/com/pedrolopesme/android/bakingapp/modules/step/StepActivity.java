package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

public class StepActivity extends BaseActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    public static final String STEP_BUNDLE_KEY = "STEP_BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(getTagName(), "Creating recipe activity");
        Recipe recipe = extractRecipeFromIntent();
        Step step = extractStepFromIntent();

        Log.d(getTagName(), "Recipe found: " + recipe);
        Log.d(getTagName(), "Step found: " + step);

        renderActionBar(recipe.getName());
        setContentView(R.layout.activity_step);

        createLayout(recipe, step);
    }

    private void createLayout(Recipe recipe, Step step) {
        Log.d(getTagName(), "Creating layout with Step Fragment for Recipe " + recipe);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment stepFragment = fragmentManager.findFragmentByTag(StepsNavigation.TAG_STEP_FRAGMENT);

        if (stepFragment == null) {
            Log.d(getTagName(), "Creating Step Fragment");

            Bundle bundle = new Bundle();
            bundle.putParcelable(StepFragment.RECIPE_BUNDLE_KEY, recipe);
            bundle.putParcelable(StepFragment.STEP_BUNDLE_KEY, step);
//            bundle.putInt(StepFragment.COLUMNS_BUNDLE_NAME, 1);
            stepFragment = new StepFragment();
            stepFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_step_container, stepFragment, StepsNavigation.TAG_STEP_FRAGMENT)
                    .commit();

            Log.d(getTagName(), "Fragment Created");

        } else {
            Log.d(getTagName(), "Reusing Fragment with recipe: " + recipe);
            Bundle bundle = stepFragment.getArguments();
            bundle.putParcelable(StepFragment.RECIPE_BUNDLE_KEY, recipe);
            bundle.putParcelable(StepFragment.STEP_BUNDLE_KEY, step);
//            arguments.putInt(RecipeFragment.COLUMNS_BUNDLE_NAME, 1);
        }
    }

    private Recipe extractRecipeFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            return (Recipe) intent.getParcelableExtra(RECIPE_BUNDLE_KEY);

        }
        return null;
    }

    private Step extractStepFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            return (Step) intent.getParcelableExtra(STEP_BUNDLE_KEY);

        }
        return null;
    }

}
