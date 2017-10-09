package com.pedrolopesme.android.bakingapp.modules.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipeFragment;
import com.pedrolopesme.android.bakingapp.modules.fragments.RecipesFragment;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

public class RecipeActivity extends BaseActivity {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(getTagName(), "Creating recipe activity");
        Recipe recipe = extractRecipeFromIntent();

        Log.d(getTagName(), "Recipe fount: " + recipe);
        renderActionBar(recipe.getName());
        setContentView(R.layout.activity_recipe);

        createLayout(recipe);
    }

    private Recipe extractRecipeFromIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            return (Recipe) intent.getParcelableExtra(RECIPE_BUNDLE_KEY);

        }
        return null;
    }

    private void createLayout(Recipe recipe) {
        Log.d(getTagName(), "Creating layout with Recipe fragment for recipe " + recipe);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment recipeFragment = fragmentManager.findFragmentByTag(RecipesNavigation.TAG_RECIPE_FRAGMENT);

        if (recipeFragment == null) {
            Log.d(getTagName(), "Creating Recipe Fragment");

            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);
            recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.fl_recipe_container, recipeFragment, RecipesNavigation.TAG_RECIPE_FRAGMENT)
                    .commit();

            Log.d(getTagName(), "Fragment Created");

        } else {
            Log.d(getTagName(), "Reusing Fragment with recipe: " + recipe);
            Bundle arguments = recipeFragment.getArguments();
            arguments.putParcelable(RecipeFragment.RECIPE_BUNDLE_KEY, recipe);
        }
    }
}
