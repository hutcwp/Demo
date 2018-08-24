package com.hutcwp.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hutcwp.main.R;
import com.hutcwp.main.db.repos.RecordsRepos;
import com.hutcwp.main.model.SignRecord;
import com.hutcwp.main.ui.activity.SignRecordActivity;
import com.hutcwp.main.ui.view.RoteMenuView;
import com.hutcwp.main.util.DateUtil;

public class OtherFragment extends Fragment {

    private Button btnSign;

    private RoteMenuView rotemenuview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_other, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    private void initListener() {
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordsRepos.getInstance().signRecord();
            }
        });

        rotemenuview.setClickListener(new RoteMenuView.clickListener() {
            @Override
            public void click1() {
                getActivity().startActivity(new Intent(getContext(), SignRecordActivity.class));
            }

            @Override
            public void click2() {

            }

            @Override
            public void click3() {

            }

            @Override
            public void click4() {

            }

            @Override
            public void click5() {

            }

            @Override
            public void click6() {

            }

            @Override
            public void click7() {

            }

            @Override
            public void click8() {

            }

            @Override
            public void click9() {

            }
        });
    }

    private void initView(View rootView) {
        rotemenuview = rootView.findViewById(R.id.rotemenuview);
        btnSign = rootView.findViewById(R.id.btn_sign);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
