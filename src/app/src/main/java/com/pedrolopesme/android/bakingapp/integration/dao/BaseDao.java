package com.pedrolopesme.android.bakingapp.integration.dao;

import android.content.ContentResolver;

public class BaseDao {

    private final ContentResolver contentResolver;

    BaseDao(final ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    ContentResolver getContentResolver() {
        return contentResolver;
    }

}
