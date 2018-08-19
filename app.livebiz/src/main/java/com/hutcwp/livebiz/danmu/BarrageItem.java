package com.hutcwp.livebiz.danmu;

import android.content.Context;

public class BarrageItem extends android.support.v7.widget.AppCompatTextView {

    public BarrageItem(Context context, Barrage barrage) {
        super(context);
        initParams(barrage);
    }

    private void initParams(Barrage barrage) {
        this.setText(barrage.getContent());
        this.setTextSize(barrage.getTextSize());
        this.setTextColor(barrage.getColor());
    }

}
