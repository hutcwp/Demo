package com.hutcwp.livebiz.ui.view.danmu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import static com.hutcwp.livebiz.ui.view.danmu.AnimationHelper.getScreenHeight;
import static com.hutcwp.livebiz.ui.view.danmu.AnimationHelper.getScreenWidth;


public class BarrageView extends RelativeLayout {

    private static final int BARRAGE_POS_TOP = 0;
    private static final int BARRAGE_POS_CENTER = 1;
    private static final int BARRAGE_POS_BOOTOM = 2;

    private boolean allow_repeat = false;
    private int leftMargin = 0;
    private int danmuHeight = 400;

    private int BrrageHeight = getScreenHeight(getContext()) / 3;
    private int danmuLineCounts = 10;

    private int curPos = BARRAGE_POS_TOP;

    public BarrageView(Context context) {
        super(context);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showBarrage(Barrage barrage) {
        BarrageItem barrageItem = new BarrageItem(getContext(), barrage);
        layoutBarrageItemPosition(barrageItem);
        initAnim(barrageItem);
        addView(barrageItem);
    }

    private void layoutBarrageItemPosition(BarrageItem barrageItem) {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_RIGHT);
        lp.topMargin = (int) (Math.random() * 10) * (BrrageHeight / danmuLineCounts) + BrrageHeight * curPos;
        Log.i("hutcwp", "curTopMargin = " + lp.topMargin);
        barrageItem.setLayoutParams(lp);
    }

    private void initAnim(final BarrageItem barrageItem) {
        int fromX = getScreenWidth(getContext());
        int toX = -getScreenWidth(getContext());
        Animation anim = AnimationHelper.createTranslateAnim(getContext(), fromX, toX);
        Log.i("hutcwp", "barrageItem width = " + toX);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                if (allow_repeat)
                removeView(barrageItem);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        barrageItem.startAnimation(anim);
    }


    /**
     * 设置弹幕的位置
     * @param pos 位置
     */
    public void setBarragePos(int pos) {
        curPos = pos;
    }

}
