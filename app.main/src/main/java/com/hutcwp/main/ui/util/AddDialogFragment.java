package com.hutcwp.main.ui.util;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hutcwp.main.R;
import com.hutcwp.main.db.repos.AccountRepos;
import com.hutcwp.main.util.SingToast;

import java.util.zip.Inflater;

/**
 * Created by hutcwp on 2018/8/27 00:40
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class AddDialogFragment extends DialogFragment {

    private LayoutInflater mInflater;
    private ViewGroup mContainer;

    private Button btnConfirm;
    private Button btnCancle;

    private EditText evName;
    private EditText evPassword;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        this.mContainer = container;
        View view = mInflater.inflate(R.layout.fragment_add, mContainer);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(evName.getText())||TextUtils.isEmpty(evPassword.getText())){
                    SingToast.toast("内容不能为空！");
                    return;
                }

                final Account account = new Account();
                account.setUsername(evName.getText().toString().trim());
                account.setPassword(evPassword.getText().toString().trim());
                account.setType(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AccountRepos.getmInstance().addAccount(account);
                    }
                }).start();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });
    }

    private void initView(View rootView) {
        btnCancle = rootView.findViewById(R.id.btn_cancel);
        btnConfirm = rootView.findViewById(R.id.btn_confirm);
        evName = rootView.findViewById(R.id.ev_name);
        evPassword = rootView.findViewById(R.id.ev_password);
    }

    // @NonNull
    // @Override
    // public Dialog onCreateDialog(Bundle savedInstanceState) {
    //
    //     AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AppTheme);
    //     builder.setTitle("对话框");
    //     builder.setPositiveButton("确定", null);
    //     builder.setNegativeButton("取消", null);
    //     return builder.create();
    //
    // }


}
