package com.example.coronatrackersimple.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Data;
import com.example.coronatrackersimple.Constants;
import com.example.coronatrackersimple.workers.CleanUpWorker;
import com.example.coronatrackersimple.workers.DownloadJsonWorker;

import java.util.List;

public class VirusViewModel extends AndroidViewModel
{
    public static final String TAG = "1234";

    public static final String APiURL = "api_url";
    private WorkManager workManager;
    private LiveData<List<WorkInfo>>savedWorkInfo;
    private String outputData;


    public VirusViewModel(@NonNull Application application)
    {
        super(application);
        workManager = WorkManager.getInstance(application);
        savedWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT);
    } // ViewModel constructor closed


    public void setOutputData(String outputData)
    {
        this.outputData = outputData;
    } // setOutPutData closed


    public String getOutputData()
    {
        return outputData;
    } // getOutPut data closed
    public LiveData<List<WorkInfo>>getSavedWorkInfo()
    {
        return savedWorkInfo;
    }

    public void downloadJson()
    {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        WorkContinuation workContinuation = workManager.beginUniqueWork(Constants.JSON_PROCESSING_WORK_NAME
        , ExistingWorkPolicy.REPLACE, OneTimeWorkRequest.from(CleanUpWorker.class));

        Log.d(TAG, "downloadJson: "+createInputUrl());
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DownloadJsonWorker.class)
                .setInputData(createInputUrl())
                .addTag(Constants.TAG_OUTPUT)
                .setConstraints(constraints)
                .build();
            workContinuation = workContinuation.then(request);
            workContinuation.enqueue();

    } // downloadJson closed

    public void cancelWork()
    {
        workManager.cancelUniqueWork(Constants.JSON_PROCESSING_WORK_NAME);
    } // cancelWork closed


    private Data createInputUrl()
    {
        Data.Builder data = new Data.Builder();
                data.putString(Constants.STRING_URL,Constants.URL);
        return data.build();
    } // createInputUrl closed

} // VirusViewModel class closed
