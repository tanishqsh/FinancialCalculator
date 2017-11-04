package com.madhouseapps.financialcalculator.ReportGeneration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouseapps.financialcalculator.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EMIReport extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener {

    String principal, emi, interest, interestRate, totalPayable, tenure;
    TextView principalView, emiView, interestView, interestRateView, totalPayableView, tenureView;
    LinearLayout canvas;
    int category;
    int mory;

    final int PERMISSION_CODE = 13;
    String absPath = "";
    int height;
    int width;

    String rupee;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emi_report);

        rupee = getResources().getString(R.string.rupee);

        principalView = (TextView) findViewById(R.id.principal);
        emiView = (TextView) findViewById(R.id.EMI);
        interestView = (TextView) findViewById(R.id.InterestPayable);
        interestRateView = (TextView) findViewById(R.id.interestRate);
        totalPayableView = (TextView) findViewById(R.id.TotalPayable);
        tenureView = (TextView) findViewById(R.id.tenure);
        canvas = (LinearLayout) findViewById(R.id.canvas);


        Intent intent = getIntent();
        principal = intent.getStringExtra("PRINCIPAL");
        emi = intent.getStringExtra("EMI");
        interest = intent.getStringExtra("INTEREST");
        interestRate = intent.getStringExtra("INTERESTRATE");
        totalPayable = intent.getStringExtra("TOTALPAYABLE");
        tenure = intent.getStringExtra("TENURE");
        mory = intent.getIntExtra("TenureType", 0);
        category = intent.getIntExtra("Category", 1);

       EMI_Report();
    }

    private void EMI_Report(){

        principalView.setText("PRINCIPAL LOAN AMOUNT: "+rupee+" "+principal);
        emiView.setText("EMI:  "+rupee+" "+emi);
        interestRateView.setText("INTEREST RATE:  "+interestRate+"% p.a");
        interestView.setText("INTEREST: "+rupee+" "+interest);

        if(mory==1){
            tenureView.setText("TENURE:  "+tenure+" years");
        } else {
            tenureView.setText("TENURE:  "+tenure+" months");
        }
        totalPayableView.setText("TOTAL PAYABLE: "+rupee+" "+totalPayable);

        canvas.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public void onGlobalLayout() {

        // Ensure you call it only once :
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            canvas.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        else {
            canvas.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }

        // Here you can get the size :)
        height = canvas.getHeight();
        width = canvas.getWidth();
        askForPermission();
    }

    private void askForPermission(){
        //asking for permissions
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
        else{
            File file = saveBitMap(getApplicationContext(), canvas);    //which view you want to pass that view as parameter
            if (file != null) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                shareIntent.putExtra(Intent.EXTRA_TEXT, "This EMI Report was generated Using FinCal https://play.google.com/store/apps/details?id=com.madhouseapps.financialcalculator");
                shareIntent.setType("image/*");
                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
                Toast.makeText(this, "EMIReport Saved To The Device.", Toast.LENGTH_SHORT).show();
                finish();

            } else {
                Toast.makeText(this, "Could Not Save", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private File saveBitMap(Context context, LinearLayout canvas){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MADHOUSEReports");
        if(!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated) {
                return null;
            }
        }

        String filename = pictureFileDir.getPath()+File.separator+System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(canvas);

        try{
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e){
            e.printStackTrace();
            Log.d("TAG","Issue saving the report");
        }

        absPath = pictureFile.getAbsolutePath();
        scanGallery(context, pictureFile.getAbsolutePath());
        return pictureFile;
    }

    private Bitmap getBitmapFromView(View view){
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
