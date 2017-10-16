package com.madhouseapps.financialcalculator.TanishqTable;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by tanishqsharma on 16/10/17.
 */

public class TableRow extends LinearLayout {

    public TableRow(Context context, String text1, String text2, String text3) {
        super(context);
        this.setWeightSum(10f);
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(new TableTextView(getContext(), 4, text1));
        this.addView(new TableTextView(getContext(), 3, text2));
        this.addView(new TableTextView(getContext(), 3, text3));
    }
}
