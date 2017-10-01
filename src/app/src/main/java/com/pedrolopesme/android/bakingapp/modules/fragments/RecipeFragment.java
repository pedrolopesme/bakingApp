package com.pedrolopesme.android.bakingapp.modules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentRecipeBinding;
import com.pedrolopesme.android.bakingapp.databinding.FragmentRecipesBinding;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeViewModel;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Recipe Fragment.
 * <p>
 * This fragment shows recipe info
 */
public final class RecipeFragment extends ViewModelFragment {

    private RecipeViewModel recipeViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, root);

        FragmentRecipeBinding binding = FragmentRecipeBinding.bind(root);
        binding.setViewModel(recipeViewModel);
        return root;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(final @Nullable ViewModel.State savedViewModelState) {
        recipeViewModel = new RecipeViewModel(getContext(), savedViewModelState);

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("aaa");
        recipeViewModel.setRecipe(recipe);

        return recipeViewModel;
    }

    public void setRecipe(Recipe recipe){
        recipeViewModel.setRecipe(recipe);
    }

}
