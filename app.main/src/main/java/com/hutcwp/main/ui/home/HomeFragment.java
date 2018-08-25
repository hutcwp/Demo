package com.hutcwp.main.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hutcwp.main.R;
import com.hutcwp.main.db.repos.SignRecordsRepos;
import com.hutcwp.main.ui.home.view.RoteMenuView;
import com.hutcwp.main.util.DateUtil;
import com.hutcwp.main.util.SingToast;

public class HomeFragment extends Fragment {

    private Button mBtnSign;
    private RoteMenuView mRoteMenuView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);
        initView(rootView);
        initListener();
        init();
        return rootView;
    }

    private void init() {
        if(DateUtil.isInTime("08:00:00","11:00:00")){
            mBtnSign.setText("早安");
        }else if(DateUtil.isInTime("01:00:00","04:00:00")){
            mBtnSign.setText("晚安");
        }
    }


    private void initListener() {
        mBtnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DateUtil.isInTime("08:00:00", "11:00:00")
                        || DateUtil.isInTime("01:00:00", "04:00:00")) {
                    SignRecordsRepos.getInstance().signRecord();
                    SingToast.toast("记录成功！");
                }else {
                    SingToast.toast("当前不在记录时间段！");
                }
            }
        });

        mRoteMenuView.setClickListener(new RoteMenuView.ClickListener() {
            @Override
            public void click1() {
                getActivity().startActivity(new Intent(getContext(), SignRecordActivity.class));
            }
        });
    }

    private void initView(View rootView) {
        mRoteMenuView = rootView.findViewById(R.id.rotemenuview);
        mBtnSign = rootView.findViewById(R.id.btn_sign);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
