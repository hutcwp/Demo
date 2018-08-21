package com.hutcwp.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

public class SubAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mViewList;
    private static final String TAG = "SubAdapter";

    public SubAdapter(FragmentManager fm, Context mContext, List<Fragment> mViewList) {
        super(fm);
        this.mContext = mContext;
        this.mViewList = mViewList;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem : " + position);
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        Log.d(TAG, "size:" + mViewList.size());
        return mViewList.size();
    }
}
