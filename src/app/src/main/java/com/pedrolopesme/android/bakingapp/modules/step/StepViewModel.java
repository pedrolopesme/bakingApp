package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Context;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

/**
 * Step ViewModel
 */
public final class StepViewModel extends ViewModel {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private final Context appContext;
    private Recipe recipe;
    private Step step;
    private StepsNavigation stepsNavigation;

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

    public void setStepsNavigation(StepsNavigation stepsNavigation) {
        this.stepsNavigation = stepsNavigation;
    }

    public Uri getStepThumbUri() {
        if (step != null && step.getThumbnailURL() != null)
            return Uri.parse(step.getThumbnailURL());

        return null;
    }

    @Bindable
    public String getStepNumber() {
        return String.valueOf(step.getId());
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
        if (step != null)
            return Uri.parse(step.getVideoURL());

        return null;
    }

    public boolean hasVideoUrl() {
        return step != null && step.getVideoURL() != null && !step.getVideoURL().isEmpty();
    }

    /**
     * Moves to the next step. If its the last step, move to the first
     *
     * @param recipe
     * @param currentStep
     */
    public void moveToNextStep(final Recipe recipe, final Step currentStep) {
        if (recipe != null && currentStep != null) {
            int nextStepIndex = recipe.getSteps().indexOf(currentStep) + 1;
            if (nextStepIndex >= recipe.getSteps().size())
                nextStepIndex = 0;

            Step step = recipe.getSteps().get(nextStepIndex);
            stepsNavigation.navigate(step);
        }
    }

    /**
     * Moves to the previous step. If its the first step, move to the last
     *
     * @param recipe
     * @param currentStep
     */
    public void moveToPrevioustStep(final Recipe recipe, final Step currentStep) {
        if (recipe != null && currentStep != null) {
            int nextStepIndex = recipe.getSteps().indexOf(currentStep) - 1;
            if (nextStepIndex < 0)
                nextStepIndex = recipe.getSteps().size() - 1;

            Step step = recipe.getSteps().get(nextStepIndex);
            stepsNavigation.navigate(step);
        }
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
