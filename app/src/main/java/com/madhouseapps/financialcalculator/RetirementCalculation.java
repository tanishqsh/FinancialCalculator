package com.madhouseapps.financialcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.Helper.PreferenceManager;
import com.madhouseapps.financialcalculator.ReportGeneration.RDReport;
import com.madhouseapps.financialcalculator.ReportGeneration.RPReport;


public class RetirementCalculation extends Fragment {


    TextView MVtitle, MVAmount, InterestTitle, InterestAmount, ReturnTitle, ReturnAmount;
    TextView CurrentAgeTitle;
    TextView RetirementAgeTitle;
    EditText CurrentAge, RetirementAge;
    SeekBar RateChanger;
    TextView DepositTitle; //savings per month
    TextView RatePercent;
    EditText DepositInput; //savings per month
    Button statsButton, shareButton;

    String emptyLiteral = "-";
    double progress_value = 8;
    double decimalProgress;


    PreferenceManager preferenceManager;

    TextWatcher textWatcher;

    public RetirementCalculation() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_retirementcalculation, container, false);


        preferenceManager = new PreferenceManager(getContext());
        MVtitle = (TextView) rootView.findViewById(R.id.MVTitle);
        MVAmount = (TextView) rootView.findViewById(R.id.MVAmount);
        InterestTitle = (TextView) rootView.findViewById(R.id.InterestTitle);
        InterestAmount = (TextView) rootView.findViewById(R.id.InterestAmount);
        shareButton = (Button) rootView.findViewById(R.id.ShareButton);
        DepositTitle = (TextView) rootView.findViewById(R.id.DepositTitle);
        DepositInput = (EditText) rootView.findViewById(R.id.DepositInput);
        statsButton = (Button) rootView.findViewById(R.id.statsButton);
        RateChanger = (SeekBar) rootView.findViewById(R.id.RateChanger);
        CurrentAgeTitle = (TextView) rootView.findViewById(R.id.CurrentAgeTitle);
        RatePercent = (TextView) rootView.findViewById(R.id.RatePercent);
        RetirementAgeTitle = (TextView) rootView.findViewById(R.id.RetirementAgeTitle);
        CurrentAge = (EditText) rootView.findViewById(R.id.CurrentAgeInput);
        RetirementAge = (EditText) rootView.findViewById(R.id.RetirementAgeInput);
        ReturnAmount = (TextView) rootView.findViewById(R.id.ReturnAmount);
        ReturnTitle = (TextView) rootView.findViewById(R.id.ReturnTitle);
        RateChanger.setProgress(80);


        setFont();
        DepositInput.setSelection(DepositInput.getText().length());

        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                Integer.parseInt(CurrentAge.getText().toString().trim()),
                Integer.parseInt(RetirementAge.getText().toString().trim()));


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
                    } else {
                        if (!s.toString().equals("")) {

                            double value = Double.parseDouble(s.toString());
                            value = value * 10;
                            RateChanger.setProgress((int) value);
                            decimalProgress = (float) (Double.parseDouble(s.toString()));
                            progress_value = decimalProgress;

                            if(!DepositInput.getText().toString().equals("") && !RetirementAge.getText().toString().equals("") && !CurrentAge.getText().toString().equals(""))
                            {
                                CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                        Integer.parseInt(CurrentAge.getText().toString().trim()),
                                        Integer.parseInt(RetirementAge.getText().toString().trim()));
                            }



                        }
                    }
                }
            }
        };

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
                        RatePercent.setText(""+decimalProgress);
                    } else {
                        decimalProgress = ((float) progress) / 10.0;
                        progress_value = decimalProgress;
                        RatePercent.setText(""+decimalProgress);
                    }

                    if(!DepositInput.getText().toString().equals("") && !RetirementAge.getText().toString().equals("") && !CurrentAge.getText().toString().equals("")) {
                        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                Integer.parseInt(CurrentAge.getText().toString().trim()),
                                Integer.parseInt(RetirementAge.getText().toString().trim()));
                    }


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
                if(!s.toString().equals("") && !RetirementAge.getText().toString().equals("") && !CurrentAge.getText().toString().equals("")){
                    CalculateAndSet(Integer.parseInt(s.toString().trim()),
                            Integer.parseInt(CurrentAge.getText().toString().trim()),
                            Integer.parseInt(RetirementAge.getText().toString().trim()));
                } else {
                    MVAmount.setText(emptyLiteral);
                    InterestAmount.setText(emptyLiteral);
                }
            }
        });



        /*
        Live edits & outputs
         */
        CurrentAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("") && !RetirementAge.getText().toString().equals("") && !DepositInput.getText().toString().equals("")){

                    if(Integer.parseInt(s.toString())>=Integer.parseInt(RetirementAge.getText().toString())){

                        Toast.makeText(getContext(), "Current Age Should Be Less Than Retirement Age!", Toast.LENGTH_SHORT).show();
                        CurrentAge.setText("");

                    } else {

                            CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                    Integer.parseInt(s.toString().trim()),
                                    Integer.parseInt(RetirementAge.getText().toString().trim()));

                    }


                } else {
                    MVAmount.setText(emptyLiteral);
                    InterestAmount.setText(emptyLiteral);
                }
            }
        });

        /*
        Live edits & outputs
         */
        RetirementAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("") && !CurrentAge.getText().toString().equals("") && !DepositInput.getText().toString().equals("")){


                        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                Integer.parseInt(CurrentAge.getText().toString().trim()),
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
                    intent.putExtra("Tenure", Integer.parseInt(RetirementAge.getText().toString()) - Integer.parseInt(CurrentAge.getText().toString()));
                    intent.putExtra("Calculation", 5);
                    intent.putExtra("SavingsPerMonth", Float.parseFloat(MVAmount.getText().toString()));
                    intent.putExtra("TotalInvested", Float.parseFloat(InterestAmount.getText().toString()));
                    intent.putExtra("TotalReturn", Float.parseFloat(ReturnAmount.getText().toString().trim()));
                    intent.putExtra("Category", 5);
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


                    Intent intent = new Intent(getContext(), RPReport.class);
                    intent.putExtra("ACCUMULATION", DepositInput.getText().toString());
                    intent.putExtra("INTERESTRATE", String.valueOf(progress_value));
                    intent.putExtra("TENURE", Integer.parseInt(RetirementAge.getText().toString()) - Integer.parseInt(CurrentAge.getText().toString()));
                    intent.putExtra("MV", MVAmount.getText().toString());
                    intent.putExtra("TotalReturn", ReturnAmount.getText().toString().trim());
                    intent.putExtra("TotalInvested", InterestAmount.getText().toString());
                    intent.putExtra("Category", 5);
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }


    public void setFont(){
        Typeface poppins_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppinsb.ttf");
        MVtitle.setTypeface(poppins_bold);
        MVAmount.setTypeface(poppins_bold);
        InterestTitle.setTypeface(poppins_bold);
        InterestAmount.setTypeface(poppins_bold);
        DepositTitle.setTypeface(poppins_bold);
        RetirementAgeTitle.setTypeface(poppins_bold);
        CurrentAgeTitle.setTypeface(poppins_bold);
        CurrentAge.setTypeface(poppins_bold);
        RetirementAge.setTypeface(poppins_bold);
        ReturnTitle.setTypeface(poppins_bold);
        ReturnAmount.setTypeface(poppins_bold);
        RatePercent.setTypeface(poppins_bold);
        statsButton.setTypeface(poppins_bold);
        DepositInput.setTypeface(poppins_bold);
        shareButton.setTypeface(poppins_bold);

    }


    public void CalculateAndSet(int amount, int currentage, int retirementage) {


        if(DepositInput.getText().toString().equals("") ||
                CurrentAge.getText().toString().equals("") ||
                RetirementAge.getText().toString().equals("") ||
                RatePercent.getText().toString().equals("")){

            //Do nothing

        } else {


            double tenure_years = retirementage-currentage; // examples: 20 years
            double tenure_months = tenure_years * 12;  // examples: 240 months
            double quarters = tenure_years * 4; // 80 quarters
            double rate_quarterly = progress_value/400; // 8% = 0.08/4 = 0.02
            double Iplus1 = 1+rate_quarterly;
            double deno = ((Math.pow(Iplus1, quarters))-1) / (1-(Math.pow(Iplus1, -0.3334)));
            double monthly = amount / deno;
            double totalInvested = tenure_months * monthly;

            MVAmount.setText(String.valueOf(Math.round(monthly)));
            InterestAmount.setText(String.valueOf(Math.round(totalInvested)));
            ReturnAmount.setText(String.valueOf(Math.round(amount-totalInvested)));
        }

    }
}