package com.snilloc.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static com.snilloc.workmanager.MyWorker.KEY_TASK_OUTPUT;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_TASK_DESCRIPTION = "key_task_desc";
    public static int amount = 0;
    WorkViewModel fWorkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewModel
        fWorkViewModel = new ViewModelProvider(this).get(WorkViewModel.class);

        TextView tv = findViewById(R.id.textView);

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the Work
                fWorkViewModel.doTheWork();
            }
        });

//        //fWorkViewModel.getWorkStatus().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                tv.setText(s);
//            }
//        });

//        String output = fWorkViewModel.getWorkStatus();
//        tv.setText("Number of times work done: " + amount);

//        //Display the status of the work
//        final TextView textView = findViewById(R.id.textView);
//        //Get the status in LiveData so that it can be observable
//        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(request.getId())
//                .observe(this, new Observer<WorkInfo>() {
//                    @Override
//                    public void onChanged(WorkInfo workInfo) {
//                        if (workInfo != null) {
//                            //Check if the work is finished
//                            if (workInfo.getState().isFinished()){
//                                //Get the output data
//                                Data data1 = workInfo.getOutputData();
//                                String outputData = data1.getString(KEY_TASK_OUTPUT);
//                                textView.append(outputData + "\n");
//                            }
//                            //Get the status from the WorkInfo Object
//                            String status = workInfo.getState().name();
//                            //Append the status of the work
//                            textView.append(status + "\n");
//                        }
//                    }
//                });

    }
}
