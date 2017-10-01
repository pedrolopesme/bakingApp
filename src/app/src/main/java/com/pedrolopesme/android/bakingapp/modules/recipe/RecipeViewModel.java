package com.pedrolopesme.android.bakingapp.modules.recipe;

import android.content.Context;
import android.databinding.Bindable;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.modules.adapter.RecipeListAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.adapter.RecyclerViewAdapter;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.RecyclerViewViewModel;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import java.util.ArrayList;

/**
 * Recipe ViewModel
 */
public final class RecipeViewModel extends ViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private Recipe recipe;

    public RecipeViewModel(final Context context, final @Nullable State savedInstanceState) {
        super(savedInstanceState);
        Log.d(TAG_LOG, "Creating Recipe ViewModel");
        appContext = context.getApplicationContext();

        if (savedInstanceState instanceof RecipeState) {
            recipe = ((RecipeState) savedInstanceState).recipe;
        }

        Log.d(TAG_LOG, "Recipe ViewModel created");
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Bindable
    public String getName() {
        return recipe.getName();
    }

    @Override
    public RecipeState getInstanceState() {
        return new RecipeState(this);
    }

    private static class RecipeState extends State {

        private final Recipe recipe;

        public RecipeState(final RecipeViewModel viewModel) {
            super(viewModel);
            recipe = viewModel.recipe;
        }

        public RecipeState(final Parcel in) {
            super(in);
            recipe = (Recipe) in.readValue(Recipe.class.getClassLoader());
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(recipe, flags);
        }

        public static Creator<RecipeState> CREATOR = new Creator<RecipeViewModel.RecipeState>() {
            @Override
            public RecipeViewModel.RecipeState createFromParcel(final Parcel source) {
                return new RecipeViewModel.RecipeState(source);
            }

            @Override
            public RecipeViewModel.RecipeState[] newArray(final int size) {
                return new RecipeViewModel.RecipeState[size];
            }
        };
    }
}
