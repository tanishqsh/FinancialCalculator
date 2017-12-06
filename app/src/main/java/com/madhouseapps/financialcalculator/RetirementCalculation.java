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

import com.madhouseapps.financialcalculator.ReportGeneration.RDReport;
import com.madhouseapps.financialcalculator.ReportGeneration.RPReport;


public class RetirementCalculation extends Fragment {


    TextView MVtitle, MVAmount, InterestTitle, InterestAmount;
    TextView CurrentAgeTitle;
    TextView RetirementAgeTitle;
    EditText CurrentAge, RetirementAge;
    TextView DepositTitle; //savings per month
    EditText DepositInput; //savings per month
    Button statsButton, shareButton;

    String emptyLiteral = "-";
    double progress_value = 8;



    public RetirementCalculation() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_retirementcalculation, container, false);


        MVtitle = (TextView) rootView.findViewById(R.id.MVTitle);
        MVAmount = (TextView) rootView.findViewById(R.id.MVAmount);
        InterestTitle = (TextView) rootView.findViewById(R.id.InterestTitle);
        InterestAmount = (TextView) rootView.findViewById(R.id.InterestAmount);
        shareButton = (Button) rootView.findViewById(R.id.ShareButton);
        DepositTitle = (TextView) rootView.findViewById(R.id.DepositTitle);
        DepositInput = (EditText) rootView.findViewById(R.id.DepositInput);
        statsButton = (Button) rootView.findViewById(R.id.statsButton);
        CurrentAgeTitle = (TextView) rootView.findViewById(R.id.CurrentAgeTitle);
        RetirementAgeTitle = (TextView) rootView.findViewById(R.id.RetirementAgeTitle);
        CurrentAge = (EditText) rootView.findViewById(R.id.CurrentAgeInput);
        RetirementAge = (EditText) rootView.findViewById(R.id.RetirementAgeInput);

        setFont();
        DepositInput.setSelection(DepositInput.getText().length());

        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                Integer.parseInt(CurrentAge.getText().toString().trim()),
                Integer.parseInt(RetirementAge.getText().toString().trim()));



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


        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MVAmount.getText().toString().equals("-")) {
                    Toast.makeText(getContext(), "Incomplete Fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), Statistics.class);
                    intent.putExtra("Amount", Float.parseFloat(DepositInput.getText().toString()));
                    intent.putExtra("Rate", 8);
                    intent.putExtra("Tenure", Integer.parseInt(RetirementAge.getText().toString()) - Integer.parseInt(CurrentAge.getText().toString()));
                    intent.putExtra("Calculation", 5);
                    intent.putExtra("MV", Float.parseFloat(MVAmount.getText().toString()));
                    intent.putExtra("Interest", Float.parseFloat(InterestAmount.getText().toString()));
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
                    intent.putExtra("DEPOSIT", DepositInput.getText().toString());
                    intent.putExtra("INTERESTRATE", String.valueOf(progress_value));
                    intent.putExtra("TENURE", Integer.parseInt(RetirementAge.getText().toString()) - Integer.parseInt(CurrentAge.getText().toString()));
                    intent.putExtra("MV", MVAmount.getText().toString());
                    intent.putExtra("INTEREST", InterestAmount.getText().toString());
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
        statsButton.setTypeface(poppins_bold);
        DepositInput.setTypeface(poppins_bold);
        shareButton.setTypeface(poppins_bold);

    }


    public void CalculateAndSet(int amount, int currentage, int retirementage) {


        if(DepositInput.getText().toString().equals("") ||
                CurrentAge.getText().toString().equals("") ||
                RetirementAge.getText().toString().equals("")){

            //Do nothing

        } else {

            int tenure_in_years =  retirementage-currentage;
            int tenure_months = tenure_in_years * 12;

            //perfect till here

            double rate = 8; //8 percent
            double rate_quarterly = rate/400; //quarterly rate (r/n)

            double retirement_amount=0; //this is the final amounts
            double amt; //temporary amount at any given point

            double temp_tenure_years;
            double temp_tenure_months = tenure_months;

            for(int i=0; i<tenure_months; i++){
                temp_tenure_years = temp_tenure_months / 12;
                amt = amount * Math.pow((1 + rate_quarterly), 4*temp_tenure_years);
                retirement_amount = retirement_amount + amt;
                temp_tenure_months = temp_tenure_months-1;
            }

            MVAmount.setText(String.valueOf(Math.round(retirement_amount)));

            double interestA = retirement_amount - (amount * tenure_months);
            InterestAmount.setText(String.valueOf(Math.round(interestA)));

            // work out share fields / statistics
        }

    }
}