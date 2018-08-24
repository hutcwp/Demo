package me.hutcwp.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import net.wequick.small.Small;

/**
 * Created by hutcwp on 2018/8/19 23:22
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class LaunchActivity extends AppCompatActivity{

    private static final long MIN_INTRO_DISPLAY_TIME = 1000000000; // mμs -> 1.0s

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final long tStart = System.nanoTime();
        Small.setUp(LaunchActivity.this, new net.wequick.small.Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                long tEnd = System.nanoTime();
                long offset = tEnd - tStart;
                if (offset < MIN_INTRO_DISPLAY_TIME) {
                    // 这个延迟仅为了让 "Small Logo" 显示足够的时间, 实际应用中不需要
                    getWindow().getDecorView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Small.openUri("main", LaunchActivity.this);
                            finish();
                        }
                    }, 5000);
                } else {
                    Small.openUri("main", LaunchActivity.this);
                    finish();
                }
            }
        });
    }
}
