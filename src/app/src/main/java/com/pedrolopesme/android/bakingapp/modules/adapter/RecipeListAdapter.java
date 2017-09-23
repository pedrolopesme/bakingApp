package com.pedrolopesme.android.bakingapp.modules.adapter;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.ItemRecipeBinding;
import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipeViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeListAdapter extends RecyclerViewAdapter<Recipe, RecipeViewModel> {

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        RecipeViewModel viewModel = new RecipeViewModel();
        ItemRecipeBinding binding = ItemRecipeBinding.bind(itemView);
        binding.setViewModel(viewModel);
        return new RecipeViewHolder(itemView, binding, viewModel);
    }

    public void setItems(ArrayList<Recipe> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public ArrayList<Recipe> getItems() {
        return items;
    }

    static class RecipeViewHolder
            extends ItemViewHolder<Recipe, RecipeViewModel> {

        RecipeViewHolder(View itemView, ViewDataBinding binding,
                                RecipeViewModel viewModel) {
            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.tv_recipe_name)
        void onClickVersionItem() {
            viewModel.onClick();
        }
    }

}
