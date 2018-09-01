package com.hutcwp.main.ui.main;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hutcwp.main.R;
import com.hutcwp.main.ui.home.HomeFragment;
import com.hutcwp.main.ui.read.ReadFragment;
import com.hutcwp.main.ui.util.UtilFragment;
import com.hutcwp.main.util.BasicConfig;
import com.hutcwp.main.util.FileUtils;
import com.hutcwp.main.util.SingToast;
import com.hutcwp.main.util.ThreadUtils;

import net.wequick.small.Small;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        checkPermission();
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
                        checkUpgrade();
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


    // **************************************************************
    // **************************************************************
    // **************************************************************
    // **************************************************************

    /**
     * 插件化更新
     */
    private void checkUpgrade() {
        new UpgradeManager(this).checkUpgrade();
    }

    private static class UpgradeManager {

        private static class UpdateInfo {
            public String packageName;
            public String downloadUrl;
        }

        private static class UpgradeInfo {
            public JSONObject manifest;
            public List<UpdateInfo> updates;
        }

        private interface OnResponseListener {
            void onResponse(UpgradeInfo info);
        }

        private interface OnUpgradeListener {
            void onUpgrade(boolean succeed);
        }

        private static class ResponseHandler extends Handler {
            private OnResponseListener mListener;

            public ResponseHandler(OnResponseListener listener) {
                mListener = listener;
            }

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mListener.onResponse((UpgradeInfo) msg.obj);
                        break;
                }
            }
        }

        private ResponseHandler mResponseHandler;

        private Context mContext;
        private ProgressDialog mProgressDlg;

        public UpgradeManager(Context context) {
            mContext = context;
        }

        public void checkUpgrade() {
            mProgressDlg = ProgressDialog.show(mContext, "Small", "检查更新...", false, true);
            requestUpgradeInfo(Small.getBundleVersions(), new OnResponseListener() {
                @Override
                public void onResponse(UpgradeInfo info) {
                    mProgressDlg.setMessage("升级中...");
                    upgradeBundles(info,
                            new OnUpgradeListener() {
                                @Override
                                public void onUpgrade(boolean succeed) {
                                    mProgressDlg.dismiss();
                                    mProgressDlg = null;
                                    String text = succeed ?
                                            "升级成功!切换到后台并返回到前台来查看更改"
                                            : "升级失败!";
                                    SingToast.toast(text);
                                }
                            });
                }
            });
        }

        /**
         * @param versions
         * @param listener
         */
        private void requestUpgradeInfo(Map versions, OnResponseListener listener) {
            System.out.println(versions); // this should be passed as HTTP parameters
            mResponseHandler = new ResponseHandler(listener);
            ThreadUtils.runOnLongBackThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Example HTTP request to get the upgrade bundles information.
                        // Json format see http://wequick.github.io/small/upgrade/bundles.json
                        String jsonUrl = "https://raw.githubusercontent.com/hutcwp/img-floder/master/bundle.json";
                        URL url = new URL(jsonUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        InputStream is = conn.getInputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) != -1) {
                            sb.append(new String(buffer, 0, length));
                        }

                        // Parse json
                        JSONObject jo = new JSONObject(sb.toString());
                        JSONObject mf = jo.has("manifest") ? jo.getJSONObject("manifest") : null;
                        JSONArray updates = jo.getJSONArray("updates");
                        int N = updates.length();
                        List<UpdateInfo> infos = new ArrayList<UpdateInfo>(N);
                        for (int i = 0; i < N; i++) {
                            JSONObject o = updates.getJSONObject(i);
                            UpdateInfo info = new UpdateInfo();
                            info.packageName = o.getString("pkg");
                            info.downloadUrl = o.getString("url");
                            infos.add(info);
                        }

                        // Post message
                        UpgradeInfo ui = new UpgradeInfo();
                        ui.manifest = mf;
                        ui.updates = infos;
                        Message.obtain(mResponseHandler, 1, ui).sendToTarget();
                    } catch (Exception e) {
                        e.printStackTrace();
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDlg.dismiss();
                                mProgressDlg = null;
                                SingToast.toast("更新失败");
                            }
                        });
                    }
                }
            });
        }

        private static class DownloadHandler extends Handler {
            private OnUpgradeListener mListener;

            public DownloadHandler(OnUpgradeListener listener) {
                mListener = listener;
            }

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        mListener.onUpgrade((Boolean) msg.obj);
                        break;
                }
            }
        }

        private DownloadHandler mHandler;

        private void upgradeBundles(final UpgradeInfo info,
                                    final OnUpgradeListener listener) {
            // Just for example, you can do this by OkHttp or something.
            mHandler = new DownloadHandler(listener);
            ThreadUtils.runOnLongBackThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Update manifest
                        if (info.manifest != null) {
                            if (!Small.updateManifest(info.manifest, false)) {

                                Message.obtain(mHandler, 1, false).sendToTarget();
                                return;
                            }
                        }
                        // Download bundles
                        List<UpdateInfo> updates = info.updates;
                        for (UpdateInfo u : updates) {
                            // Get the patch file for downloading
                            net.wequick.small.Bundle bundle = Small.getBundle(u.packageName);
                            File file = bundle.getPatchFile();

                            // Download
                            URL url = new URL(u.downloadUrl);
                            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                            InputStream is = urlConn.getInputStream();
                            OutputStream os = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = is.read(buffer)) != -1) {
                                os.write(buffer, 0, length);
                            }
                            os.flush();
                            os.close();
                            is.close();
                            // Upgrade
                            bundle.upgrade();
                        }
                        Message.obtain(mHandler, 1, true).sendToTarget();
                    } catch (IOException e) {
                        Log.e(TAG, "error = " + e.getMessage());
                        e.printStackTrace();
                        Message.obtain(mHandler, 1, false).sendToTarget();
                        ThreadUtils.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProgressDlg.dismiss();
                                mProgressDlg = null;
                                SingToast.toast("更新失败");
                            }
                        });
                    }
                }
            });
        }
    }


    public static int REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("hutcwp", "checkPermission: 已经授权！");
        }
    }

}
