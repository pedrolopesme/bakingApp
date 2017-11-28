package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Context;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

/**
 * Step ViewModel
 */
public final class StepViewModel extends ViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private Recipe recipe;
    private Step step;

    public StepViewModel(final Context context, final @Nullable State savedInstanceState) {
        super(savedInstanceState);
        Log.d(TAG_LOG, "Creating Step ViewModel");
        appContext = context.getApplicationContext();

        if (savedInstanceState instanceof StepState) {
            recipe = ((StepState) savedInstanceState).recipe;
            step = ((StepState) savedInstanceState).step;
        }

        Log.d(TAG_LOG, "Step ViewModel created");
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    @Bindable
    public String getStepNumber() {
        int stepIndex = recipe.getSteps().indexOf(step);
        return String.valueOf(stepIndex + 1);
    }

    @Bindable
    public String getInstruction() {
        return step.getDescription();
    }

    @Override
    public StepState getInstanceState() {
        return new StepState(this);
    }

    public Uri getVideoUri() {
        return Uri.parse(step.getVideoURL());
    }

    public boolean hasVideoUrl() {
        return step.getVideoURL() != null && !step.getVideoURL().isEmpty();
    }

    public static class StepState extends State {

        private final Recipe recipe;
        private final Step step;

        public StepState(final StepViewModel viewModel) {
            super(viewModel);
            recipe = viewModel.recipe;
            step = viewModel.step;
        }

        public StepState(final Parcel in) {
            super(in);
            recipe = (Recipe) in.readValue(Recipe.class.getClassLoader());
            step = (Step) in.readValue(Step.class.getClassLoader());
        }

        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            super.writeToParcel(dest, flags);
            dest.writeParcelable(recipe, flags);
            dest.writeParcelable(step, flags);
        }

        public static Creator<StepState> CREATOR = new Creator<StepViewModel.StepState>() {
            @Override
            public StepViewModel.StepState createFromParcel(final Parcel source) {
                return new StepViewModel.StepState(source);
            }

            @Override
            public StepViewModel.StepState[] newArray(final int size) {
                return new StepViewModel.StepState[size];
            }
        };
    }
}
