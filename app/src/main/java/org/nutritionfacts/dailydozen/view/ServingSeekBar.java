package org.nutritionfacts.dailydozen.view;

import android.content.Context;
import android.widget.TextView;

public class ServingSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private TextView showProgressTextView;

    public ServingSeekBar(Context context) {
        super(context);
        this.showProgressTextView = new TextView(getContext());
        this.showProgressTextView.setText("0");

    }

    public void changeValueOnSeekBarTextView(String value){
        this.showProgressTextView.setText(value);
    }

    public TextView getShowProgressTextView() {
        return showProgressTextView;
    }



}
