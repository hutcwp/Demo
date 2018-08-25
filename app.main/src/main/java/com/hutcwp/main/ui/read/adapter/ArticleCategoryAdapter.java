package com.hutcwp.main.ui.read.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.hutcwp.main.model.ArticleCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理Fragment 的 Adapter
 */
public class ArticleCategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mViewList = new ArrayList<>();
    private List<ArticleCategory> mTitles = new ArrayList<>();
    private static final String TAG = "ArticleCategoryAdapter";

    public ArticleCategoryAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? super.getPageTitle(position) : mTitles.get(position).getName();
    }

    public void setNewData(List<Fragment> newData) {
        Log.d(TAG, "setNewData # size : " + newData.size());
        this.mViewList = newData;
        notifyDataSetChanged();
    }

    public void setTitles(List<ArticleCategory> mTitles) {
        this.mTitles = mTitles;
    }
}
