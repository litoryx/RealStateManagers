package com.example.realstatemanagers.model;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.realstatemanagers.DetailActivity;
import com.example.realstatemanagers.R;

import androidx.annotation.NonNull;
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

    @SuppressLint("MissingPermission")
    @NonNull
    @Override
    public Result doWork() {

        intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String title = "L'heure de déjeuner";
        String textContent = "Venez choisir votre restaurant pas très loin avant d'aller le chercher à la main. lol";

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "12")
                .setSmallIcon(R.drawable.baseline_home_24)
                .setContentTitle(title)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(12, builder.build());
        return Result.success();
    }
}

