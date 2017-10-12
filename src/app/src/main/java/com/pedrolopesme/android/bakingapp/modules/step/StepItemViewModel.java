package com.pedrolopesme.android.bakingapp.modules.step;

import android.content.Context;
import android.databinding.Bindable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.models.Step;
import com.pedrolopesme.android.bakingapp.mvvm.viewmodel.ItemViewModel;

/**
 * Recipe Step Item ViewModel
 */
public final class StepItemViewModel extends ItemViewModel<Step> {

    private final String TAG_LOG = this.getClass().getSimpleName();
    private Step step;
    private StepsNavigation stepNavigation;
    private Context context;

    public StepItemViewModel(StepsNavigation stepNavigation) {
        this.stepNavigation = stepNavigation;
        this.context = stepNavigation.getContext();
    }

    @Override
    public void setItem(final Step item) {
        Log.d(TAG_LOG, "Setting Recipe to RecipeItemViewModel: " + item);
        step = item;
        notifyChange();
    }

    /**
     * Opens a Recipe Activity from the current step item
     */
    public void onClick() {
        Log.d(TAG_LOG, "Firing onClick on step:" + step);
//        stepNavigation.navigate(step);
    }

    @Bindable
    public String getDescription() {
        return step.getDescription();
    }

}
