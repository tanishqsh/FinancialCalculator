package com.madhouseapps.financialcalculator;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.Helper.InputFilterMinMax;


public class EMICalculation extends Fragment {

    /*
    true for year
    false for month
     */
    boolean mory = true;

    String rupee = "₹";
    String rupeeCode;
    double progress_value=5;
    double decimalProgress;


    TextView emiAmount, tenureOptionsYearly, tenureOptionsMonthly, tenureTitle;
    TextView emiTitle, interestTitle, interestAmount, totalTitle, totalAmount, loanTitle, rateTitle;
    EditText tenureInput, loanInput;
    EditText ratePercent;
    Button statsButton;
    SeekBar rateChanger;

    TextWatcher textWatcher;

    public EMICalculation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emicalculation, container, false);

        emiAmount = (TextView) view.findViewById(R.id.EMIAmount);
        emiTitle = (TextView) view.findViewById(R.id.EMITitle);
        interestTitle = (TextView) view.findViewById(R.id.InterestTitle);
        interestAmount = (TextView) view.findViewById(R.id.InterestAmount);
        totalTitle = (TextView) view.findViewById(R.id.TotalTitle);
        totalAmount = (TextView) view.findViewById(R.id.TotalAmount);
        loanTitle = (TextView) view.findViewById(R.id.LoanTitle);
        loanInput = (EditText) view.findViewById(R.id.LoanInput);
        rateTitle = (TextView) view.findViewById(R.id.RateTitle);
        ratePercent = (EditText) view.findViewById(R.id.RatePercent);
        tenureTitle = (TextView) view.findViewById(R.id.TenureTitle);
        tenureInput = (EditText) view.findViewById(R.id.TenureInput);
        tenureOptionsYearly = (TextView) view.findViewById(R.id.TenureOptionsYearly);
        tenureOptionsMonthly = (TextView) view.findViewById(R.id.TenureOptionsMonthly);
        statsButton = (Button) view.findViewById(R.id.statsButton);
        rateChanger = (SeekBar) view.findViewById(R.id.RateChanger);
        rateChanger.setProgress(50);

        rupeeCode = getResources().getString(R.string.rupee);


        Typeface poppins_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/poppinsb.ttf");


        emiAmount.setTypeface(poppins_bold);
        emiTitle.setTypeface(poppins_bold);
        interestAmount.setTypeface(poppins_bold);
        interestTitle.setTypeface(poppins_bold);
        totalAmount.setTypeface(poppins_bold);
        totalTitle.setTypeface(poppins_bold);
        loanTitle.setTypeface(poppins_bold);
        ratePercent.setTypeface(poppins_bold);
        rateTitle.setTypeface(poppins_bold);
        loanInput.setTypeface(poppins_bold);
        tenureTitle.setTypeface(poppins_bold);
        tenureInput.setTypeface(poppins_bold);
        tenureOptionsYearly.setTypeface(poppins_bold);
        tenureOptionsMonthly.setTypeface(poppins_bold);
        statsButton.setTypeface(poppins_bold);

        CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                mory,
                Integer.parseInt(tenureInput.getText().toString().trim()));


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()>0){

                    if(s.charAt(s.toString().length()-1)=='.' || s.charAt(0)=='.'){
                        //Dont do anything, wait for him to enter something after the '.'
                    } else {
                        if(!s.toString().equals("")){

                            double value = Double.parseDouble(s.toString());
                            value = value * 10;
                            rateChanger.setProgress((int)value);
                            decimalProgress = (float)(Double.parseDouble(s.toString()));
                            progress_value = decimalProgress;
                            CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                                    mory,
                                    Integer.parseInt(tenureInput.getText().toString().trim()));
                        }
                    }
                }


            }
        };

        /*

        Live edits & outputs along with seeker progress

         */
        rateChanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){

                    if(progress==0){
                        seekBar.setProgress(1);
                        decimalProgress = 0.1;
                        progress_value = decimalProgress;
                        Toast.makeText(getContext(), ""+decimalProgress, Toast.LENGTH_SHORT).show();
                        ratePercent.setText(""+decimalProgress);
                    } else {
                        decimalProgress = ((float) progress) / 10.0;
                        progress_value = decimalProgress;
                        ratePercent.setText(""+decimalProgress);
                    }

                    CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                            mory,
                            Integer.parseInt(tenureInput.getText().toString().trim()));


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ratePercent.removeTextChangedListener(textWatcher);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ratePercent.addTextChangedListener(textWatcher);
            }
        });


        /*
        Tenure Option Change, Changes From Monthly to Yearly & Vice Versa
         */

        tenureOptionsYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenureOptionsYearly.setTextColor(getResources().getColor(R.color.primary_emi));
                tenureOptionsYearly.setTextSize(14);
                tenureOptionsMonthly.setTextColor(Color.parseColor("#000000"));
                tenureOptionsMonthly.setTextSize(10);
                mory = true;

                CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                        mory,
                        Integer.parseInt(tenureInput.getText().toString().trim()));
            }
        });

        tenureOptionsMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenureOptionsMonthly.setTextColor(getResources().getColor(R.color.primary_emi));
                tenureOptionsMonthly.setTextSize(14);
                tenureOptionsYearly.setTextColor(Color.parseColor("#000000"));
                tenureOptionsYearly.setTextSize(10);
                mory = false;

                CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                        mory,
                        Integer.parseInt(tenureInput.getText().toString().trim()));
            }
        });

        /*
        Live Edits & Outputs
         */
        loanInput.addTextChangedListener(new TextWatcher() {
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
                            mory,
                            Integer.parseInt(tenureInput.getText().toString().trim()));
                }

            }
        });

        /*
        Live edits & outputs
         */
        tenureInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                            mory,
                            Integer.parseInt(s.toString().trim()));
                }
            }
        });

        //ratePercent.setFilters(new InputFilter[]{new InputFilterMinMax("0", "250")});
        ratePercent.addTextChangedListener(textWatcher);

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Statistics.class);

                intent.putExtra("Amount", Float.parseFloat(loanInput.getText().toString()));
                intent.putExtra("Rate", progress_value);
                intent.putExtra("Tenure", Integer.parseInt(tenureInput.getText().toString()));
                intent.putExtra("Compounding", 0);
                intent.putExtra("TenureType", returnforMory());
                intent.putExtra("Calculation", 1);
                intent.putExtra("EMI", Float.parseFloat(emiAmount.getText().toString()));
                intent.putExtra("Interest", Float.parseFloat(interestAmount.getText().toString()));

                startActivity(intent);
            }
        });



        return view;
    }

    public int returnforMory(){
        if(mory){
            return 1;
        } else {
            return 0;
        }
    }

    @SuppressLint("SetTextI18n")
    public void CalculateAndSet(double amount, boolean mory, int tenure){

        double pV = (progress_value/12)/100;
        if (mory) {
            tenure = tenure*12;
        }


        double emi= (amount*pV*Math.pow(1+pV,tenure))/((Math.pow((1+pV),tenure))-1);
        emiAmount.setText(""+Math.round(emi));
        totalAmount.setText(""+(Math.round(emi*tenure)));
        interestAmount.setText(""+((Math.round((emi*tenure)))-(Integer.parseInt(loanInput.getText().toString()))));


    }

}
