package me.hutcwp.app.dynamic.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.listener.LittleImageButtonClickListener;
import me.hutcwp.app.dynamic.util.LogUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class LittleImageButton extends android.support.v7.widget.AppCompatImageButton implements View.OnClickListener {

    private static final int NORMAL_STATU = 0;
    private static final int PRESS_STATU = 1;

    private int curStatus = NORMAL_STATU;
    private int normalDrawableId = R.drawable.ic_launcher_background;
    private int pressDrawableId = R.drawable.ic_launcher_background;

    private LittleImageButtonClickListener littleImageButtonClickListener = null;

    /**
     * 设置点击自定义事件监听接口
     *
     * @param littleImageButtonClickListener 自定义监听事件接口
     */
    public void setLittleImageButtonClickListener(LittleImageButtonClickListener littleImageButtonClickListener) {
        this.littleImageButtonClickListener = littleImageButtonClickListener;
    }

    public LittleImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LittleImageButton);
        LogUtil.D("test", "typeArray == null? " + (typedArray == null));

        if (typedArray != null) {
            normalDrawableId = typedArray.getResourceId(R.styleable.LittleImageButton_img_normal, R.drawable.ic_launcher_background);
            pressDrawableId = typedArray.getResourceId(R.styleable.LittleImageButton_img_press, R.drawable.ic_launcher_background);
            typedArray.recycle();
        }
        //初始化背景
        setBackgroundResource(normalDrawableId);
        setOnClickListener(LittleImageButton.this);
    }

    @Override
    public void onClick(View view) {
        //图片切换逻辑
        if (curStatus == NORMAL_STATU) {
            curStatus = PRESS_STATU;
            setBackgroundResource(pressDrawableId);
        } else {
            curStatus = NORMAL_STATU;
            setBackgroundResource(normalDrawableId);
        }
        //必须等图片切换逻辑处理完成
        if (littleImageButtonClickListener != null) {
            littleImageButtonClickListener.onClick(curStatus);
        }
    }

    /**
     * 设置点击后的图片资源ID
     *
     * @param id 点击后的图片资源ID
     */
    public void setPressDrawableRes(@IdRes int id) {
        pressDrawableId = id;
    }

    /**
     * 设置时默认时的图片资源ID
     *
     * @param id 默认时的图片资源ID
     */
    public void setNormalDrawableRes(@IdRes int id) {
        normalDrawableId = id;
    }

    /**
     * 提供给外部修改状态的接口
     * @param status 状态值
     */
    public void setSelected(boolean status){
        if(status){
            if (curStatus == NORMAL_STATU) {
                curStatus = PRESS_STATU;
                setBackgroundResource(pressDrawableId);
            } else {
                curStatus = NORMAL_STATU;
                setBackgroundResource(normalDrawableId);
            }
        }
    }

}
