package com.example.coronatrackersimple.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.Data;
import androidx.work.WorkerParameters;

import com.example.coronatrackersimple.Constants;

public class DownloadJsonWorker extends Worker
{
    public static final String TAG = "1234";
    public DownloadJsonWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        String inputUrl = getInputData().getString(Constants.STRING_URL);


        Data data = new Data.Builder()
                .putString(Constants.DATA_OUTPUT,VirusWorkerUtils.processJson(inputUrl))
                .build();
        return Worker.Result.success(data);
    }
}
