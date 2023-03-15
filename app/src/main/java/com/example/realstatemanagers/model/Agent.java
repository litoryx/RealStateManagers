package com.example.realstatemanagers.model;

import com.example.realstatemanagers.Possession;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Agent {


    @NonNull
    @PrimaryKey
    String id;
    String name;
    String password;
    String adr_agent;

    public Agent(@NonNull String id, String name, String password, String adr_agent) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.adr_agent = adr_agent;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAdr_agent() {
        return adr_agent;
    }
}
