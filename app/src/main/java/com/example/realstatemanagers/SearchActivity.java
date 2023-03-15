package com.example.realstatemanagers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.realstatemanagers.model.SearchCriteria;
import com.example.realstatemanagers.viewmodel.MainViewModel;
import com.example.realstatemanagers.viewmodel.SearchViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class SearchActivity extends AppCompatActivity {

    TextInputLayout searchType;
    TextInputLayout searchDatebegins;
    TextInputLayout searchAdresse;
    TextInputLayout searchAgent;
    TextInputLayout minNbrePiece;
    TextInputLayout maxNbrePiece;
    TextInputLayout minSurface;
    TextInputLayout maxSurface;
    TextInputLayout minPrice;
    TextInputLayout maxPrice;
    SearchViewModel mSearchViewModel;
    RecyclerView mRecyclerView;
    AppCompatButton mButtonSearch;
    FloatingActionButton mButtonBack;
    ListPossessionRecyclerAdapter.Listener mListener = SearchActivity.this::onItemClick;

    ListPossessionRecyclerAdapter mListPoss = new ListPossessionRecyclerAdapter(mListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchType = findViewById(R.id.search_poss);
        searchDatebegins = findViewById(R.id.search_dateBegins);
        searchAdresse = findViewById(R.id.search_adresse);
        searchAgent = findViewById(R.id.search_agent);
        minNbrePiece = findViewById(R.id.search_minNbrePiece);
        maxNbrePiece = findViewById(R.id.search_maxNbrePiece);
        minSurface = findViewById(R.id.search_minSurface);
        maxSurface = findViewById(R.id.search_maxSurface);
        minPrice = findViewById(R.id.search_minPrice);
        maxPrice = findViewById(R.id.search_maxPrice);
        mButtonSearch = findViewById(R.id.buttonSearch);
        mButtonBack = findViewById(R.id.buttonBack);
        LinearLayout layone = (LinearLayout) findViewById(R.id.linearLayout10);

        mSearchViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(SearchViewModel.class);

        mRecyclerView = findViewById(R.id.list_possessionSearch);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mListPoss);

        mButtonBack.setOnClickListener(v -> {
            Intent activityIntent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(activityIntent);
        });

        mButtonSearch.setOnClickListener(v -> {
            layone.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            int minPiece = 0;
            int maxPiece = 9999;
            int minSur = 0;
            int maxSur = 99999;
            int minPri = 0;
            int maxPri = 999999999;
            String search = null;
            String dateBegins = null;
            String adresse = null;
            String agent = null;

            if (minNbrePiece.getEditText() != null) {
                if (!TextUtils.isEmpty(minNbrePiece.getEditText().getText().toString())) {
                    minPiece = Integer.parseInt(minNbrePiece.getEditText().getText().toString());
                }
            }
            if (maxNbrePiece.getEditText() != null) {
                if (!TextUtils.isEmpty(maxNbrePiece.getEditText().getText().toString())) {
                    maxPiece = Integer.parseInt(maxNbrePiece.getEditText().getText().toString());
                }
            }
            if (minSurface.getEditText() != null) {
                if (!TextUtils.isEmpty(minSurface.getEditText().getText().toString())) {
                    Log.d("minSurfFoutafge", minSurface.getEditText().getText().toString());
                    minSur = Integer.parseInt(minSurface.getEditText().getText().toString());
                }
            }
            if (maxSurface.getEditText() != null) {
                if (!TextUtils.isEmpty(maxSurface.getEditText().getText().toString())) {
                    maxSur = Integer.parseInt(maxSurface.getEditText().getText().toString());
                }
            }
            if (minPrice.getEditText() != null) {
                if (!TextUtils.isEmpty(minPrice.getEditText().getText().toString())) {
                    minPri = Integer.parseInt(minPrice.getEditText().getText().toString());
                }
            }
            if (maxPrice.getEditText() != null) {
                if (!TextUtils.isEmpty(maxPrice.getEditText().getText().toString())) {
                    maxPri = Integer.parseInt(maxPrice.getEditText().getText().toString());
                }
            }
            if (searchType.getEditText() != null) {
                if (!TextUtils.isEmpty(searchType.getEditText().getText().toString())) {
                    search = searchType.getEditText().getText().toString();
                }
            }
            if (searchDatebegins.getEditText() != null) {
                if (!TextUtils.isEmpty(searchDatebegins.getEditText().toString())) {
                    dateBegins = searchDatebegins.getEditText().toString();
                }
            }
            if (searchAdresse.getEditText() != null) {
                if (!TextUtils.isEmpty(searchAdresse.getEditText().toString())) {
                    adresse = searchAdresse.getEditText().toString();
                }
            }
            if (searchAgent.getEditText() != null) {
                if (!TextUtils.isEmpty(searchAgent.getEditText().toString())) {
                    agent = searchAgent.getEditText().toString();
                }
            }

            SearchCriteria searchCriteria = new SearchCriteria(search,
                    dateBegins, adresse, agent,
                    minPiece, maxPiece, minSur, maxSur, minPri, maxPri, true);

          getFinalPossessionSearch(searchCriteria);
          finish();
        });
    }

    public void getFinalPossessionSearch(SearchCriteria searchCriteria){
        mSearchViewModel.updateSearch(searchCriteria);
    }

    public void onItemClick(Possession possession){
            Intent activityIntent = new Intent(SearchActivity.this, DetailActivity.class);
            activityIntent.putExtra("mPossession", possession);
            startActivity(activityIntent, null);
    }
}