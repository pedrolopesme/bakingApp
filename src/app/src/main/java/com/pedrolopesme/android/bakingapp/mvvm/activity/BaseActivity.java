package com.pedrolopesme.android.bakingapp.mvvm.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Base Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String getTagName() {
        return this.getClass().getSimpleName();
    }

    protected void renderActionBar(String title) {
        setTitle(title);
    }

}
