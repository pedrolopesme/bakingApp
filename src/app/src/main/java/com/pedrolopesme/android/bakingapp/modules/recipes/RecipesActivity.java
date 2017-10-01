package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipeFragment;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipesFragment;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

/**
 * Recipes Activity - Main Activity
 */
public final class RecipesActivity extends BaseActivity {

    private final String TAG_LOG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(TAG_LOG, "Creating RecipesActivity");
        super.onCreate(savedInstanceState);
        renderActionBar(getString(R.string.activity_recipes));
        setContentView(R.layout.activity_recipes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fl_recipe_container) != null) {
            createTwoPanesLayout(fragmentManager);
        } else {
            createOnePaneLayout(fragmentManager);
        }
    }

    /**
     * Creates Recipes Fragment
     *
     * @param fragmentManager reference
     */
    private void createOnePaneLayout(FragmentManager fragmentManager) {
        Log.d(TAG_LOG, "Creating layout with one pane");

        int columns = 1;
        Fragment fragment = fragmentManager.findFragmentByTag(RecipesNavigation.TAG_RECIPES_FRAGMENT);
        if (fragment == null) {
            Log.d(TAG_LOG, "Creating RecipesFragment");

            fragment = new RecipesFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(RecipesFragment.COLUMNS_BUNDLE_NAME, columns);
            fragment.setArguments(arguments);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipes_container, fragment, RecipesNavigation.TAG_RECIPES_FRAGMENT)
                    .commit();

            Log.d(TAG_LOG, "Fragment Created");

        } else {
            Bundle arguments = fragment.getArguments();
            arguments.putInt(RecipesFragment.COLUMNS_BUNDLE_NAME, columns);
        }
    }

    /**
     * Creates Recipes Fragment and Recipe Fragment
     *
     * @param fragmentManager reference
     */
    private void createTwoPanesLayout(FragmentManager fragmentManager) {
        Log.d(TAG_LOG, "Creating layout with two panes");

        int columns = 2;
        Fragment recipesFragment = fragmentManager.findFragmentByTag(RecipesNavigation.TAG_RECIPES_FRAGMENT);
        Fragment recipeFragment = fragmentManager.findFragmentByTag(RecipesNavigation.TAG_RECIPE_FRAGMENT);

        if (recipesFragment == null && recipeFragment == null) {

            Log.d(TAG_LOG, "Creating fragments");
            recipeFragment = new RecipeFragment();
            recipesFragment = new RecipesFragment();

            Bundle arguments = new Bundle();
            arguments.putInt(RecipesFragment.COLUMNS_BUNDLE_NAME, columns);
            recipesFragment.setArguments(arguments);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipes_container, recipesFragment, RecipesNavigation.TAG_RECIPES_FRAGMENT)
                    .add(R.id.fl_recipe_container, recipeFragment, RecipesNavigation.TAG_RECIPE_FRAGMENT)
                    .commit();

            Log.d(TAG_LOG, "Fragment Created");

        } else {

            Bundle arguments = recipesFragment.getArguments();
            arguments.putInt(RecipesFragment.COLUMNS_BUNDLE_NAME, columns);
        }
    }
}
