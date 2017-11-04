package com.madhouseapps.financialcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.ReportGeneration.RDReport;
import com.madhouseapps.financialcalculator.ReportGeneration.SIPReport;


public class SIPCalculation extends Fragment {


    TextView MVtitle, MVAmount, InterestTitle, InterestAmount;
    TextView DepositTitle;
    EditText DepositInput;
    TextView RateTitle, RatePercent;
    SeekBar RateChanger;
    TextView TenureTitle, TenureOptionsYearly, TenureOptionsMonthly;
    EditText TenureInput;
    Button statsButton, shareButton;
    String emptyLiteral = "-";

    double progress_value = 12;
    double decimalProgress;

    TextWatcher textWatcher;

    /*
true for year
false for month
*/
    boolean mory = true;


    public SIPCalculation() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sipcalculation, container, false);


        MVtitle = (TextView) rootView.findViewById(R.id.MVTitle);
        MVAmount = (TextView) rootView.findViewById(R.id.MVAmount);
        InterestTitle = (TextView) rootView.findViewById(R.id.InterestTitle);
        InterestAmount = (TextView) rootView.findViewById(R.id.InterestAmount);
        shareButton = (Button) rootView.findViewById(R.id.ShareButton);
        DepositTitle = (TextView) rootView.findViewById(R.id.DepositTitle);
        RateTitle = (TextView) rootView.findViewById(R.id.RateTitle);
        RatePercent = (TextView) rootView.findViewById(R.id.RatePercent);
        TenureTitle = (TextView) rootView.findViewById(R.id.TenureTitle);
        TenureOptionsYearly = (TextView) rootView.findViewById(R.id.TenureOptionsYearly);
        TenureOptionsMonthly = (TextView) rootView.findViewById(R.id.TenureOptionsMonthly);

        DepositInput = (EditText) rootView.findViewById(R.id.DepositInput);
        TenureInput = (EditText) rootView.findViewById(R.id.TenureInput);
        RateChanger = (SeekBar) rootView.findViewById(R.id.RateChanger);
        statsButton = (Button) rootView.findViewById(R.id.statsButton);
        RateChanger.setProgress(120);

        setFont();

        DepositInput.setSelection(DepositInput.getText().length());


        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                Integer.parseInt(TenureInput.getText().toString().trim()));


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {

                    if (s.charAt(s.toString().length() - 1) == '.' || s.charAt(0) == '.') {
                        //Dont do anything, wait for him to enter something after the '.'
                        Log.d("DECIMAL", "Successfully Bugged");
                    } else {
                        if (!s.toString().equals("")) {

                            double value = Double.parseDouble(s.toString());
                            value = value * 10;
                            RateChanger.setProgress((int) value);
                            decimalProgress = (float) (Double.parseDouble(s.toString()));
                            progress_value = decimalProgress;
                            CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                    Integer.parseInt(TenureInput.getText().toString().trim()));

                        }
                    }
                }
            }
        };

        /*

        Live edits & outputs along with seeker progress

         */
         /*

        Live edits & outputs along with seeker progress

         */
        RateChanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){

                    if(progress==0){
                        seekBar.setProgress(1);
                        decimalProgress = 0.1;
                        progress_value = decimalProgress;
                        RatePercent.setText(String.valueOf(decimalProgress));
                    } else {
                        decimalProgress = ((float) progress) / 10.0;
                        progress_value = decimalProgress;
                        RatePercent.setText(String.valueOf(decimalProgress));
                    }

                    CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                            Integer.parseInt(TenureInput.getText().toString().trim()));


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                RatePercent.removeTextChangedListener(textWatcher);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                RatePercent.addTextChangedListener(textWatcher);
            }
        });



        DepositInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    CalculateAndSet(Integer.parseInt(s.toString().trim()),
                            Integer.parseInt(TenureInput.getText().toString().trim()));
                } else {
                    MVAmount.setText(emptyLiteral);
                    InterestAmount.setText(emptyLiteral);
                }
            }
        });




        /*
        Tenure Option Change, Changes From Monthly to Yearly & Vice Versa
         */

        TenureOptionsYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenureOptionsYearly.setTextColor(ContextCompat.getColor(getContext(),R.color.primary_sip));
                TenureOptionsYearly.setTextSize(14);
                TenureOptionsMonthly.setTextColor(Color.parseColor("#000000"));
                TenureOptionsMonthly.setTextSize(10);
                mory = true;

                CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                        Integer.parseInt(TenureInput.getText().toString().trim()));
            }
        });

        TenureOptionsMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenureOptionsMonthly.setTextColor(ContextCompat.getColor(getContext(), R.color.primary_sip));
                TenureOptionsMonthly.setTextSize(14);
                TenureOptionsYearly.setTextColor(Color.parseColor("#000000"));
                TenureOptionsYearly.setTextSize(10);
                mory = false;

                CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                        Integer.parseInt(TenureInput.getText().toString().trim()));
            }
        });


        /*
        Live edits & outputs
         */
        TenureInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){

                    CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                            Integer.parseInt(s.toString().trim()));
                } else {
                    MVAmount.setText(emptyLiteral);
                    InterestAmount.setText(emptyLiteral);
                }
            }
        });

        RatePercent.addTextChangedListener(textWatcher);

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MVAmount.getText().toString().equals("-")) {
                    Toast.makeText(getContext(), "Incomplete Fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), Statistics.class);

                    intent.putExtra("Amount", Float.parseFloat(DepositInput.getText().toString()));
                    intent.putExtra("Rate", progress_value);
                    intent.putExtra("Tenure", Integer.parseInt(TenureInput.getText().toString()));
                    intent.putExtra("Compounding", 0);
                    intent.putExtra("TenureType", returnforMory());
                    intent.putExtra("Calculation", 4);
                    intent.putExtra("MV", Float.parseFloat(MVAmount.getText().toString()));
                    intent.putExtra("Interest", Float.parseFloat(InterestAmount.getText().toString()));
                    intent.putExtra("Category", 4);
                    startActivity(intent);
                }

            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MVAmount.getText().toString().equals("-")){
                    Toast.makeText(getContext(), "Incomplete Fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), SIPReport.class);
                    intent.putExtra("DEPOSIT", DepositInput.getText().toString());
                    intent.putExtra("INTERESTRATE", String.valueOf(progress_value));
                    intent.putExtra("TENURE", TenureInput.getText().toString());
                    intent.putExtra("MV", MVAmount.getText().toString());
                    intent.putExtra("TenureType", returnforMory());
                    intent.putExtra("INTEREST", InterestAmount.getText().toString());
                    intent.putExtra("Category", 2);
                    startActivity(intent);
                }
            }
        });


        return rootView;
    }

    public int returnforMory(){
        if(mory){
            return 1;
        } else {
            return 0;
        }
    }


    public void setFont(){

        Typeface poppins_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppinsb.ttf");
        MVtitle.setTypeface(poppins_bold);
        MVAmount.setTypeface(poppins_bold);
        InterestTitle.setTypeface(poppins_bold);
        InterestAmount.setTypeface(poppins_bold);
        DepositTitle.setTypeface(poppins_bold);
        RateTitle.setTypeface(poppins_bold);
        TenureOptionsYearly.setTypeface(poppins_bold);
        TenureOptionsMonthly.setTypeface(poppins_bold);
        RatePercent.setTypeface(poppins_bold);
        TenureTitle.setTypeface(poppins_bold);
        TenureInput.setTypeface(poppins_bold);
        statsButton.setTypeface(poppins_bold);
        DepositInput.setTypeface(poppins_bold);
        shareButton.setTypeface(poppins_bold);

    }


    public void CalculateAndSet(int amount, int tenure){
        if(!DepositInput.getText().toString().equals("")) {
            if (mory) {
                tenure = tenure * 12;
            }

            double rate2 = (progress_value) / 1200;
            //getting all the inputs perfectly

            double inte;

            double MV = amount;
            double inte_sum = 0;
            for (int i = 0; i < tenure; i++) {
                inte = MV * rate2;
                MV = MV + inte;
                inte_sum = inte_sum + inte;
                MVAmount.setText(String.valueOf(Math.round(MV)));
                InterestAmount.setText(String.valueOf(Math.round(inte_sum)));
                MV = MV + amount;

            }

        }

    }
}