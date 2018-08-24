package com.hutcwp.main.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutcwp.main.R;
import com.hutcwp.main.adapter.SubAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private ViewPager mViewPager;


    private static final String TAG = "ChatFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        initView(rootView);
        initlize();
        return rootView;
    }

    private void initlize() {
        List<Fragment> datas = new ArrayList<>();
        datas.add(new HomeFragment());
        datas.add(new HomeFragment());
        datas.add(new HomeFragment());
        mViewPager.setAdapter(new SubAdapter(getChildFragmentManager(), getContext(), datas));
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
    }

    private void initView(View rootView) {
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrooled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected : " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged");
        }
    }
}
