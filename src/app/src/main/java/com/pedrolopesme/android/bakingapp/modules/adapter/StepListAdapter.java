package com.pedrolopesme.android.bakingapp.modules.adapter;

import android.databinding.ViewDataBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.ItemRecipeBinding;
import com.pedrolopesme.android.bakingapp.databinding.ItemStepBinding;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipeItemViewModel;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesNavigation;
import com.pedrolopesme.android.bakingapp.modules.step.StepItemViewModel;
import com.pedrolopesme.android.bakingapp.modules.step.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Step List Adapter implementing RecyclerViewAdapter
 */
public final class StepListAdapter extends RecyclerViewAdapter<Step, StepItemViewModel> {

    private final String TAG_LOG = this.getClass().getSimpleName();

    private StepsNavigation stepsNavigation;

    /**
     * Starts StepListAdapter injecting steps navigator
     *
     * @param stepsNavigation navigator
     */
    public StepListAdapter(StepsNavigation stepsNavigation) {
        this.stepsNavigation = stepsNavigation;
    }

    @Override
    public StepViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.d(TAG_LOG, "Creating viewHolder for StepListAdapter");

        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_step, parent, false);

        StepItemViewModel viewModel = new StepItemViewModel(stepsNavigation);
        ItemStepBinding binding = ItemStepBinding.bind(itemView);
        binding.setViewModel(viewModel);

        return new StepViewHolder(itemView, binding, viewModel);
    }

    public void setItems(final List<Step> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public ArrayList<Step> getItems() {
        return items;
    }

    class StepViewHolder
            extends ItemViewHolder<Step, StepItemViewModel> {

        private final String TAG_LOG = this.getClass().getSimpleName();

        StepViewHolder(final View itemView, final ViewDataBinding binding,
                       final StepItemViewModel viewModel) {

            super(itemView, binding, viewModel);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cl_step_item)
        void onClickStepItem() {
            Log.d(TAG_LOG, "Firing onClick on StepViewHolder");
            viewModel.onClick();
        }
    }
}
