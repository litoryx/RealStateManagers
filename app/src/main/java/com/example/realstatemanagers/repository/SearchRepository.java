package com.example.realstatemanagers.repository;

import android.text.TextUtils;
import android.util.Log;

import com.example.realstatemanagers.MainApplication;
import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.SearchCriteria;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.sqlite.db.SimpleSQLiteQuery;

import static okhttp3.internal.Internal.instance;

public class SearchRepository {

    private static volatile SearchRepository instance;
    PossessionDAO mPossessionDAO;
    LiveData<List<Possession>> search;

    private MutableLiveData<SearchCriteria> searchTextLivedata = new MutableLiveData<>(null);

    public SearchRepository(PossessionDAO possessionDAO) {
        mPossessionDAO = possessionDAO;

    }

    public LiveData<SearchCriteria> getSearchTextLivedata() {
        return searchTextLivedata;
    }

    public static SearchRepository getInstance() {
        SearchRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SearchRepository.class) {
            if (instance == null) {
                instance = new SearchRepository(SavePosMyDataBase.getInstance(MainApplication.getApplication()).mPossessionDAO());
            }
            return instance;
        }
    }

    public void updateSearch(SearchCriteria searchCriteria){

        searchTextLivedata.setValue(searchCriteria);
    }

    public LiveData<List<Possession>> getPossessionS(){ return search; }
}
