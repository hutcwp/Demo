package com.hutcwp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.behaviour.BottomNavBarFabBehaviour;
import com.hutcwp.main.ui.fragment.ChatFragment;
import com.hutcwp.main.ui.fragment.OtherFragment;
import com.hutcwp.main.ui.fragment.UtilFragment;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BottomNavigationMenuView navigationMenuView;
    private LinearLayout bottom_nav;


    private static final int OTHER_FRAGMENT = 1;
    private static final int UTIL_FRAGMENT = 2;
    private static final int CHAT_FRAGMENT = 3;
    private static  int CUR_FRAGMENT = -1;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private TabLayout mTabLayout;

    private static String[] sUris = new String[]{"home", "mine", "stub"};
    private static String[] sTitles = new String[]{"Home", "Mine", "Stub"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        switchFragment(UTIL_FRAGMENT);
    }

    private void initView() {
        bottom_nav = (LinearLayout) findViewById(R.id.bottom_nav);

        ImageView imgOne = (ImageView) findViewById(R.id.img_one);
        ImageView imgTwo = (ImageView) findViewById(R.id.img_two);
        ImageView imgThree = (ImageView) findViewById(R.id.img_three);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.img_one:
                        switchFragment(CHAT_FRAGMENT);
                        break;
                    case R.id.img_two:
                        switchFragment(OTHER_FRAGMENT);
                        break;
                    case R.id.img_three:
                        switchFragment(UTIL_FRAGMENT);
                        break;
                    default:
                        break;
                }
            }
        };
        imgOne.setOnClickListener(listener);
        imgTwo.setOnClickListener(listener);
        imgThree.setOnClickListener(listener);
    }

    private void switchFragment(int tag) {
        if (tag == CUR_FRAGMENT) {
            return;
        }

        if (tag == CHAT_FRAGMENT) {
            loadFragment(new ChatFragment());
        } else if (tag == OTHER_FRAGMENT) {
            loadFragment(new OtherFragment());
        } else if (tag == UTIL_FRAGMENT) {
            loadFragment(new UtilFragment());
        }
        CUR_FRAGMENT = tag;
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss();
    }

    public void click(View view) {
        Small.openUri("livebiz", MainActivity.this);
    }
}
