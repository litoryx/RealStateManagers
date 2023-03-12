package com.example.realstatemanagers;


import android.content.Context;

import com.example.realstatemanagers.repository.PossessionDAO;
import com.example.realstatemanagers.repository.SavePosMyDataBase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class PossessionTestDAO {

    private PossessionDAO mPossessionDAO;
    private SavePosMyDataBase db1;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db1 = Room.inMemoryDatabaseBuilder(context, SavePosMyDataBase.class).build();
        mPossessionDAO = db1.mPossessionDAO();
        Possession posAutoAdd = new Possession("012",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025","350m²","9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22","",
                "Mickael JEAN");
        db1.mPossessionDAO().InsertPoss(posAutoAdd);
    }

    @After
    public void closeDb() throws IOException {
        db1.close();
    }

    @Test
    public void writePossessionAndReadInList() throws InterruptedException {
        Possession posAutoAdd = new Possession("024",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025","350m²","9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22","",
                "Mickael JEAN");
        db1.mPossessionDAO().InsertPoss(posAutoAdd);
        List<Possession> byPossession = LiveDataTestUtil.getOrAwaitValue(mPossessionDAO.getPossession());
        assertThat(byPossession.get(1), equalTo(posAutoAdd));
        assertThat(byPossession.size(), equalTo(2));
    }

    @Test
    public void testUpdatePossession() throws InterruptedException {
        Possession possession1 = new Possession("032",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025","350m²","9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22","",
                "Mickael JEAN");
        Possession possessionUpdate = new Possession("056",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025","350m²","9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22","",
                "Mickael JEAN");
        db1.mPossessionDAO().InsertPoss(possession1);
        db1.mPossessionDAO().updatePossessionWithCursor(possessionUpdate);
        List<Possession> byPossession = LiveDataTestUtil.getOrAwaitValue(mPossessionDAO.getPossession());
        assertThat(possessionUpdate, equalTo(byPossession.get(1)));
    }

    @Test
    public void testRemoveTask() throws InterruptedException {
        Possession possession1 = new Possession("032",
                "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
                "19450025","350m²","9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
                "Chicago Centre","Starbuck, BNP",
                "Non vendu","03/12/22","",
                "Mickael JEAN");
        String taskId = possession1.getId();
        int posId = Integer.parseInt(taskId);
        db1.mPossessionDAO().InsertPoss(possession1);
        db1.mPossessionDAO().DeletePoss(012);
        db1.mPossessionDAO().DeletePoss(posId);
        List<Possession> byPossession = LiveDataTestUtil.getOrAwaitValue(mPossessionDAO.getPossession());
        assertTrue(byPossession.isEmpty());
    }
}
