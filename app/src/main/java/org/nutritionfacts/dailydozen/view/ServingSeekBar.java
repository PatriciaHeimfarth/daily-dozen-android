package org.nutritionfacts.dailydozen.view;

import android.content.Context;
import android.widget.TextView;

public class ServingSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    private TextView showProgressTextView;

    public TextView getShowProgressTextView() {
        return showProgressTextView;
    }

    public void setShowProgressTextView(TextView showProgress) {
        this.showProgressTextView = showProgress;
    }

    public ServingSeekBar(Context context) {
        super(context);
        this.showProgressTextView = new TextView(getContext());
        this.showProgressTextView.setText("0");

    }

}
