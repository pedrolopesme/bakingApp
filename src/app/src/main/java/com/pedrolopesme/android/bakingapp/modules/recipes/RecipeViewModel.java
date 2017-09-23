package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.databinding.Bindable;

import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ItemViewModel;

/**
 * Recipe List Item ViewModel
 */

public class RecipeViewModel extends ItemViewModel<Recipe> {

    private Recipe recipe;

    @Override
    public void setItem(Recipe item) {
        recipe = item;
        notifyChange();
    }

    public void onClick() {
//        notifyPropertyChanged(BR.);
    }

    @Bindable
    public String getName() {
        return recipe.getName();
    }
}
