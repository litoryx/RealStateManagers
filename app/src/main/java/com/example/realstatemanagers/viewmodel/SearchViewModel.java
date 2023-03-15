package com.example.realstatemanagers.viewmodel;

import com.example.realstatemanagers.Possession;
import com.example.realstatemanagers.model.SearchCriteria;
import com.example.realstatemanagers.repository.SearchRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {

    SearchRepository mSearchRepository;

    public SearchViewModel(SearchRepository searchRepository) {
        this.mSearchRepository = searchRepository;
    }

    public void updateSearch(SearchCriteria searchCriteria){
        mSearchRepository.updateSearch(searchCriteria);

    }
}
