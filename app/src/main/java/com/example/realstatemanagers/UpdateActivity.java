package com.example.realstatemanagers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.realstatemanagers.viewmodel.AddViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateActivity extends AppCompatActivity {

    AddViewModel mAddViewModel;
    Button mButton;
    TextInputLayout modDesc;
    TextInputLayout modType;
    TextInputLayout modVal;
    TextInputLayout modSurface;
    TextInputLayout modNbrePiece;
    TextInputLayout modAdr;
    TextInputLayout modStatut;
    TextInputLayout modAgent;

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

        Intent intent = getIntent();
        String id = intent.getStringExtra("mId");

        this.mAddViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(AddViewModel.class);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Possession possession = new Possession(id,
                        modDesc.getEditText().getText().toString(),
                        modType.getEditText().getText().toString(),
                        modVal.getEditText().getText().toString(),
                        modSurface.getEditText().getText().toString(),
                        modNbrePiece.getEditText().getText().toString(),
                        null,
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
            }
        });
    }

    public void updatePossession(Possession possession){
        this.mAddViewModel.updatePossession(possession);}
}