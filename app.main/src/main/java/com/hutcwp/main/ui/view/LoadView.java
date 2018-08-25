package com.hutcwp.main.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hutcwp.main.R;

public class LoadView extends RelativeLayout {

    private RelativeLayout mRvPlaceholder;
    private TextView mTvInflateFail;
    private ProgressBar mPbInflate;
    private ClickListener listener;

    public LoadView(Context context) {
        this(context, null, 0);
    }

    public LoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initListener();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_loadview, this);
        mRvPlaceholder = findViewById(R.id.layout_placeholder);
        mTvInflateFail = findViewById(R.id.tv_inflate_fail);
        mPbInflate = findViewById(R.id.progressBar);
    }

    private void initListener() {
        mTvInflateFail.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.click();
                }
            }
        });
    }

    public void showInflate() {
        mRvPlaceholder.setVisibility(View.VISIBLE);
        mPbInflate.setVisibility(View.VISIBLE);
        mTvInflateFail.setVisibility(View.GONE);
    }

    public void showInflateFail() {
        mRvPlaceholder.setVisibility(View.VISIBLE);
        mPbInflate.setVisibility(View.GONE);
        mTvInflateFail.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        mRvPlaceholder.setVisibility(View.GONE);
    }

    public interface ClickListener {
        void click();
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

}
