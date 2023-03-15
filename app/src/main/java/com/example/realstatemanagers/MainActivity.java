package com.example.realstatemanagers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.realstatemanagers.repository.Utils;
import com.example.realstatemanagers.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMain;
    private TextView textViewquantityEtoD;
    private TextView textViewQuantity;
    private TextView textViewEtoD;
    private TextView connexion;
    FloatingActionButton mAddButton;
    FloatingActionButton mGeoButton;
    FloatingActionButton mSearchButton;
    RecyclerView mRecyclerView;
    MainViewModel mMainViewModel;
    ListPossessionRecyclerAdapter.Listener mListener = MainActivity.this::onItemClick;

    ListPossessionRecyclerAdapter mListPoss = new ListPossessionRecyclerAdapter(mListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);
        textViewquantityEtoD = findViewById(R.id.activity_main_quantity_EurotoDoll);
        textViewEtoD = findViewById(R.id.activity_main_EurotoDoll);
        connexion = findViewById(R.id.connexion);
        mAddButton = findViewById(R.id.add_possession);
        mGeoButton = findViewById(R.id.geo_loc);
        mSearchButton = findViewById(R.id.add_search);

        mMainViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(MainViewModel.class);

        mRecyclerView = findViewById(R.id.list_possession);
        mRecyclerView.setContentDescription("listmypossession");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mListPoss);

        getSupportActionBar();

        textViewMain.setVisibility(View.VISIBLE);
        textViewQuantity.setVisibility(View.VISIBLE);
        textViewquantityEtoD.setVisibility(View.VISIBLE);
        textViewEtoD.setVisibility(View.VISIBLE);
        connexion.setVisibility(View.VISIBLE);

        mMainViewModel.getPossession().observe(this, list ->{
            mListPoss.submitList(list);

            Boolean isInternet = Utils.isInternetAvailable(getApplicationContext());
            int mConvertDtoE = Utils.convertDollarToEuro(100);
            int mConvertSEtoD = Utils.convertEuroToDollars(100);
            String mConvertString = getString(R.string.quantity, mConvertDtoE);
            String mConvertEtoDString = getString(R.string.quantity, mConvertSEtoD);

            if (isInternet){connexion.setText(R.string.connect_test);}else{connexion.setText(R.string.notconnect_test);}
            textViewQuantity.setText(mConvertString);
            textViewquantityEtoD.setText(mConvertEtoDString);
        });

        mAddButton.setOnClickListener(v -> {
            Intent activityIntent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(activityIntent);
        });

        mSearchButton.setOnClickListener(v -> {
            Intent activityIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(activityIntent);
        });


        mGeoButton.setOnClickListener(v -> {
            Intent activityIntent = new Intent(MainActivity.this, GeoActivity.class);
            startActivity(activityIntent);
        });


    }

    public void onItemClick(Possession possession){
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);

        if(isTablet){
            textViewMain.setVisibility(View.GONE);
            textViewQuantity.setVisibility(View.GONE);
            textViewquantityEtoD.setVisibility(View.GONE);
            textViewEtoD.setVisibility(View.GONE);
            connexion.setVisibility(View.GONE);
            Fragment fragment = DetailFragment.newInstance(possession);

            getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.detail_container, fragment).commit();
        }else{
            Intent activityIntent = new Intent(MainActivity.this, DetailActivity.class);
            activityIntent.putExtra("mPossession", possession);
            startActivity(activityIntent, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                return true;
            case android.R.id.edit:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        mMainViewModel.refresh();
    }
}