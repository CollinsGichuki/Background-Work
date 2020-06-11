package com.snilloc.workmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.snilloc.workmanager.MainActivity.KEY_TASK_DESCRIPTION;
import static com.snilloc.workmanager.MainActivity.amount;

public class MyWorker extends Worker {
    public static final String NOTIFICATION_CHANNEL_ID = "com.snilloc.workmanager";
    public static final int NOTIFICATION_ID = 1;
    public static final String KEY_TASK_OUTPUT = "key_task_output";

    //Constructor matching Super
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //This is where we will do all the work that is to be done in the background
    @NonNull
    @Override
    public Result doWork() {
        //The work
        //Get the data sent
        Data data = getInputData();
        String description = data.getString(KEY_TASK_DESCRIPTION);//Use the key of the data from the MainActivity
        displayNotification("Hey i am your work", description);

        //Output data when work is finished
        Data data1 = new Data.Builder()
                .putString(KEY_TASK_OUTPUT, "Task is finished successfully")
                .build();
        return Result.success(data1);
    }

    private void displayNotification(String task, String desc) {
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        //Notification Channel is only in Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "WorkManager",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notifications for the WorkManager App");

            manager.createNotificationChannel(channel);
        }
        //Create a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher);
        //Display the notification
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
