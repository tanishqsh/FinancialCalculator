package com.madhouseapps.financialcalculator;


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


    TextView emiAmount, tenureOptionsYearly, tenureOptionsMonthly, tenureTitle, ratePercent;
    TextView emiTitle, interestTitle, interestAmount, totalTitle, totalAmount, loanTitle, rateTitle;
    EditText tenureInput, loanInput;
    Button statsButton;
    SeekBar rateChanger;

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
        ratePercent = (TextView) view.findViewById(R.id.RatePercent);
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

        /*

        Live edits & outputs along with seeker progress

         */
        rateChanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if(progress==0){
                    seekBar.setProgress(5);
                    decimalProgress = 0.5f;
                    progress_value = decimalProgress;
                    ratePercent.setText(""+decimalProgress+"%");
                } else {
                    //ratePercent.setText(""+progress+"%");
                    decimalProgress = (float)(progress-(progress%5))/10;
                    progress_value = decimalProgress;
                    ratePercent.setText(""+decimalProgress+"%");
                }

                CalculateAndSet(Integer.parseInt(loanInput.getText().toString().trim()),
                        mory,
                        Integer.parseInt(tenureInput.getText().toString().trim()));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        /*
        Tenure Option Change, Changes From Monthly to Yearly & Vice Versa
         */

        tenureOptionsYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenureOptionsYearly.setTextColor(getResources().getColor(R.color.primary_emi));
                tenureOptionsMonthly.setTextColor(Color.parseColor("#000000"));
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
                tenureOptionsYearly.setTextColor(Color.parseColor("#000000"));
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


        return view;
    }

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
