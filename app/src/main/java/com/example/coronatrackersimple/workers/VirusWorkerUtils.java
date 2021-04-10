package com.example.coronatrackersimple.workers;

import android.content.ContentResolver;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.work.Worker;

import com.example.coronatrackersimple.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public final class VirusWorkerUtils
{
    public static final String TAG = "1234";

    @WorkerThread
    public static String processJson(String inputUrl)
    {
        try
        {
            URL url = new URL(Constants.URL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNext())
            {
                stringBuilder.append(scanner.next());
            }// while closed
            Log.d(TAG, "processJson: "+stringBuilder.toString());
            scanner.close();
            return stringBuilder.toString();
        }catch (IOException e)
        {
            Log.d(TAG, "processJson: ");
            return e.getMessage();
        } // catch closed

    } // processJson closed

} // VirusWorker Util closed
