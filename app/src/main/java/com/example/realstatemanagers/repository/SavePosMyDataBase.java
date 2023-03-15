package com.example.realstatemanagers.repository;

import android.content.Context;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.Agent;

import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Possession.class, Agent.class}, version = 1, exportSchema = false)

public abstract class SavePosMyDataBase extends RoomDatabase {

    private static SavePosMyDataBase INSTANCE;

    public abstract PossessionDAO mPossessionDAO();
    public abstract AgentDAO mAgentDAO();

    public static Agent posAutoAgent = new Agent("045","Pascal","pouf","6 rue pelleport");

    public static Possession posAutoAdd = new Possession("013",
            "dnufdnbvfdisdio  fhvsdiisin udsubusbub dsfs","Maison",
            "19450025",350,"9","https://firebasestorage.googleapis.com/v0/b/go4lucnch.appspot.com/o/restaurant.jpg?alt=media&token=257934b8-7d69-4d20-80d4-40a58df39a9c",
            "Chicago Centre","Starbuck, BNP",
            "Non vendu","03/12/22",null,
            "045");

    public static SavePosMyDataBase getInstance(Context context) {

        if (INSTANCE == null) {

            synchronized (SavePosMyDataBase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            SavePosMyDataBase.class, "MyPoDatabase.db")

                            .addCallback(prepopulateDatabase())

                            .build();

                }

            }

        }

        return INSTANCE;

    }

    private static RoomDatabase.Callback prepopulateDatabase() {

        return new RoomDatabase.Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {

                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.mAgentDAO().InsertAgent(posAutoAgent));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.mPossessionDAO().InsertPoss(posAutoAdd));
            }

        };

    }
}
