package com.pedrolopesme.android.bakingapp.modules.recipes;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pedrolopesme.android.bakingapp.model.Recipe;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;
import com.pedrolopesme.android.bakingapp.modules.adapter.RecipeListAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.RecyclerViewViewModel;

import java.util.ArrayList;

/**
 * Recipe Recycler View ViewModel
 */
public class RecipesViewModel extends RecyclerViewViewModel {

    private final Context appContext;

    RecipeListAdapter adapter;

    public RecipesViewModel(Context context, @Nullable State savedInstanceState) {
        super(savedInstanceState);
        appContext = context.getApplicationContext();

        ArrayList<Recipe> recipes;
        if (savedInstanceState instanceof RecipeState) {
            recipes = ((RecipeState) savedInstanceState).recipes;
        } else {
            recipes = getRecipes();
        }
        adapter = new RecipeListAdapter();
        adapter.setItems(recipes);
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

    public void onClick(Activity activity) {
        try {
            // Do something on click
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Teste");
        recipe.setServings(3);
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

        public RecipeState(RecipesViewModel viewModel) {
            super(viewModel);
            recipes = viewModel.adapter.getItems();
        }

        public RecipeState(Parcel in) {
            super(in);
            recipes = in.createTypedArrayList(Recipe.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeTypedList(recipes);
        }

        public static Creator<RecipeState> CREATOR = new Creator<RecipeState>() {
            @Override
            public RecipeState createFromParcel(Parcel source) {
                return new RecipeState(source);
            }

            @Override
            public RecipeState[] newArray(int size) {
                return new RecipeState[size];
            }
        };
    }
}
