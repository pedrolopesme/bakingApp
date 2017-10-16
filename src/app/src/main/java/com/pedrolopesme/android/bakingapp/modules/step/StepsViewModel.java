package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.integration.APIServiceFactory;
import com.pedrolopesme.android.bakingapp.integration.api.RecipesAPIService;
import com.pedrolopesme.android.bakingapp.integration.api.RetrofitAPIServiceFactory;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.adapter.RecipeListAdapter;
import com.pedrolopesme.android.bakingapp.modules.adapter.StepListAdapter;
import com.pedrolopesme.android.bakingapp.modules.recipes.RecipesNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.RecyclerViewViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Steps Recycler View ViewModel
 */
public final class StepsViewModel extends RecyclerViewViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private final StepListAdapter adapter;

    public StepsViewModel(final StepsNavigation stepsNavigation,
                          final Context context,
                          final @Nullable State savedInstanceState) {

        super(savedInstanceState);
        Log.d(TAG_LOG, "Creating StepsViewModel");
        appContext = context.getApplicationContext();

        adapter = new StepListAdapter(stepsNavigation);
        if (savedInstanceState instanceof StepState) {
            adapter.setItems(((StepState) savedInstanceState).steps);
        }

        Log.d(TAG_LOG, "StepsViewModel created");
    }

    @Override
    protected RecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(appContext);
    }

    @Override
    public RecyclerViewViewModelState getInstanceState() {
        return new StepState(this);
    }

    public void setSteps(List<Step> steps) {
        adapter.setItems(steps);
    }

    public static class StepState extends RecyclerViewViewModelState {

        private final ArrayList<Step> steps;

        public StepState(final StepsViewModel viewModel) {
            super(viewModel);
            steps = viewModel.adapter.getItems();
        }

        public StepState(final Parcel in) {
            super(in);
            steps = in.createTypedArrayList(Step.CREATOR);
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeTypedList(steps);
        }

        public static Creator<StepState> CREATOR = new Creator<StepsViewModel.StepState>() {
            @Override
            public StepsViewModel.StepState createFromParcel(final Parcel source) {
                return new StepsViewModel.StepState(source);
            }

            @Override
            public StepsViewModel.StepState[] newArray(final int size) {
                return new StepsViewModel.StepState[size];
            }
        };
    }
}
