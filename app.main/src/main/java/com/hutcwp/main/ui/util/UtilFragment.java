package com.hutcwp.main.ui.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hutcwp.main.R;

public class UtilFragment extends Fragment {

    TextView mTvPassword ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_util, container, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mTvPassword = rootView.findViewById(R.id.tv_password);
        mTvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),PasswordBookActivity.class));
            }
        });
    }


}
