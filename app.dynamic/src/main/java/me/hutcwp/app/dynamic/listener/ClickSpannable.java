package me.hutcwp.app.dynamic.listener;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Administrator on 2018/1/16.
 *
 * SpannableStringBuilder 点击事件
 */

public class ClickSpannable extends ClickableSpan implements
        View.OnClickListener {

    private View.OnClickListener onClickListener;

    public ClickSpannable(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onClick(View widget) {
        onClickListener.onClick(widget);
    }
}
