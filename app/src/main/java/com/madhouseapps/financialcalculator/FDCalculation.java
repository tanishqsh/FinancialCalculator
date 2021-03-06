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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.Helper.PreferenceManager;
import com.madhouseapps.financialcalculator.ReportGeneration.EMIReport;
import com.madhouseapps.financialcalculator.ReportGeneration.FDReport;

import java.util.ArrayList;
import java.util.List;


public class FDCalculation extends Fragment {

    /*
   true for year
   false for month
    */
    boolean mory = true;

    /*
    1  - Monthly
    2  - Quarterly
    3  - Half Yearly
    4  - Yearly
     */
    double progress_value = 7.5;
    String emptyLiteral = "-";

    String rupee = "₹";


    TextView MVtitle, MVAmount, InterestTitle, InterestAmount, TotalTitle, TotalAmount;
    TextView DepositTitle;
    EditText DepositInput;
    TextView RateTitle, RatePercent;
    SeekBar RateChanger;
    TextView TenureTitle, TenureOptionsYearly, TenureOptionsMonthly;
    EditText TenureInput;
    TextView CompoundingTitle;
    Spinner CompoundingOptions;
    Button statsButton, shareButton;

    double decimalProgress;

    List<String> compoundingOptions = new ArrayList<>();

    TextWatcher textWatcher;
    PreferenceManager preferenceManager;

    public FDCalculation() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fdcalculation, container, false);

        preferenceManager = new PreferenceManager(getContext());


        MVtitle = (TextView) rootView.findViewById(R.id.MVtitle);
        MVAmount = (TextView) rootView.findViewById(R.id.MVAmount);
        InterestTitle = (TextView) rootView.findViewById(R.id.InterestTitle);
        InterestAmount = (TextView) rootView.findViewById(R.id.InterestAmount);
        TotalTitle = (TextView) rootView.findViewById(R.id.TotalTitle);
        TotalAmount = (TextView) rootView.findViewById(R.id.TotalAmount);
        DepositTitle = (TextView) rootView.findViewById(R.id.DepositTitle);
        RateTitle = (TextView) rootView.findViewById(R.id.RateTitle);
        RatePercent = (TextView) rootView.findViewById(R.id.RatePercent);
        TenureTitle = (TextView) rootView.findViewById(R.id.TenureTitle);
        TenureOptionsMonthly = (TextView) rootView.findViewById(R.id.TenureOptionsMonthly);
        TenureOptionsYearly = (TextView) rootView.findViewById(R.id.TenureOptionsYearly);
        shareButton = (Button) rootView.findViewById(R.id.ShareButton);


        DepositInput = (EditText) rootView.findViewById(R.id.DepositInput);
        TenureInput = (EditText) rootView.findViewById(R.id.TenureInput);
        RateChanger = (SeekBar) rootView.findViewById(R.id.RateChanger);
        statsButton = (Button) rootView.findViewById(R.id.statsButton);

        RateChanger.setProgress(75);

        compoundingOptions.clear();
        compoundingOptions.add("MONTHLY"); //1/12
        compoundingOptions.add("QUARTERLY"); //1/4
        compoundingOptions.add("HALF YEARLY"); //1/2
        compoundingOptions.add("YEARLY"); //1

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

                    if(!DepositInput.getText().toString().equals("") && !TenureInput.getText().toString().equals("")) {
                        CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                                Integer.parseInt(TenureInput.getText().toString().trim()));
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


        /*
        Tenure Option Change, Changes From Monthly to Yearly & Vice Versa
         */

        TenureOptionsYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenureOptionsYearly.setTextColor(getResources().getColor(R.color.primary_fd));
                TenureOptionsYearly.setTextSize(14);
                TenureOptionsMonthly.setTextColor(Color.parseColor("#000000"));
                TenureOptionsMonthly.setTextSize(10);
                mory = true;

                if(!DepositInput.getText().toString().equals("") && !TenureInput.getText().toString().equals("")) {
                    CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                            Integer.parseInt(TenureInput.getText().toString().trim()));
                }

            }
        });

        TenureOptionsMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TenureOptionsMonthly.setTextColor(getResources().getColor(R.color.primary_fd));
                TenureOptionsMonthly.setTextSize(14);
                TenureOptionsYearly.setTextColor(Color.parseColor("#000000"));
                TenureOptionsYearly.setTextSize(10);
                mory = false;

                if(!DepositInput.getText().toString().equals("") && !TenureInput.getText().toString().equals("")) {
                    CalculateAndSet(Integer.parseInt(DepositInput.getText().toString().trim()),
                            Integer.parseInt(TenureInput.getText().toString().trim()));
                }
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
                if (!s.toString().equals("")) {
                    CalculateAndSet(Integer.parseInt(s.toString().trim()),
                            Integer.parseInt(TenureInput.getText().toString().trim()));

                } else {
                    MVAmount.setText(emptyLiteral);
                    InterestAmount.setText(emptyLiteral);
                }
            }
        });

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
                    intent.putExtra("Calculation", 2);
                    intent.putExtra("MV", Float.parseFloat(MVAmount.getText().toString()));
                    intent.putExtra("Interest", Float.parseFloat(InterestAmount.getText().toString()));

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

                    Intent intent = new Intent(getContext(), FDReport.class);
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
        TotalTitle.setTypeface(poppins_bold);
        TotalAmount.setTypeface(poppins_bold);
        DepositTitle.setTypeface(poppins_bold);
        RateTitle.setTypeface(poppins_bold);
        RatePercent.setTypeface(poppins_bold);
        TenureTitle.setTypeface(poppins_bold);
        TenureOptionsMonthly.setTypeface(poppins_bold);
        TenureOptionsYearly.setTypeface(poppins_bold);
        TenureInput.setTypeface(poppins_bold);
        statsButton.setTypeface(poppins_bold);
        DepositInput.setTypeface(poppins_bold);
        shareButton.setTypeface(poppins_bold);

    }


    public void CalculateAndSet(int amount, int tenure){

        if(!DepositInput.getText().toString().equals("") && !TenureInput.getText().toString().equals("") && !RatePercent.getText().toString().equals("")) {
            double rate2 = (progress_value) / 100;
            int n = 4;
            //converting months to years
            double ten = 0;
            if (!mory) {
                ten = ((double) tenure) / 12;
            } else {
                ten = tenure;
            }

            double MV = Math.round(amount * (Math.pow(1 + (rate2 / n), n * ten)));
            MVAmount.setText("" + Math.round(MV));
            double interest = MV - amount;
            InterestAmount.setText("" + Math.round(interest));

        }

    }
}