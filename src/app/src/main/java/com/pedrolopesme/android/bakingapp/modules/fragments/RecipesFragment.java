package com.pedrolopesme.android.bakingapp.modules.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentRecipesBinding;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Base Fragment
 */
public class RecipesFragment extends ViewModelFragment {

    private RecipesViewModel recipeRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, root);
        FragmentRecipesBinding binding = FragmentRecipesBinding.bind(root);
        binding.setViewModel(recipeRecyclerView);
        return root;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(@Nullable ViewModel.State savedViewModelState) {
        recipeRecyclerView = new RecipesViewModel(getContext(), savedViewModelState);
        return recipeRecyclerView;
    }

    @OnClick(R.id.tv_recipe_name)
    void onClick() {
        recipeRecyclerView.onClick(getActivity());
    }


}
