package com.pedrolopesme.android.bakingapp.mvvm.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import com.pedrolopesme.android.bakingapp.R;

/**
 * View Model Abstract Class
 */
public abstract class ViewModel extends BaseObservable {

    protected ViewModel(@Nullable State savedInstanceState) {
    }

    @CallSuper
    public void onStart() {
    }

    public State getInstanceState() {
        return new State(this);
    }

    @CallSuper
    public void onStop() {
    }

    /**
     * Shows a snack message
     *
     * @param context
     * @param viewId
     * @param message
     */
    public void showSnackMessage(Context context, int viewId, int message) {
        Snackbar.make(((Activity) context).getWindow().getDecorView().findViewById(viewId), message, Snackbar.LENGTH_LONG).show();
    }

    public static class State implements Parcelable {

        protected State(ViewModel viewModel) {
        }

        public State(Parcel in) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @CallSuper
        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }

        public static Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel source) {
                return new State(source);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }
}
