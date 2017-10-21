package com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract.AUTHORITY;
import static com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract.RecipeWidgetEntry;
import static com.pedrolopesme.android.bakingapp.integration.contentProvider.recipeWidget.RecipeWidgetContract.URI_RECIPE_WIDGET_PATH;

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

    /**
     * The DB Helper
     */
    private RecipeWidgetDBHelper dbHelper;

    static {
        MATCHER.addURI(AUTHORITY, URI_RECIPE_WIDGET_PATH, CODE_RECIPE_WIDGET_DIR);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new RecipeWidgetDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(LOG_TAG, "Querying Recipe Widget Provider");
        final int code = MATCHER.match(uri);
        if (code == CODE_RECIPE_WIDGET_DIR) {

            final Context context = getContext();
            if (context == null) {
                return null;
            }

            final Cursor cursor = dbHelper.getReadableDatabase().query(
                    RecipeWidgetEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
            ;

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
                return "vnd.android.cursor.dir/" + AUTHORITY + "." + URI_RECIPE_WIDGET_PATH;
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

                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    final long id = db.insert(RecipeWidgetEntry.TABLE_NAME, null, contentValues);
                    if (id != -1) {
                        db.setTransactionSuccessful();
                        return RecipeWidgetContract.URI_RECIPE_WIDGET.buildUpon().appendPath(String.valueOf(id)).build();
                    }
                } catch (Exception ex) {
                    Log.e(LOG_TAG, "It was impossible to insert values", ex);
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(LOG_TAG, "Removing all recipe widget entries");
        switch (MATCHER.match(uri)) {
            case CODE_RECIPE_WIDGET_DIR:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = dbHelper.getWritableDatabase().delete(
                        RecipeWidgetEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

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
