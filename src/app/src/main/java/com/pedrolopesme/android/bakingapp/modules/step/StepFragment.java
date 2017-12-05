package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentStepBinding;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Step Fragment
 * <p>
 * This fragment shows step info
 */
public final class StepFragment extends ViewModelFragment implements OnPreparedListener {

    public static final String TAG_STEP_FRAGMENT = "stepFragment";
    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    public static final String STEP_BUNDLE_KEY = "STEP_BUNDLE_KEY";
    public static final String COLUMNS_BUNDLE_NAME = "stepFragmentColumns";
    public static final String LANDSCAPE_MODE = "stepLandscapeMode";

    private StepViewModel stepViewModel;
    private Recipe recipe;
    private Step step;
    private VideoView videoView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        recipe = extractRecipeFromArguments();
        step = extractStepFromArguments();
        int currentOrientation = getResources().getConfiguration().orientation;

        if (recipe == null || step == null)
            return createViewStepNotFound(inflater, container, savedInstanceState);
        else {
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.is_phone)){
                return createViewStepVideoFullScreen(inflater, container, savedInstanceState);
            } else {
                return createViewStepFound(inflater, container, savedInstanceState);
            }
        }
    }

    private View createViewStepFound(final LayoutInflater inflater, final ViewGroup container,
                                     final Bundle savedInstanceState) {

        Log.d(getTagName(), "Step found " + step + " for the recipe " + recipe);
        stepViewModel.setRecipe(recipe);
        stepViewModel.setStep(step);
        stepViewModel.setStepsNavigation(createStepsNavigation());

        View root = inflater.inflate(R.layout.fragment_step, container, false);
        FragmentStepBinding binding = FragmentStepBinding.bind(root);
        binding.setViewModel(stepViewModel);

        ButterKnife.bind(this, root);
        return root;
    }

    private View createViewStepVideoFullScreen(final LayoutInflater inflater, final ViewGroup container,
                                               final Bundle savedInstanceState) {
        Log.d(getTagName(), "Step found " + step + " for the recipe " + recipe);

        stepViewModel.setRecipe(recipe);
        stepViewModel.setStep(step);
        stepViewModel.setStepsNavigation(createStepsNavigation());

        View root = inflater.inflate(R.layout.fragment_step_videoonly, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private View createViewStepNotFound(final LayoutInflater inflater, final ViewGroup container,
                                        final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_not_found, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Optional
    @OnClick(R.id.tv_step_right)
    public void moveToNext() {
        Log.d(getTagName(), "Moving the next step");
        stepViewModel.moveToNextStep(recipe, step);
    }

    @Optional
    @OnClick(R.id.tv_step_left)
    public void moveToPrevious() {
        Log.d(getTagName(), "Moving the previous step");
        stepViewModel.moveToPrevioustStep(recipe, step);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (stepViewModel.hasVideoUrl()) {
            videoView = (VideoView) getView().findViewById(R.id.vv_step_video);
            videoView.setOnPreparedListener(this);
            videoView.setVideoURI(stepViewModel.getVideoUri());
            videoView.setVisibility(View.VISIBLE);
        }

    }

    @Nullable
    @Override
    protected ViewModel createViewModel(final @Nullable ViewModel.State savedViewModelState) {
        Log.d(getTagName(), "Creating step view model");
        stepViewModel = new StepViewModel(getContext(), savedViewModelState);
        return stepViewModel;
    }

    public void setRecipe(Recipe recipe) {
        stepViewModel.setRecipe(recipe);
    }

    public void setStep(Step step) {
        stepViewModel.setStep(step);
    }

    private Recipe extractRecipeFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return (Recipe) arguments.getParcelable(RECIPE_BUNDLE_KEY);
        }
        return null;
    }

    private Step extractStepFromArguments() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return (Step) arguments.getParcelable(STEP_BUNDLE_KEY);
        }
        return null;
    }

    @Override
    public void onPrepared() {
    }

    /**
     * Creates steps navigation
     *
     * @return steps navigation
     */
    private StepsNavigation createStepsNavigation() {
        Bundle bundle = getArguments();
        Integer panels = bundle.getInt(COLUMNS_BUNDLE_NAME);

        switch (panels) {
            case 1:
                return new StepsNavigation(StepsNavigation.Panels.ONE, getContext(), getFragmentManager(), recipe);
            case 2:
                return new StepsNavigation(StepsNavigation.Panels.TWO, getContext(), getFragmentManager(), recipe);
            default:
                return new StepsNavigation(StepsNavigation.Panels.ONE, getContext(), getFragmentManager(), recipe);
        }
    }
}
