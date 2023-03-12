package com.example.realstatemanagers;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.realstatemanagers.provider.PossessionContentProvider;
import com.example.realstatemanagers.repository.SavePosMyDataBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class PossessionContentProviderTest {
    // FOR DATA

    private ContentResolver mContentResolver;

    // DATA SET FOR TEST

    private static long USER_ID = 1;

    @Before

    public void setUp() {

        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext()
                ,

                SavePosMyDataBase.class)


                .allowMainThreadQueries()


                .build();


        mContentResolver = InstrumentationRegistry.getInstrumentation().getContext()

                .getContentResolver();

    }

    @Test

    public void getItemsWhenNoItemInserted() {

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PossessionContentProvider.URI_ITEM, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());

        assertThat(cursor.getCount(), is(0));

        cursor.close();

    }

    @Test

    public void insertAndGetItem() {

        // BEFORE : Adding demo item

        final Uri userUri = mContentResolver.insert(PossessionContentProvider.URI_ITEM, generateItem());

        // TEST

        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(PossessionContentProvider.URI_ITEM, USER_ID), null, null, null, null);

        assertThat(cursor, notNullValue());

        assertThat(cursor.getCount(), is(1));

        assertThat(cursor.moveToFirst(), is(true));

        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("text")), is("Visite cet endroit de rêve !"));

    }

    // ---

    private ContentValues generateItem(){

        final ContentValues values = new ContentValues();

        values.put("text", "Visite cet endroit de rêve !");

        values.put("category", "0");

        values.put("isSelected", "false");

        values.put("userId", "1");

        return values;

    }
}
