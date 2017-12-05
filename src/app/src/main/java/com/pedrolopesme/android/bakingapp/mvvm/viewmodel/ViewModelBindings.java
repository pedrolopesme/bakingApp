package com.pedrolopesme.android.bakingapp.mvvm.viewmodel;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

/**
 * View Model Bindings
 */
public class ViewModelBindings {

    @BindingAdapter("recyclerViewViewModel")
    public static void setRecyclerViewViewModel(RecyclerView recyclerView,
                                                RecyclerViewViewModel viewModel) {
        viewModel.setupRecyclerView(recyclerView);
    }

}
