package org.nutritionfacts.dailydozen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import org.nutritionfacts.dailydozen.R;
import org.nutritionfacts.dailydozen.RDA;
import org.nutritionfacts.dailydozen.Servings;
import org.nutritionfacts.dailydozen.model.DDServings;
import org.nutritionfacts.dailydozen.model.Day;
import org.nutritionfacts.dailydozen.model.Food;
import org.nutritionfacts.dailydozen.model.Tweak;
import org.nutritionfacts.dailydozen.model.TweakServings;
import org.nutritionfacts.dailydozen.task.CalculateStreakTask;
import org.nutritionfacts.dailydozen.task.CalculateTweakStreakTask;
import org.nutritionfacts.dailydozen.task.StreakTaskInput;
import org.nutritionfacts.dailydozen.view.ServingSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RDACheckBoxes extends LinearLayout {
    @BindView(R.id.food_check_boxes_container)
    protected ViewGroup vgContainer;

    private ServingSeekBar servingSeekBar;

    private RDA rda;
    private Day day;

    public RDACheckBoxes(Context context) {
        this(context, null);
    }

    public RDACheckBoxes(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RDACheckBoxes(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.food_check_boxes, this);
        ButterKnife.bind(this);
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setRDA(RDA rda) {
        this.rda = rda;
    }

    public void setServings(final Servings servings) {
        final int numServings = servings != null ? servings.getServings() : 0;

        ServingSeekBar seekBar = createSeekBar(numServings, rda.getRecommendedAmount());

        vgContainer.removeAllViews();
        vgContainer.addView(seekBar);
        vgContainer.addView(seekBar.getShowProgressTextView());

    }

    private ServingSeekBar createSeekBar(Integer currentServings, Integer maxServings) {
        servingSeekBar = new ServingSeekBar(getContext());

        servingSeekBar.setOnSeekBarChangeListener(getOnCheckedChangeListener(servingSeekBar));
        servingSeekBar.setMax(maxServings * 2);
        servingSeekBar.setProgress(currentServings * 2);
        servingSeekBar.setLayoutParams(new LinearLayout.LayoutParams(555, 50, 1f));
        servingSeekBar.getShowProgressTextView().setText(givePortionStringWithHalfValues(currentServings * 2));
        return servingSeekBar;
    }

    private SeekBar.OnSeekBarChangeListener getOnCheckedChangeListener(final ServingSeekBar seekBar) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (rda instanceof Food) {
                    handlePortionChangeOnSeekBar();
                } else if (rda instanceof Tweak) {
                    handleTweakChangeOnSeekBar();
                }
            }
        };
    }


    private void handlePortionChangeOnSeekBar() {
        day = Day.createDayIfDoesNotExist(day);

        final DDServings servings = DDServings.createServingsIfDoesNotExist(day, (Food)rda);
        final Integer numberOfConsumedHalfPortions = servingSeekBar.getProgress();

        if (servings != null && servings.getServings() != numberOfConsumedHalfPortions) {
            servings.setServings(numberOfConsumedHalfPortions / 2);
            servings.save();
            onServingsChanged();
            Timber.d("Changed Servings for %s", servings);

            servingSeekBar.getShowProgressTextView().setText(
                    givePortionStringWithHalfValues(numberOfConsumedHalfPortions));

        }
    }

    private String givePortionStringWithHalfValues(int numberOfConsumedHalfPortions){

        if (numberOfConsumedHalfPortions % 2 == 0)
            return String.valueOf((numberOfConsumedHalfPortions / 2));
        return String.valueOf((numberOfConsumedHalfPortions / 2)) + ".5";
    }

    private void handleTweakChangeOnSeekBar() {
        day = Day.createDayIfDoesNotExist(day);

        final TweakServings servings = TweakServings.createServingsIfDoesNotExist(day, (Tweak)rda);
        final Integer numberOfConsumedHalfPortions = servingSeekBar.getProgress();

        if (servings != null && servings.getServings() != numberOfConsumedHalfPortions) {
            servings.setServings(numberOfConsumedHalfPortions);

            servings.save();
            onTweakServingsChanged();
            Timber.d("Changed TweakServings for %s", servings);

            servingSeekBar.getShowProgressTextView().setText(
                    givePortionStringWithHalfValues(numberOfConsumedHalfPortions));
        }
    }

    private void onServingsChanged() {
        new CalculateStreakTask(getContext()).execute(new StreakTaskInput(day, rda));
    }

    private void onTweakServingsChanged() {
        new CalculateTweakStreakTask(getContext()).execute(new StreakTaskInput(day, rda));
    }
}
