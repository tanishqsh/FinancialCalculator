package com.madhouseapps.financialcalculator;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FAQActivity extends AppCompatActivity {

    private TextView FAQTitle;
    private Typeface poppins_bold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        poppins_bold = Typeface.createFromAsset(getAssets(), "fonts/poppinsb.ttf");
        FAQTitle = (TextView) findViewById(R.id.FAQTitle);
        FAQTitle.setTypeface(poppins_bold);
    }
}
