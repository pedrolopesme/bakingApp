package com.pedrolopesme.android.bakingapp.modules.adapter;

import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.ItemRecipeBinding;
import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipeItemViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Recipes List Adapter implementing RecyclerViewAdapter
 */
public final class RecipeListAdapter extends RecyclerViewAdapter<Recipe, RecipeItemViewModel> {

    private final String TAG_LOG = this.getClass().getSimpleName();

    @Override
    public RecipeViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.d(TAG_LOG, "Creating viewHolder for RecipeListAdapter");

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        RecipeItemViewModel viewModel = new RecipeItemViewModel();
        ItemRecipeBinding binding = ItemRecipeBinding.bind(itemView);
        binding.setViewModel(viewModel);
        return new RecipeViewHolder(itemView, binding, viewModel);
    }

    public void setItems(final ArrayList<Recipe> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public ArrayList<Recipe> getItems() {
        return items;
    }

    static class RecipeViewHolder
            extends ItemViewHolder<Recipe, RecipeItemViewModel> {

        private final String TAG_LOG = this.getClass().getSimpleName();

        RecipeViewHolder(final View itemView, final ViewDataBinding binding,
                         final RecipeItemViewModel viewModel) {
            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_recipe_name)
        void onClickRecipeItem() {
            Log.d(TAG_LOG, "Firing onClick on RecipeViewHolder");
            viewModel.onClick();
        }
    }

}
