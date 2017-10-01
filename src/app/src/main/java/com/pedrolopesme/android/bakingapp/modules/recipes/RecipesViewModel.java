package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;
import com.pedrolopesme.android.bakingapp.modules.adapter.RecipeListAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.RecyclerViewViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipe Recycler View ViewModel
 */
public final class RecipesViewModel extends RecyclerViewViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private final RecipeListAdapter adapter;

    public RecipesViewModel(final RecipesNavigation recipesNavigation, final Context context, final @Nullable State savedInstanceState) {
        super(savedInstanceState);
        Log.d(TAG_LOG, "Creating RecipesViewModel");
        appContext = context.getApplicationContext();

        ArrayList<Recipe> recipes;
        if (savedInstanceState instanceof RecipeState) {
            recipes = ((RecipeState) savedInstanceState).recipes;
        } else {
            recipes = getRecipes();
        }
        adapter = new RecipeListAdapter(recipesNavigation);
        adapter.setItems(recipes);
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

    /**
     * TODO replace this with the real data source
     *
     * @return recipes list
     */
    private ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Teste");
        recipe.setServings(3);

        List<Step> steps = new ArrayList<>();
        steps.add(new Step());
        recipe.setSteps(steps);
        recipes.add(recipe);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipe2.setName("Teste 2");
        recipe2.setServings(6);
        recipes.add(recipe2);

        Recipe recipe3 = new Recipe();
        recipe3.setId(3);
        recipe3.setName("Teste 3");
        recipe3.setServings(1);
        recipes.add(recipe3);

        return recipes;
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
