package me.hutcwp.app.dynamic.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.bean.User;
import me.hutcwp.app.dynamic.other.CircleMovementMethod;
import me.hutcwp.app.dynamic.util.LogUtil;


/**
 * Created by Administrator on 2018/1/16.
 */

public class LikesView extends android.support.v7.widget.AppCompatTextView {

    private Context mContext;
    private List<User> list;

    public LikesView(Context context) {
        this(context, null);
    }

    public LikesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    /**
     * 设置点赞数据
     *
     * @param list
     */
    public void setList(List<User> list) {
        this.list = list;
    }

    /**
     * 刷新点赞列表
     */
    public void notifyDataSetChanged() {
        if (list == null || list.size() <= 0) {
            return;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setImageSpan());
        for (int i = 0; i < list.size(); i++) {
            User item = list.get(i);
            LogUtil.D("test", "item==null?  " + (item == null) + "list==null" + (list == null) + " list size " + list.size());
            builder.append(setClickableSpan(item.getUsername(), item));
            if (i != list.size() - 1) {
                builder.append(" , ");
            } else {
                if (list.size() != 0)
                    builder.append(" " + "等" + list.size() + "人觉得很赞");
            }
        }

        setText(builder);
        setMovementMethod(new CircleMovementMethod(0xffcccccc, 0xffcccccc));
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 设置评论用户名字点击事件
     *
     * @param item
     * @param user
     * @return
     */
    public SpannableString setClickableSpan(final String item, final User user) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //  评论用户名字点击事件
//                Toast.makeText(mContext, bean.getPhone(), Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onItemClick(0, user);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的文字颜色
                ds.setColor(0xff387dcc);
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置点赞图标
     *
     * @return
     */
    private SpannableString setImageSpan() {
        String text = "  ";
        SpannableString imgSpanText = new SpannableString(text);
        imgSpanText.setSpan(new ImageSpan(getContext(), R.mipmap.img_like_icon, DynamicDrawableSpan.ALIGN_BASELINE),
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }

    private onItemClickListener listener;

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position, User bean);
    }

}