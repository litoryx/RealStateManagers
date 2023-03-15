package com.example.realstatemanagers.model;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.realstatemanagers.MainActivity;
import com.example.realstatemanagers.R;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWork extends Worker {


    Intent intent;

    public MyWork(
            @NonNull Context context,
            @NonNull WorkerParameters params)
    {
        super(context, params);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission")
    @NonNull
    @Override
    public Result doWork() {

        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String title = "Ajout effectué";
        String textContent = "L'ajout de l'utilisateur a été effectué";

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "11")
                .setSmallIcon(R.drawable.baseline_home_24)
                .setContentTitle(title)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(11, builder.build());
        return Result.success();
    }
}

