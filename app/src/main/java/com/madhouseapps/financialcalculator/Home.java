package com.madhouseapps.financialcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.Helper.MadHouseDialog;
import com.madhouseapps.financialcalculator.Helper.PreferenceManager;

import java.util.Random;

public class Home extends AppCompatActivity {

    TextView MAD, HOUSE, Quote, Author, EMIButton, SIPButton, RDButton, FDButton, RPButton;
    Typeface poppins_bold;
    Typeface poppins_regular;
    TextView FAQs;
    TextView Share;
    TextView Rate;

    String quotes[];
    String authors[];
    String emDash;

    final int PERMISSION_CODE = 13;

    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        poppins_bold = Typeface.createFromAsset(getAssets(), "fonts/poppinsb.ttf");
        poppins_regular = Typeface.createFromAsset(getAssets(), "fonts/poppinsr.ttf");

        quotes = getResources().getStringArray(R.array.quotes);
        authors = getResources().getStringArray(R.array.author);
        emDash = getResources().getString(R.string.emdash);


        MAD = (TextView) findViewById(R.id.MAD);
        HOUSE = (TextView) findViewById(R.id.HOUSE);
        Quote = (TextView) findViewById(R.id.Quote);
        Author = (TextView) findViewById(R.id.Author);
        EMIButton = (TextView) findViewById(R.id.EMIButton);
        RDButton = (TextView) findViewById(R.id.RDButton);
        SIPButton = (TextView) findViewById(R.id.SIPButton);
        FDButton = (TextView) findViewById(R.id.FDButton);
        RPButton = (TextView) findViewById(R.id.RPButton);
        FAQs = (TextView) findViewById(R.id.FAQButton);
        Share = (TextView) findViewById(R.id.ShareButton);
        Rate = (TextView) findViewById(R.id.RateButton);

        MAD.setTypeface(poppins_regular);
        HOUSE.setTypeface(poppins_bold);
        Quote.setTypeface(poppins_bold);
        Author.setTypeface(poppins_regular);
        EMIButton.setTypeface(poppins_bold);
        RDButton.setTypeface(poppins_bold);
        FDButton.setTypeface(poppins_bold);
        SIPButton.setTypeface(poppins_bold);
        RPButton.setTypeface(poppins_bold);
        Rate.setTypeface(poppins_bold);

        preferenceManager = new PreferenceManager(this);
        int reportCount = preferenceManager.getIntegerPreferences("ReportCount");
        if(reportCount!=0 && reportCount%6==0){
            MadHouseDialog madHouseDialog = new MadHouseDialog(Home.this);
            madHouseDialog.show();
        }

        preferenceManager.addIntegerPreference("ReportCount", preferenceManager.getIntegerPreferences("ReportCount")+1);


        //TODO: Remove this
        HOUSE.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MadHouseDialog madHouseDialog = new MadHouseDialog(Home.this);
                madHouseDialog.show();
                return true;
            }
        });

        /*
         * Getting a random number between 0 to length of quotes array
         */
        Random random = new Random();
        int quote_number = random.nextInt(quotes.length);
        Quote.setText(quotes[quote_number]);
        Author.setText(String.format("%s %s", emDash, authors[quote_number]));

        EMIButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calculations.class);
                intent.putExtra("Choice", 1);
                startActivity(intent);
            }
        });

        FDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calculations.class);
                intent.putExtra("Choice", 2);
                startActivity(intent);
            }
        });

        RDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calculations.class);
                intent.putExtra("Choice", 3);
                startActivity(intent);
            }
        });

        SIPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calculations.class);
                intent.putExtra("Choice", 4);
                startActivity(intent);
            }
        });

        RPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Calculations.class);
                intent.putExtra("Choice", 5);
                startActivity(intent);
            }
        });

        FAQs.setTypeface(poppins_bold);
        Share.setTypeface(poppins_bold);

        FAQs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FAQActivity.class);
                startActivity(intent);
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Check Out This Excellent Financial App: https://play.google.com/store/apps/details?id=com.madhouseapps.financialcalculator");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        askForPermission();


    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission required to save report!", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
