package com.example.realstatemanagers.repository;

import android.database.Cursor;

import com.example.realstatemanagers.Possession;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;



@Dao
public interface PossessionDAO {

    @Query("select * from Possession")
    LiveData<List<Possession>> getPossession();

    @RawQuery(observedEntities = Possession.class)
    LiveData<List<Possession>> getPossessionsAsk(SupportSQLiteQuery query);

    @Query("select * from Possession")
    Cursor getPossessionWithCursor();

    @Query("delete from Possession where id = :possessionId")
    int DeletePoss(long possessionId);

    @Update
    void updatePossessionWithCursor(Possession possession);

    @Insert
    long InsertPoss(Possession possession);

    @Update
    int updatePossession(Possession possession);
}
