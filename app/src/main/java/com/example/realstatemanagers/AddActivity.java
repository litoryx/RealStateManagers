package com.example.realstatemanagers;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.realstatemanagers.model.MyWork;
import com.example.realstatemanagers.viewmodel.AddViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton mButtonBack;

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
        mButtonBack = findViewById(R.id.buttonBack);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        this.mAddViewModel = new ViewModelProvider(this, MainViewModelFactory.getInstance()).get(AddViewModel.class);
        String dateString = s.format(date);
        long id = (long) (Math.random() * 50000);
        String idString = Long.toString(id);

        mButton.setOnClickListener(v -> {
            int surface = Integer.parseInt(addSurface.getEditText().getText().toString());

            Possession possession = new Possession(idString,
                    addDesc.getEditText().getText().toString(),
                    addType.getEditText().getText().toString(),
                    addVal.getEditText().getText().toString(),
                    surface,
                    addNbrePiece.getEditText().getText().toString(),
                    null,
                    addAdr.getEditText().getText().toString(),
                    null,
                    addStatut.getEditText().getText().toString(),
                    dateString,
                    null,
                    addAgent.getEditText().getText().toString());
            addPossession(possession);
            createNotificationChannel();
            WorkRequest myWorkRequest = OneTimeWorkRequest.from(MyWork.class);
            WorkManager.getInstance(getApplicationContext()).enqueue(myWorkRequest);
            finish();
        });

        mButtonBack.setOnClickListener(v -> {
            Intent activityIntent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(activityIntent);
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel 1";
            String description = "getString(R.string.channel_description)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("11", name, importance);
            channel.setDescription(description);
            //j'ai laissé createNotificationChannel dans l'activity pour getSystemService qui en as besoin
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

public void addPossession(Possession possession){
        this.mAddViewModel.insertPossession(possession);}
}