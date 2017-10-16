package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentRecipesBinding;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import butterknife.ButterKnife;

/**
 * Recipes Fragment.
 * <p>
 * This fragment shows an recycler view with recipes, so it uses an
 * RecipesViewModel which implements an RecyclerViewViewModel
 */
public final class RecipesFragment extends ViewModelFragment {

    public static final String TAG_RECIPES_FRAGMENT = "recipesFragment";
    private RecipesViewModel recipesRecyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, root);
        FragmentRecipesBinding binding = FragmentRecipesBinding.bind(root);
        binding.setViewModel(recipesRecyclerView);
        return root;
    }

    @Nullable
    @Override
    protected ViewModel createViewModel(final @Nullable ViewModel.State savedViewModelState) {
        recipesRecyclerView = new RecipesViewModel(getContext(), savedViewModelState);
        return recipesRecyclerView;
    }
}
