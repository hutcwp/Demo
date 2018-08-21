package com.hutcwp.main;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.view.ViewPager;

import com.hutcwp.main.model.Item;

import net.wequick.small.Small;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private static String[] sUris = new String[]{"home", "mine", "stub"};
    private static String[] sTitles = new String[]{"Home", "Mine", "Stub"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().beginTransaction().add(new HomeFragment(),"Home").commitAllowingStateLoss();

        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        List<Fragment> datas = new ArrayList<>();
        datas.add(new HomeFragment());
        datas.add(new HomeFragment());
        datas.add(new HomeFragment());
        mViewPager.setAdapter(new SubAdapter(getSupportFragmentManager(), this, datas));
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
    }

    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrooled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected : "+position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged");
        }
    }

    public void click(View view) {
        Small.openUri("livebiz", MainActivity.this);
    }
}
