package com.example.coronatrackersimple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;
import androidx.work.Data;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatrackersimple.sharedprefs.SavedPrefs;
import com.example.coronatrackersimple.viewmodel.VirusViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "1234";
    private TextView textViewInfected , textViewDeaths , textViewRecovered , textViewNewCases
            ,textViewNewDeaths , textViewCountries , textViewLoadingText;
    private ProgressBar progressBar;
    private VirusViewModel virusViewModel;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");


        textViewInfected = findViewById(R.id.textViewMainActivityInfected);
        textViewDeaths = findViewById(R.id.textViewMainActivityDeaths);
        textViewRecovered = findViewById(R.id.textViewMainActivityRecovered);
        textViewNewCases = findViewById(R.id.textViewMainActivityNewCases);
        textViewNewDeaths = findViewById(R.id.textViewMainActivityNewDeaths);
        textViewCountries = findViewById(R.id.textViewMainActivityCountries);
        textViewLoadingText = findViewById(R.id.textViewLoadingText);
        progressBar = findViewById(R.id.progressBarMainActivity);
        relativeLayout = findViewById(R.id.relativeLayoutStatus);


        virusViewModel = new ViewModelProvider(this).get(VirusViewModel.class);
        getCovidStatus();
    } // onCreate closed

    private void getCovidStatus()
    {
        virusViewModel.getSavedWorkInfo().observe(MainActivity.this, new Observer<List<WorkInfo>>()
        {
            @Override
            public void onChanged(List<WorkInfo> workInfos)
            {
                if(workInfos==null && workInfos.isEmpty())
                {
                    Log.d(TAG, "onChanged: workinfo null"+workInfos.toString());
                    return;
                } // if workInfo null closed
                else
                {
                    WorkInfo workInfo = workInfos.get(0);
                    boolean finished = workInfo.getState().isFinished();
                    if (finished)
                    {
                        Data data = workInfo.getOutputData();
                        String output = data.getString(Constants.DATA_OUTPUT);
                        if(!TextUtils.isEmpty(output))
                        {
                            virusViewModel.setOutputData(output);
                        } // if text empty closed

                        try
                        {
                            populateTheUi();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        } // catch closed

                    } // if finished closed
                    else
                    {
                        showWorkInProgress();
                    } // else closed
                } // else closed
            } // onChanged closed
        });
        virusViewModel.downloadJson();
    } // getVirusStatus closed


    private void populateTheUi() throws JSONException
    {
       String outputData =  virusViewModel.getOutputData();
        JSONObject jsonObject = new JSONObject(outputData);
        Log.d(TAG, "populateTheUi: "+jsonObject.toString());
        textViewInfected.setText(String.format("%,d",jsonObject.getLong("cases")));
        textViewDeaths.setText(String.format("%,d",jsonObject.getLong("deaths")));
        textViewRecovered.setText(String.format("%,d",jsonObject.getLong("recovered")));
        textViewNewCases.setText(String.format("%,d",jsonObject.getLong("todayCases")));
        textViewNewDeaths.setText(String.format("%,d",jsonObject.getLong("todayDeaths")));
        textViewCountries.setText(String.format("%,d",jsonObject.getLong("affectedCountries")));

        processingDone();

    } // populate the Ui closed




    private void processingDone()
    {
        progressBar.setVisibility(View.GONE);
        textViewLoadingText.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void showWorkInProgress()
    {
        progressBar.setVisibility(View.VISIBLE);
        textViewLoadingText.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_refresh,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menuRefresh:
                getCovidStatus();
                break;
        } // switch closed
        return super.onOptionsItemSelected(item);
    }



} // MainActivity closed