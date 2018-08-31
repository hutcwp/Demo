package me.hutcwp.app.dynamic.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.base.BaseApplication;


/**
 * 评论弹框
 * Created by only on 2017/6/27.
 */

public class CommentPopupWindow extends PopupWindow {

    private final String TAG = "CommentPopupWindow";
    private Context mContext;
    private CommentSendClick sendClick;

    private View mContentView; //整体布局
    private EditText mPopupCommentEdt;
    private TextView mPopupCommentSendTv;

    public CommentPopupWindow(Context mContext, CommentSendClick sendClick) {
        super(mContext);
        this.mContext = mContext;
        this.sendClick = sendClick;
        initPopup();
    }

    private void initPopup() {
        mContentView = View.inflate(mContext, R.layout.popup_comment_input, null);
        mPopupCommentEdt = (EditText) mContentView.findViewById(R.id.edt_popup_comment);
        mPopupCommentSendTv = (TextView) mContentView.findViewById(R.id.tv_popup_send);

        setContentView(mContentView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置弹出动画
//        setAnimationStyle();
        //使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        //设置允许在外点击消失
        setOutsideTouchable(true);
        //设置一个空白背景
        setBackgroundDrawable(new BitmapDrawable());
        //显示在键盘上方
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mPopupCommentSendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = mPopupCommentEdt.getText().toString().trim();
                if (result.length() <= 0) {
                    Toast.makeText(mContext, "还没有填写任何内容哦！", Toast.LENGTH_SHORT).show();
                } else {
                    sendClick.onSendClick(v, result);
                    //hideSoftInput必须放在dismiss之前，否则会因为Token为空无法关闭系统键盘
                    dismiss();
                }
            }
        });
    }

    /**
     * 外部调用显示
     */
    public void showReveal() {
        if (mContentView == null) {
            Toast.makeText(mContext, "创建失败", Toast.LENGTH_SHORT).show();
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    showKeyboard(mPopupCommentEdt);
                }
            }, 200);
            //显示并设置位置
            showAtLocation(mContentView, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void update(int x, int y, int width, int height) {
        super.update(x, y, width, height);
        Log.e(TAG, "x:" + x + ",y:" + y + ",w:" + width + ",h:" + height);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) BaseApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 关闭键盘
     *
     * @param context
     * @param popupCommentEdt
     */
    private void hideSoftInput(Context context, EditText popupCommentEdt) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(popupCommentEdt.getWindowToken(), 0);
        }
    }


    @Override
    public void dismiss() {
        hideSoftInput(mContext, mPopupCommentEdt);
        super.dismiss();
    }

    /**
     * 评论提交接口
     */
    public interface CommentSendClick {
        void onSendClick(View v, String result);
    }

}
