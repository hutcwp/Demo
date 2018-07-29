package me.hutcwp.demo.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RateLayout extends FrameLayout {
    private float rate = 16f / 9;
    private Boolean enable = true;

    public RateLayout(@NonNull Context context) {
        this(context, null);
    }

    public RateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRate(float rate, boolean isInvalidate) {
        this.rate = rate;
        if (isInvalidate) {
            invalidate();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (enable) {
            int width = MeasureSpec.getSize(widthMeasureSpec);

            int height = (int) (width / rate);
            int mode = MeasureSpec.getMode(heightMeasureSpec);
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, mode));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }


}
