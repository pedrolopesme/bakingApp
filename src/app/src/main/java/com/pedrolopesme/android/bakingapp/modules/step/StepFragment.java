package com.pedrolopesme.android.bakingapp.modules.step;

import android.net.Uri;
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
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private StepViewModel stepViewModel;
    private Recipe recipe;
    private Step step;
    private VideoView videoView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        recipe = extractRecipeFromArguments();
        step = extractStepFromArguments();
        if (recipe == null || step == null)
            return createViewStepNotFound(inflater, container, savedInstanceState);
        else
            return createViewStepFound(inflater, container, savedInstanceState);
    }

    private View createViewStepFound(final LayoutInflater inflater, final ViewGroup container,
                                     final Bundle savedInstanceState) {

        Log.d(getTagName(), "Step found " + step + " for the recipe " + recipe);
        stepViewModel.setRecipe(recipe);
        stepViewModel.setStep(step);

        View root = inflater.inflate(R.layout.fragment_step, container, false);
        FragmentStepBinding binding = FragmentStepBinding.bind(root);
        binding.setViewModel(stepViewModel);

        ButterKnife.bind(this, root);
        return root;
    }

    private View createViewStepNotFound(final LayoutInflater inflater, final ViewGroup container,
                                        final Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_not_found, container, false);
        ButterKnife.bind(this, root);
        return root;
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
}
