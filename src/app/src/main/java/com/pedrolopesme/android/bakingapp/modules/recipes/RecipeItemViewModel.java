package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.net.Uri;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeActivity;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ItemViewModel;
import com.pedrolopesme.android.bakingapp.utils.RecipeLevelCalculatorUtil;

/**
 * Recipe List Item ViewModel
 */
public final class RecipeItemViewModel extends ItemViewModel<Recipe> {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private Recipe recipe;
    private Context context;

    public RecipeItemViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void setItem(final Recipe item) {
        Log.d(TAG_LOG, "Setting Recipe to RecipeItemViewModel: " + item);
        recipe = item;
        notifyChange();
    }


    public Uri getStepThumbUri() {
        if (recipe != null && recipe.getRecipeAvailableImage() != null)
            return Uri.parse(recipe.getRecipeAvailableImage());

        return null;
    }


    /**
     * Opens a Recipe Activity from the current recipe item
     */
    public void onClick() {
        Log.d(TAG_LOG, "Firing onClick on recipe:" + recipe);
        Intent recipeActivityIntent = new Intent(context, RecipeActivity.class);
        recipeActivityIntent.putExtra(RecipeActivity.RECIPE_BUNDLE_KEY, recipe);
        context.startActivity(recipeActivityIntent);
    }

    @Bindable
    public String getName() {
        return recipe.getName();
    }

    @Bindable
    public String getLevel() {
        Integer resourceId = RecipeLevelCalculatorUtil.calculate(recipe);
        if (resourceId != null) {
            return context.getString(resourceId);
        }
        return context.getString(R.string.level_default);
    }

    @Bindable
    public String getServings() {
        return Integer.toString(recipe.getServings());
    }

}
