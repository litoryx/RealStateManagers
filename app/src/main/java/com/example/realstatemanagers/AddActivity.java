package com.example.realstatemanagers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.realstatemanagers.model.MyWork;
import com.example.realstatemanagers.viewmodel.AddViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    AddViewModel mAddViewModel;
    Button mButton;
    TextInputLayout addDesc;
    TextInputLayout addType;
    TextInputLayout addVal;
    TextInputLayout addSurface;
    TextInputLayout addNbrePiece;
    TextInputLayout addAdr;
    TextInputLayout addStatut;
    TextInputLayout addAgent;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addDesc = findViewById(R.id.add_desc);
        addType = findViewById(R.id.add_poss);
        addVal = findViewById(R.id.add_val);
        addSurface = findViewById(R.id.add_surface);
        addNbrePiece = findViewById(R.id.add_nbrePiece);
        addAdr = findViewById(R.id.add_adr);
        addStatut = findViewById(R.id.add_statut);
        addAgent = findViewById(R.id.add_agent);
        mButton = findViewById(R.id.buttonSave);

        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        this.mAddViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(AddViewModel.class);
        String dateString = s.format(date);
        long id = (long) (Math.random() * 50000);
        String idString = Long.toString(id);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Possession possession = new Possession(idString,
                        addDesc.getEditText().getText().toString(),
                        addType.getEditText().getText().toString(),
                        addVal.getEditText().getText().toString(),
                        addSurface.getEditText().getText().toString(),
                        addNbrePiece.getEditText().getText().toString(),
                        null,
                        addAdr.getEditText().getText().toString(),
                        null,
                        addStatut.getEditText().getText().toString(),
                        dateString,
                        null,
                        addAgent.getEditText().getText().toString());
                addPossession(possession);
                WorkRequest myWorkRequest = OneTimeWorkRequest.from(MyWork.class);
                WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);
                finish();
            }
        });
    }

public void addPossession(Possession possession){
        this.mAddViewModel.insertPossession(possession);}
}