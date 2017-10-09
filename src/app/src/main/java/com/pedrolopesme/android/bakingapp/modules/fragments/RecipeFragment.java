package com.pedrolopesme.android.bakingapp.modules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentRecipeBinding;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipe.RecipeViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import butterknife.ButterKnife;

/**
 * Recipe Fragment.
 * <p>
 * This fragment shows recipe info
 */
public final class RecipeFragment extends ViewModelFragment {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    private RecipeViewModel recipeViewModel;
    private Recipe recipe;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        recipe = extractRecipeFromArguments();
        if (recipe == null)
            return createViewRecipeNotFound(inflater, container, savedInstanceState);
        else
            return createViewRecipeFound(inflater, container, savedInstanceState);

    }
    
    private View createViewRecipeFound(final LayoutInflater inflater, final ViewGroup container,
                                       final Bundle savedInstanceState) {
        Log.d(getTagName(), "Recipe found: " + recipe);
        recipeViewModel.setRecipe(recipe);
        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, root);
        FragmentRecipeBinding binding = FragmentRecipeBinding.bind(root);
        binding.setViewModel(recipeViewModel);
        return root;
    }

    private View createViewRecipeNotFound(final LayoutInflater inflater, final ViewGroup container,
                                          final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_not_found, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(final @Nullable ViewModel.State savedViewModelState) {
        Log.d(getTagName(), "Creating view model");
        recipeViewModel = new RecipeViewModel(getContext(), savedViewModelState);
        return recipeViewModel;
    }

    public void setRecipe(Recipe recipe) {
        recipeViewModel.setRecipe(recipe);
    }

    private Recipe extractRecipeFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return (Recipe) arguments.getParcelable(RECIPE_BUNDLE_KEY);
        }
        return null;
    }

}
