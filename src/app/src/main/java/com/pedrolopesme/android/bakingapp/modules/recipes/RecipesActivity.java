package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipesFragment;

/**
 * Recipes Activity - Main Activity
 */
public final class RecipesActivity extends AppCompatActivity {

    private final String TAG_LOG = this.getClass().getSimpleName();

    private static final String TAG_RECIPES_FRAGMENT = "recipesFragment";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(TAG_LOG, "Creating RecipesActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(TAG_RECIPES_FRAGMENT);
        if (fragment == null) {
            Log.d(TAG_LOG, "Creating RecipesFragment");
            fragment = new RecipesFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, TAG_RECIPES_FRAGMENT)
                    .commit();

            Log.d(TAG_LOG, "Fragment Created");
        }
    }
}
