package com.madhouseapps.financialcalculator;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    TextView MAD, HOUSE, Quote, Author, EMIButton, SIPButton, RDButton, FDButton;
    Typeface poppins_bold;
    Typeface poppins_regular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        poppins_bold = Typeface.createFromAsset(getAssets(), "fonts/poppinsb.ttf");
        poppins_regular = Typeface.createFromAsset(getAssets(), "fonts/poppinsr.ttf");


        MAD = (TextView) findViewById(R.id.MAD);
        HOUSE = (TextView) findViewById(R.id.HOUSE);
        Quote = (TextView) findViewById(R.id.Quote);
        Author = (TextView) findViewById(R.id.Author);
        EMIButton = (TextView) findViewById(R.id.EMIButton);
        RDButton = (TextView) findViewById(R.id.RDButton);
        SIPButton = (TextView) findViewById(R.id.SIPButton);
        FDButton = (TextView) findViewById(R.id.FDButton);

        MAD.setTypeface(poppins_regular);
        HOUSE.setTypeface(poppins_bold);
        Quote.setTypeface(poppins_bold);
        Author.setTypeface(poppins_regular);
        EMIButton.setTypeface(poppins_bold);
        RDButton.setTypeface(poppins_bold);
        FDButton.setTypeface(poppins_bold);
        SIPButton.setTypeface(poppins_bold);

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



    }
}
