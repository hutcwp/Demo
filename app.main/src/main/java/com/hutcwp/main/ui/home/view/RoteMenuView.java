package com.hutcwp.main.ui.home.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hutcwp.main.R;
import com.hutcwp.main.util.CommonUtil;

public class RoteMenuView extends RelativeLayout {

    private static final String TAG = "RoteMenuView";

    private static final int DURATION = 300;

    private TextView img1;
    private TextView img2;
    private TextView img3;
    private TextView img4;
    private TextView img5;
    private TextView img6;
    private TextView img7;
    private TextView img8;
    private TextView img9;

    private static final int TURN_LEFT = 1;
    private static final int TURN_RIGHT = 2;

    private Position[] imgItems;

    private ObjectAnimator translationX = new ObjectAnimator();
    private ObjectAnimator translationY = new ObjectAnimator();

    public abstract static class ClickListener {
        public void click1() {
        }

        public void click2() {
        }

        public void click3() {
        }

        public void click4() {
        }

        public void click5() {
        }

        public void click6() {
        }

        public void click7() {
        }

        public void click8() {
        }

        public void click9() {
        }
    }

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clickListener == null) {
                return;
            }

            switch (v.getId()) {
                case R.id.iv_one:
                    clickListener.click1();
                    break;
                case R.id.iv_two:
                    clickListener.click2();
                    break;
                case R.id.iv_three:
                    clickListener.click3();
                    break;
                case R.id.iv_four:
                    clickListener.click4();
                    break;
                case R.id.iv_five:
                    clickListener.click5();
                    break;
                case R.id.iv_six:
                    clickListener.click6();
                    break;
                case R.id.iv_seven:
                    clickListener.click7();
                    break;
                case R.id.iv_eight:
                    clickListener.click8();
                    break;
                case R.id.iv_nien:
                    clickListener.click9();
                    break;
                default:
                    break;
            }
        }
    };

    public RoteMenuView(Context context) {
        super(context);
        initView();
        initListener();
    }

    public RoteMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initListener();
    }

    public RoteMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initListener();
    }


    private void initImgItem() {
        imgItems = new Position[]{
                new Position(img5.getX(), img5.getY()),
                new Position(img1.getX(), img1.getY()),
                new Position(img2.getX(), img2.getY()),
                new Position(img3.getX(), img3.getY()),
                new Position(img4.getX(), img4.getY()),
                new Position(img5.getX(), img5.getY()),
                new Position(img6.getX(), img6.getY()),
                new Position(img7.getX(), img7.getY()),
                new Position(img8.getX(), img8.getY()),
                new Position(img9.getX(), img9.getY()),
        };
    }

    private void initListener() {

        img1.setOnClickListener(listener);
        img2.setOnClickListener(listener);
        img3.setOnClickListener(listener);
        img4.setOnClickListener(listener);

        img6.setOnClickListener(listener);
        img7.setOnClickListener(listener);
        img8.setOnClickListener(listener);
        img9.setOnClickListener(listener);

        img5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                safeStartAnim(1);
            }
        });
    }

    private void safeStartAnim(int r) {
        if (translationY == null || translationX == null) {
            return;
        }

        boolean animRunning = translationX.isRunning() || translationY.isRunning();
        if (!animRunning) {
            if (r == TURN_LEFT) {
                startLeftAnimation();
            } else if ((r == TURN_RIGHT)) {
                startRightAnimation();
            }
        }
    }

    /**
     * 顺时针旋转
     */
    private void startRightAnimation() {
        Log.d(TAG, "startAnim Right");
        translateTo(img1, img2, imgItems[1]);
        translateTo(img2, img3, imgItems[2]);
        translateTo(img3, img6, imgItems[3]);
        translateTo(img6, img9, imgItems[6]);
        translateTo(img9, img8, imgItems[9]);
        translateTo(img8, img7, imgItems[8]);
        translateTo(img7, img4, imgItems[7]);
        translateTo(img4, img1, imgItems[4]);
    }

    /**
     * 顺时针旋转
     */
    private void startLeftAnimation() {
        Log.d(TAG, "startAnim Left");
        translateTo(img1, img4, imgItems[1]);
        translateTo(img4, img7, imgItems[4]);
        translateTo(img7, img8, imgItems[7]);
        translateTo(img8, img9, imgItems[8]);
        translateTo(img9, img6, imgItems[9]);
        translateTo(img6, img3, imgItems[6]);
        translateTo(img3, img2, imgItems[3]);
        translateTo(img2, img1, imgItems[2]);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_rotemenu, this);
        img1 = findViewById(R.id.iv_one);
        img2 = findViewById(R.id.iv_two);
        img3 = findViewById(R.id.iv_three);
        img4 = findViewById(R.id.iv_four);
        img5 = findViewById(R.id.iv_five);
        img6 = findViewById(R.id.iv_six);
        img7 = findViewById(R.id.iv_seven);
        img8 = findViewById(R.id.iv_eight);
        img9 = findViewById(R.id.iv_nien);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout");
                initImgItem();
            }
        });
    }

    private void translateTo(View startView, View endView, Position item) {
        translateXTo(startView, endView, item);
        translateYTo(startView, endView, item);
    }

    private void translateXTo(View startView, View endView, Position item) {
        float offsetX = endView.getX() - startView.getX();
        // Log.i(TAG, "startX = " + startView.getX() + "  endX = " + endView.getX() + "  offsetX = " + offsetX + "  item.x = " + item.x);
        translationX = ObjectAnimator.ofFloat(startView, "translationX", startView.getX() - item.x + offsetX);
        translationX.setDuration(DURATION);
        translationX.start();
    }

    private void translateYTo(View startView, View endView, Position item) {
        float offsetY = endView.getY() - startView.getY();
        translationY = ObjectAnimator.ofFloat(startView, "translationY", startView.getY() - item.y + offsetY);
        translationY.setDuration(DURATION);
        translationY.start();
    }


    float statusBarHeight = CommonUtil.getStatusBarHeight(getContext());
    float mXInView = 0;
    float mYInView = 0;
    float mXDownInScreen = 0;
    float mYDownInScreen = 0;
    float mXInScreen = 0;
    float mYInScreen = 0;

    int mDuration = 50;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                mXInView = event.getX();
                mYInView = event.getY();
                //手指距离屏幕左边的距离getRawX()
                mXDownInScreen = event.getRawX();
                mYDownInScreen = event.getRawY() - statusBarHeight;
                mXInScreen = event.getRawX();
                mYInScreen = event.getRawY() - statusBarHeight;
                break;
            case MotionEvent.ACTION_MOVE:
                // Log.d(TAG, "ACTION_MOVE");
                mXInScreen = event.getRawX();
                mYInScreen = event.getRawY() - statusBarHeight;
                //手指移动的时候更新小悬浮窗的位置。
                // dx：x方向上移动的距离，dy:y方向上移动的距离
                int dx = (int) (mXInScreen - mXInView);
                int dy = (int) (mYInScreen - mYInView);

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (mXDownInScreen == mXInScreen && mYDownInScreen == mYInScreen) {
                    return false;
                } else {
                    float offsetX = mXInScreen - mXDownInScreen;
                    float offsetY = mYInScreen - mYDownInScreen;

                    if (Math.abs(offsetX) < Math.abs(offsetY)) {
                        Log.d(TAG, String.format("mYInScreen(%s) - mYDownInScreen(%s) = offsetY(%s) ", mYInScreen, mYDownInScreen, offsetY));
                        if (mXInView < getWidth() / 3) {
                            if (offsetY> 0) {
                                safeStartAnim(TURN_LEFT);
                            } else {
                                safeStartAnim(TURN_RIGHT);
                            }
                        } else if (mXInView > getWidth() * (2 / 3f)) {
                            if (offsetX > 0) {
                                safeStartAnim(TURN_RIGHT);
                            } else {
                                safeStartAnim(TURN_LEFT);
                            }
                        }
                        return true;
                    } else {
                        Log.d(TAG, String.format("mXInScreen(%s) - mXDownInScreen(%s) = offsetX(%s) ", mXInScreen, mXDownInScreen, offsetX));
                        if (mYInView < getHeight() / 3) {
                            scrollTopHorionzel(offsetX);
                        } else if (mYInView > getHeight() * (2 / 3f)) {

                            scrollBottomHorionze(offsetX);
                        }
                        return true;
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    private void scrollBottomHorionze(float offsetX) {
        if (Math.abs(offsetX) > mDuration) {
            if (offsetX > 0) {
                Log.d(TAG, "turn right ...");
                safeStartAnim(TURN_LEFT); //right
            } else {
                Log.d(TAG, "turn left ...");
                safeStartAnim(TURN_RIGHT); //left
            }
        }
    }

    private void scrollTopHorionzel(float offsetX) {
        if (Math.abs(offsetX) > mDuration) {
            if (offsetX > 0) {
                Log.d(TAG, "turn right ...");
                safeStartAnim(TURN_RIGHT); //right
            } else {
                Log.d(TAG, "turn left ...");
                safeStartAnim(TURN_LEFT); //left
            }
        }
    }

    /**
     * 用来记录位置信息
     */
    class Position {
        float x;
        float y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

}
