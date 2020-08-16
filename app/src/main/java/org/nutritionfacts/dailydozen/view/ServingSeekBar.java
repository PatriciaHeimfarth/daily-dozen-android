package org.nutritionfacts.dailydozen.view;

import android.content.Context;

public class ServingSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
    private ServingSeekBar scbNextServing;
    private ServingSeekBar scvPrevServing;

    public ServingSeekBar(Context context) {
        super(context);

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
