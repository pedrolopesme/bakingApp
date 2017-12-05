package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeFragment;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

/**
 * Recipes Activity - Main Activity
 */
public final class RecipesActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(getTagName(), "Creating RecipesActivity");

        super.onCreate(savedInstanceState);
        renderActionBar(getString(R.string.activity_recipes));
        setContentView(R.layout.activity_recipes);

        FragmentManager fragmentManager = getSupportFragmentManager();
        createLayout(fragmentManager);
    }

    /**
     * Creates Recipes Fragment
     *
     * @param fragmentManager reference
     */
    private void createLayout(FragmentManager fragmentManager) {
        Log.d(getTagName(), "Creating layout");

        Fragment fragment = fragmentManager.findFragmentByTag(RecipesFragment.TAG_RECIPES_FRAGMENT);
        if (fragment == null) {
            Log.d(getTagName(), "Creating RecipesFragment");

            fragment = new RecipesFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipes_container, fragment, RecipesFragment.TAG_RECIPES_FRAGMENT)
                    .commit();

            Log.d(getTagName(), "Fragment Created");
        }
    }
}
