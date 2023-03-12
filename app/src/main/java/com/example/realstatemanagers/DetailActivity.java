package com.example.realstatemanagers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class DetailActivity extends AppCompatActivity {

    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Possession possession = intent.getParcelableExtra("mPossession");



        Fragment fragment = DetailFragment.newInstance(possession);

        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.detail_container, fragment).commit();
    }
}