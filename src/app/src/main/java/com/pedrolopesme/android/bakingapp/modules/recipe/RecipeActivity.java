package com.pedrolopesme.android.bakingapp.modules.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.step.StepActivity;
import com.pedrolopesme.android.bakingapp.modules.step.StepFragment;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

public class RecipeActivity extends BaseActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(getTagName(), "Creating recipe activity");
        recipe = extractRecipeFromIntent();

        Log.d(getTagName(), "Recipe fount: " + recipe);
        renderActionBar(recipe.getName());
        setContentView(R.layout.activity_recipe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fl_step_container) != null) {
            createTwoPanesLayout(fragmentManager);
        } else {
            createOnePaneLayout(fragmentManager);
        }
    }

    private void createOnePaneLayout(FragmentManager fragmentManager) {
        Log.d(getTagName(), "Creating layout with Recipe fragment for recipe " + recipe);

        Fragment recipeFragment = fragmentManager.findFragmentByTag(RecipeFragment.TAG_RECIPE_FRAGMENT);

        if (recipeFragment == null) {
            Log.d(getTagName(), "Creating Recipe Fragment");

            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);
            bundle.putInt(RecipeFragment.COLUMNS_BUNDLE_NAME, 1);
            recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipe_container, recipeFragment, RecipeFragment.TAG_RECIPE_FRAGMENT)
                    .commit();

            Log.d(getTagName(), "Fragment Created");

        } else {
            Log.d(getTagName(), "Reusing Fragment with recipe: " + recipe);
            Bundle arguments = recipeFragment.getArguments();
            arguments.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);
            arguments.putInt(RecipeFragment.COLUMNS_BUNDLE_NAME, 1);
        }
    }

    /**
     * Creates Recipe Fragment With Step Fragment side by side
     *
     * @param fragmentManager reference
     */
    private void createTwoPanesLayout(FragmentManager fragmentManager) {
        Log.d(getTagName(), "Creating layout with two panes");

        int columns = 2;
        Fragment recipeFragment = fragmentManager.findFragmentByTag(RecipeFragment.TAG_RECIPE_FRAGMENT);
        Fragment stepsFragment = fragmentManager.findFragmentByTag(StepFragment.TAG_STEP_FRAGMENT);

        if (recipeFragment == null && stepsFragment == null) {

            Log.d(getTagName(), "Creating fragments");
            recipeFragment = new RecipeFragment();
            Bundle recipeBundle = new Bundle();
            recipeBundle.putInt(RecipeFragment.COLUMNS_BUNDLE_NAME, columns);
            recipeBundle.putParcelable(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
            recipeFragment.setArguments(recipeBundle);


            stepsFragment = new StepFragment();
            Step firstStep = recipe.getSteps().get(0);
            Bundle stepBundle = new Bundle();
            stepBundle.putInt(StepFragment.COLUMNS_BUNDLE_NAME, columns);
            stepBundle.putParcelable(StepActivity.RECIPE_BUNDLE_KEY, recipe);
            stepBundle.putParcelable(StepActivity.STEP_BUNDLE_KEY, firstStep);
            stepsFragment.setArguments(stepBundle);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipe_container, recipeFragment, RecipeFragment.TAG_RECIPE_FRAGMENT)
                    .add(R.id.fl_step_container, stepsFragment, StepFragment.TAG_STEP_FRAGMENT)
                    .commit();

            Log.d(getTagName(), "Fragments Created");

        } else {
            Bundle arguments = recipeFragment.getArguments();
            arguments.putParcelable(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
            arguments.putInt(RecipeFragment.COLUMNS_BUNDLE_NAME, columns);
        }
    }

    private Recipe extractRecipeFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            return (Recipe) intent.getParcelableExtra(RECIPE_BUNDLE_KEY);

        }
        return null;
    }

}
