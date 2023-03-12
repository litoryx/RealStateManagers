package com.example.realstatemanagers.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.repository.SavePosMyDataBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PossessionContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.realstatemanagers.provider";

    public static final String TABLE_NAME = Possession.class.getSimpleName();

    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (getContext() != null) {

            final Cursor cursor = SavePosMyDataBase.getInstance(getContext()).mPossessionDAO().getPossessionWithCursor();

            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
         return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {


        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {


        throw new IllegalArgumentException("Failed to update row into " + uri);
    }
}
