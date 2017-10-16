package com.madhouseapps.financialcalculator.TanishqTable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tanishqsharma on 16/10/17.
 */

public class TableTextView extends android.support.v7.widget.AppCompatTextView {
    public TableTextView(Context context, int weight, String text) {
        super(context);
        Typeface poppins_med = Typeface.createFromAsset(context.getAssets(), "fonts/poppinsm.ttf");
        this.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, weight));
        this.setPadding(10, 10, 10, 10);
        this.setGravity(Gravity.CENTER);
        this.setTextColor(Color.parseColor("#FFFFFF"));
        this.setText(text);
        this.setTypeface(poppins_med);
    }
}
