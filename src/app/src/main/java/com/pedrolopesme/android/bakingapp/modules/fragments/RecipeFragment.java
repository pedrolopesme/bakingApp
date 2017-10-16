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
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.MultipleViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Recipe Fragment.
 * <p>
 * This fragment shows recipe info
 */
public final class RecipeFragment extends MultipleViewModelFragment {

    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    public static final String COLUMNS_BUNDLE_NAME = "recipeFragmentColumns";
    private RecipeViewModel recipeViewModel;
    private StepsViewModel stepsViewModel;
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
        stepsViewModel.setSteps(recipe.getSteps());

        View root = inflater.inflate(R.layout.fragment_recipe, container, false);
        FragmentRecipeBinding binding = FragmentRecipeBinding.bind(root);
        binding.setRecipeViewModel(recipeViewModel);
        binding.setStepsViewModel(stepsViewModel);

        ButterKnife.bind(this, root);
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
    protected List<ViewModel> createViewModel(final @Nullable List<ViewModel.State> savedViewModelState) {
        Log.d(getTagName(), "Creating view model");

        ViewModel.State recipeViewlModelState = getState(RecipeViewModel.RecipeState.class, savedViewModelState);
        recipeViewModel = new RecipeViewModel(getContext(), recipeViewlModelState);

        ViewModel.State stepsViewlModelState = getState(StepsViewModel.StepState.class, savedViewModelState);
        stepsViewModel = new StepsViewModel(createStepsNavigation(), getContext(), stepsViewlModelState);

        List<ViewModel> viewModels = new ArrayList<>();
        viewModels.add(recipeViewModel);
        viewModels.add(stepsViewModel);
        return viewModels;
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


    /**
     * Creates steps navigation
     *
     * @return steps navigation
     */
    private StepsNavigation createStepsNavigation() {
        Bundle bundle = getArguments();
        int panels = bundle.getInt(COLUMNS_BUNDLE_NAME);

        switch (panels) {
            case 1:
                return new StepsNavigation(StepsNavigation.Panels.ONE, getContext(), getFragmentManager());
            case 2:
                return new StepsNavigation(StepsNavigation.Panels.TWO, getContext(), getFragmentManager());
            default:
                return new StepsNavigation(StepsNavigation.Panels.ONE, getContext(), getFragmentManager());
        }
    }

}
