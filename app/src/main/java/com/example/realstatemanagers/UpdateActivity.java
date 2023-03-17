package com.example.realstatemanagers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.realstatemanagers.viewmodel.AddViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateActivity extends AppCompatActivity {

    AddViewModel mAddViewModel;
    Button mButton;
    Button mButtonGal;
    FloatingActionButton mButtonBack;
    TextInputLayout modDesc;
    TextInputLayout modType;
    TextInputLayout modVal;
    TextInputLayout modSurface;
    TextInputLayout modNbrePiece;
    TextInputLayout modAdr;
    TextInputLayout modStatut;
    TextInputLayout modAgent;
    ImageView imageView;
    String imgDecodableString;
    private final int STORAGE_PERMISSION_CODE = 23;
    private final int GALLERY_REQUEST_CODE=24;
    Possession possession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        modDesc = findViewById(R.id.mod_desc);
        modType = findViewById(R.id.mod_poss);
        modVal = findViewById(R.id.mod_val);
        modSurface = findViewById(R.id.mod_surface);
        modNbrePiece = findViewById(R.id.mod_nbrePiece);
        modAdr = findViewById(R.id.mod_adr);
        modStatut = findViewById(R.id.mod_statut);
        modAgent = findViewById(R.id.mod_agent);
        mButton = findViewById(R.id.buttonMod);
        mButtonGal = findViewById(R.id.buttonGal);
        mButtonBack = findViewById(R.id.buttonBack);
        imageView = (ImageView) findViewById(R.id.imageView5);

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");

        this.mAddViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(AddViewModel.class);

        if(imgDecodableString == null){imgDecodableString = null;}
        Log.d("verifimg",""+imgDecodableString);

        mButton.setOnClickListener(v -> {
            int surface = Integer.parseInt(modSurface.getEditText().getText().toString());

            Log.d("verifimg",""+imgDecodableString);
            possession = new Possession(id,
                    modDesc.getEditText().getText().toString(),
                    modType.getEditText().getText().toString(),
                    modVal.getEditText().getText().toString(),
                    surface,
                    modNbrePiece.getEditText().getText().toString(),
                    imgDecodableString,
                    modAdr.getEditText().getText().toString(),
                    null,
                    modStatut.getEditText().getText().toString(),
                    null,
                    null,
                    modAgent.getEditText().getText().toString());
            updatePossession(possession);

            Intent activityIntent = new Intent(UpdateActivity.this, DetailActivity.class);
            activityIntent.putExtra("mPossMod",possession);
            UpdateActivity.this.setResult(Activity.RESULT_OK, activityIntent);
            UpdateActivity.this.finish();
        });


        mButtonBack.setOnClickListener(v -> {
            Intent activityIntent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(activityIntent);
        });
    }

    public void updatePossession(Possession possession){
        this.mAddViewModel.updatePossession(possession);}
}