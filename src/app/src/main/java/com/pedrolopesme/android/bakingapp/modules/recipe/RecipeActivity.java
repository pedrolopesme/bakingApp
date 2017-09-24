package com.pedrolopesme.android.bakingapp.modules.recipe;

import android.os.Bundle;

import com.pedrolopesme.android.bakingapp.R;
import com.pedrolopesme.android.bakingapp.mvvm.activity.BaseActivity;

public class RecipeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
    }

}
