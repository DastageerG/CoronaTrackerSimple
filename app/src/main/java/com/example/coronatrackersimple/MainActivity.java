package com.example.coronatrackersimple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private TextView textViewInfected , textViewDeaths , textViewRecovered , textViewNewCases
            ,textViewNewDeaths , textViewCountries , textViewLoadingText;
    private ProgressBar progressBar;
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



    } // onCreate closed


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
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
                break;
        } // switch closed
        return super.onOptionsItemSelected(item);
    }
} // MainActivity closed