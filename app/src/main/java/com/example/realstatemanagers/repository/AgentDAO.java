package com.example.realstatemanagers.repository;


import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.Agent;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AgentDAO {

    @Query("select * from Agent")
    LiveData<List<Agent>> getAgent();

    @Insert
    long InsertAgent(Agent agent);
}
