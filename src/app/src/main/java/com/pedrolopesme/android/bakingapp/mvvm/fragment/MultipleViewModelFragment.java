package com.pedrolopesme.android.bakingapp.mvvm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * View Model Abstract Fragment Used in cases of multiples viewModels
 * per Fragment
 */
public abstract class MultipleViewModelFragment extends Fragment {

    private static final String EXTRA_VIEW_MODEL_STATES = "viewModelStates";

    private List<ViewModel> viewModels;

    protected String getTagName() {
        return this.getClass().getSimpleName();
    }

    @Nullable
    protected abstract List<ViewModel> createViewModel(@Nullable List<ViewModel.State> savedViewModelState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<ViewModel.State> savedViewModelState = null;
        if (savedInstanceState != null) {
            savedViewModelState = savedInstanceState.getParcelableArrayList(EXTRA_VIEW_MODEL_STATES);
        }
        viewModels = createViewModel(savedViewModelState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModels != null) {
            for (ViewModel viewModel : viewModels)
                viewModel.onStart();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModels != null) {
            ArrayList<ViewModel.State> states = new ArrayList<>();

            for (ViewModel viewModel : viewModels)
                states.add(viewModel.getInstanceState());

            outState.putParcelableArrayList(EXTRA_VIEW_MODEL_STATES, states);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModels != null) {
            for (ViewModel viewModel : viewModels)
                viewModel.onStart();
        }
    }

    protected ViewModel.State getState(Class<?> desiredState, List<ViewModel.State> states){
        if(states != null){
            for(ViewModel.State state : states)
                if(state.getClass() == desiredState) return state;
        }
        return null;
    }
}