package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.content.Context;
import android.content.res.Resources;
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
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;
import com.pedrolopesme.android.bakingapp.modules.adapter.RecipeListAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.RecyclerViewViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Recipe Recycler View ViewModel
 */
public final class RecipesViewModel extends RecyclerViewViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private final RecipeListAdapter adapter;

    public RecipesViewModel(final RecipesNavigation recipesNavigation,
                            final Context context,
                            final @Nullable State savedInstanceState) {
        super(savedInstanceState);
        Log.d(TAG_LOG, "Creating RecipesViewModel");
        appContext = context.getApplicationContext();

        adapter = new RecipeListAdapter(recipesNavigation);
        if (savedInstanceState instanceof RecipeState) {
            adapter.setItems(((RecipeState) savedInstanceState).recipes);
        } else {
            collectRecipes();
        }

        Log.d(TAG_LOG, "RecipesViewModel created");
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
        return new RecipeState(this);
    }

    private void collectRecipes() {

        Log.i(TAG_LOG, "Getting recipes from api");
        APIServiceFactory factory = new RetrofitAPIServiceFactory(appContext.getString(R.string.base_api_endpoint));
        RecipesAPIService recipesAPIService = factory.getRecipesAPIService();
        recipesAPIService.getAll().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG_LOG, "Recipes collected with success");
                    adapter.setItems(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG_LOG, "It was impossible to collect recipes from the API", t);
            }

        });

    }

    private static class RecipeState extends RecyclerViewViewModelState {

        private final ArrayList<Recipe> recipes;

        public RecipeState(final RecipesViewModel viewModel) {
            super(viewModel);
            recipes = viewModel.adapter.getItems();
        }

        public RecipeState(final Parcel in) {
            super(in);
            recipes = in.createTypedArrayList(Recipe.CREATOR);
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeTypedList(recipes);
        }

        public static Creator<RecipeState> CREATOR = new Creator<RecipeState>() {
            @Override
            public RecipeState createFromParcel(final Parcel source) {
                return new RecipeState(source);
            }

            @Override
            public RecipeState[] newArray(final int size) {
                return new RecipeState[size];
            }
        };
    }
}
