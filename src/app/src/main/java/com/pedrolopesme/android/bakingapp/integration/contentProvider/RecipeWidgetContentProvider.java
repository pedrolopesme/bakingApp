package com.pedrolopesme.android.bakingapp.integration.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pedrolopesme.android.bakingapp.contract.RecipeWidgetContract;

/**
 * Recipe Widget Provider
 */
public final class RecipeWidgetContentProvider extends ContentProvider {

    private final String LOG_TAG = this.getClass().getSimpleName();

    /**
     * The match code to retrieve registered recipe
     */
    private static final int CODE_RECIPE_WIDGET_DIR = 1;

    /**
     * The URI matcher.
     */
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(RecipeWidgetContract.AUTHORITY, RecipeWidgetContract.URI_RECIPE_WIDGET_PATH, CODE_RECIPE_WIDGET_DIR);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Log.i(LOG_TAG, "Querying Recipe Widget Provider");
        final int code = MATCHER.match(uri);
        if (code == CODE_RECIPE_WIDGET_DIR) {

            final Context context = getContext();
            if (context == null) {
                return null;
            }

            final Cursor cursor = null;
            // TODO : Do something to retrieve recipe widget
            return cursor;

        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(LOG_TAG, "Getting type from recipe widget dir");
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_WIDGET_DIR:
                return "vnd.android.cursor.dir/" + RecipeWidgetContract.AUTHORITY + "." + RecipeWidgetContract.URI_RECIPE_WIDGET_PATH;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.i(LOG_TAG, "Inserting recipe widget");
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_WIDGET_DIR:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                // TODO : Do something to insert
                final long id = 0;
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.i(LOG_TAG, "Removing all recipe widget entries");
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_WIDGET_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = 0;
                // TODO : do something to remove all
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.i(LOG_TAG, "Calling update on Recipe Widget Provider - not implemented yet");
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
}
