package com.example.realstatemanagers.viewmodel;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.repository.PossessionRepository;

import java.util.concurrent.Executor;

import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {

    PossessionRepository DataSource;
    private Executor executor;

    public AddViewModel(PossessionRepository dataSource,Executor executor) {
        this.DataSource = dataSource;
        this.executor = executor;
    }

    public void insertPossession(Possession possession){

        executor.execute(() -> {
            DataSource.insertPossession(possession);
        });
    }

    public void updatePossession(Possession possession){

        executor.execute(() -> DataSource.updatePossession(possession));
    }
}
