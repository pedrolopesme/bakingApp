package com.pedrolopesme.android.bakingapp.modules.step;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

public class StepActivity extends BaseActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    public static final String STEP_BUNDLE_KEY = "STEP_BUNDLE_KEY";

    private Recipe recipe;
    private Step step;
    private int columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(getTagName(), "Creating recipe activity");
        recipe = extractRecipeFromIntent();
        step = extractStepFromIntent();

        Log.d(getTagName(), "Recipe found: " + recipe);
        Log.d(getTagName(), "Step found: " + step);

        renderActionBar(recipe.getName());
        setContentView(R.layout.activity_step);
        renderToOrientation(getResources().getConfiguration().orientation);

        if (findViewById(R.id.fl_step_container) != null) {
            columns = 1;
        } else {
            columns = 2;
        }

        createLayout(recipe, step, columns);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createLayout(Recipe recipe, Step step, int columns) {
        Log.d(getTagName(), "Creating layout with Step Fragment for Recipe " + recipe);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment stepFragment = fragmentManager.findFragmentByTag(StepsNavigation.TAG_STEP_FRAGMENT);

        if (stepFragment == null) {
            Log.d(getTagName(), "Creating Step Fragment");

            Bundle bundle = new Bundle();
            bundle.putParcelable(StepFragment.RECIPE_BUNDLE_KEY, recipe);
            bundle.putParcelable(StepFragment.STEP_BUNDLE_KEY, step);
            bundle.putInt(StepFragment.COLUMNS_BUNDLE_NAME, columns);

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
            bundle.putInt(StepFragment.COLUMNS_BUNDLE_NAME, columns);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        renderToOrientation(newConfig.orientation);
        createLayout(recipe, step, columns);
    }

    private void renderToOrientation(int orientation){
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportActionBar().show();
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            getSupportActionBar().hide();
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
