package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.databinding.FragmentStepBinding;
import com.pedrolopesme.android.bakingapp.models.Recipe;
import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.modules.steps.StepsNavigation;
import com.pedrolopesme.android.bakingapp.mvvm.fragment.ViewModelFragment;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Step Fragment
 * <p>
 * This fragment shows step info
 */
public final class StepFragment extends ViewModelFragment {

    public static final String TAG_STEP_FRAGMENT = "stepFragment";
    public static final String RECIPE_BUNDLE_KEY = "RECIPE_BUNDLE_KEY";
    public static final String STEP_BUNDLE_KEY = "STEP_BUNDLE_KEY";
    public static final String COLUMNS_BUNDLE_NAME = "stepFragmentColumns";
    public static final String LANDSCAPE_MODE = "stepLandscapeMode";

    private StepViewModel stepViewModel;
    private Recipe recipe;
    private Step step;
    private BandwidthMeter bandwidthMeter;
    private Handler mainHandler;
    private SimpleExoPlayer player;

    @BindView(R.id.vv_step_video)
    protected SimpleExoPlayerView simpleExoPlayerView;

    @BindView(R.id.iv_step_thumb)
    protected ImageView thumbImage;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        recipe = extractRecipeFromArguments();
        step = extractStepFromArguments();
        int currentOrientation = getResources().getConfiguration().orientation;
        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        if (recipe == null || step == null)
            return createViewStepNotFound(inflater, container, savedInstanceState);
        else {
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE && getResources().getBoolean(R.bool.is_phone)) {
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

        if(stepViewModel.getStepThumbUri() != null) {
            Picasso.with(getContext()).load(stepViewModel.getStepThumbUri()).into(thumbImage);
        }

        if (stepViewModel.hasVideoUrl()) {
            simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            initializePlayer(stepViewModel.getVideoUri());
            simpleExoPlayerView.setVisibility(View.VISIBLE);
        } else {
            simpleExoPlayerView.setVisibility(View.GONE);
            player = null;
        }

    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
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
