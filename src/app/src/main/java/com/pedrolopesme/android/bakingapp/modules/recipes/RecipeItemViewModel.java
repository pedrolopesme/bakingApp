package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeActivity;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ItemViewModel;

/**
 * Recipe List Item ViewModel
 */
public final class RecipeItemViewModel extends ItemViewModel<Recipe> {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private Recipe recipe;

    @Override
    public void setItem(final Recipe item) {
        Log.d(TAG_LOG, "Setting Recipe to RecipeItemViewModel: " + item);
        recipe = item;
        notifyChange();
    }

    /**
     * Opens a Recipe Activity from the current recipe item
     *
     * @param context
     */
    public void onClick(Context context) {
        Log.d(TAG_LOG, "Firing onClick on recipe:" + recipe);
        context.startActivity(new Intent(context, RecipeActivity.class));
    }

    @Bindable
    public String getName() {
        return recipe.getName();
    }
}
