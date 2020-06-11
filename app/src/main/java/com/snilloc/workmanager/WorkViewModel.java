package com.snilloc.workmanager;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import static com.snilloc.workmanager.MainActivity.KEY_TASK_DESCRIPTION;
import static com.snilloc.workmanager.MainActivity.amount;
import static com.snilloc.workmanager.MyWorker.KEY_TASK_OUTPUT;

public class WorkViewModel extends AndroidViewModel {
    private WorkManager fWorkManager;
    private PeriodicWorkRequest periodicWorkRequest;

    private MutableLiveData<String> workStatus;

    public WorkViewModel(@NonNull Application application) {
        super(application);
        fWorkManager = WorkManager.getInstance(application);
    }

    void doTheWork() {
        //Sending data to MyWorker class for the Notification message
        Data data = new Data.Builder()
                .putString(KEY_TASK_DESCRIPTION, "Hey, i am sending the work data")
                .build();

        //Constraints for the work(notification)
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();

        //Implement WorkRequest subClass OneTimeRequest and define the worker
        periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .build();

        fWorkManager.enqueueUniquePeriodicWork("Periodic Work", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
    }

    public MutableLiveData<String> getWorkStatus() {
        fWorkManager.getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe((LifecycleOwner) this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null) {
                            //Check if the work is finished
                            if (workInfo.getState().isFinished()) {
                                //Get the output data
                                workStatus.setValue(workInfo.getState().name());
                            }
                            //Get the status from the WorkInfo Object
                            String status = workInfo.getState().name();
                            //Append the status of the work
                            //textView.append(status + "\n");
                        }
                    }
                });
        return workStatus;
    }
}
