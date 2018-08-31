package com.hutcwp.main.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hutcwp.main.R;
import com.hutcwp.main.ui.home.HomeFragment;
import com.hutcwp.main.ui.read.ReadFragment;
import com.hutcwp.main.ui.util.UtilFragment;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static String CUR_FRAGMENT = "";
    private static final String OTHER_FRAGMENT = "OTHER_FRAGMENT";
    private static final String UTIL_FRAGMENT = "UTIL_FRAGMENT";
    private static final String READ_FRAGMENT = "READ_FRAGMENT";
    private static final String DYNAMIC_FRAGMENT = "DYNAMIC_FRAGMENT";


    private Fragment curFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomBar();
        switchFragment(OTHER_FRAGMENT);
    }

    private void initBottomBar() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.white);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.img_chat, "阅读").setActiveColorResource(R.color.greenery))
                .addItem(new BottomNavigationItem(R.drawable.img_other, "首页").setActiveColorResource(R.color.greenery))
                .addItem(new BottomNavigationItem(R.drawable.img_util, "工具").setActiveColorResource(R.color.greenery))
                .addItem(new BottomNavigationItem(R.drawable.img_dynamic, "动态").setActiveColorResource(R.color.greenery))
                .setFirstSelectedPosition(1)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int pos) {
                switch (pos) {
                    case 0:
                        switchFragment(READ_FRAGMENT);
                        break;
                    case 1:
                        switchFragment(OTHER_FRAGMENT);
                        break;
                    case 2:
                        switchFragment(UTIL_FRAGMENT);
                        break;
                    case 3:
                        startDynamicPlugin();
                }
            }

            @Override
            public void onTabUnselected(int i) {

            }

            @Override
            public void onTabReselected(int i) {

            }
        });
    }

    private void startDynamicPlugin() {
        Small.openUri("dynamic", MainActivity.this);
    }

    private void switchFragment(String tag) {
        if (tag.equals(CUR_FRAGMENT)) {
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = getFragmentByTag(tag);
        }

        if (curFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(curFragment).commitAllowingStateLoss();
        }
        loadFragment(fragment, tag);
        CUR_FRAGMENT = tag;
        curFragment = fragment;
    }

    private Fragment getFragmentByTag(String tag) {
        switch (tag) {
            case READ_FRAGMENT:
                return new ReadFragment();
            case OTHER_FRAGMENT:
                return new HomeFragment();
            case UTIL_FRAGMENT:
                return new UtilFragment();
            default:
                return null;
        }
    }

    public void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.container, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
    }

}
