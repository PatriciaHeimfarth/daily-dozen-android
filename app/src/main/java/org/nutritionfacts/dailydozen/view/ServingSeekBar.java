package org.nutritionfacts.dailydozen.view;

import android.content.Context;
import android.widget.TextView;

public class ServingSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private ServingSeekBar scbNextServing;
    private ServingSeekBar scvPrevServing;

   
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

    public void setNextServing(ServingSeekBar nextServing) {

        this.scvPrevServing = nextServing;
        nextServing.scbNextServing = this;
    }

    public void onCheckChange(boolean isChecked) {
        if (isChecked) {
            continueCheck();
        } else {
            continueUncheck();
        }
    }

    private void continueCheck() {
      //  if (scbNextServing != null && !scbNextServing.isChecked()) {
      //      scbNextServing.setChecked(true);
     //   }
    }

    private void continueUncheck() {
      // if (scvPrevServing != null && scvPrevServing.isChecked()) {
      //      scvPrevServing.setChecked(false);
     //   }
    }
}
