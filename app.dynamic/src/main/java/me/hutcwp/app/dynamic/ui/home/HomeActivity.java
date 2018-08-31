package me.hutcwp.app.dynamic.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.hutcwp.app.dynamic.R;
import me.hutcwp.app.dynamic.adapter.TopicAdapter;
import me.hutcwp.app.dynamic.base.BaseActivity;
import me.hutcwp.app.dynamic.bean.Topic;
import me.hutcwp.app.dynamic.databinding.ActivityMainBinding;
import me.hutcwp.app.dynamic.model.TopicModelImp;
import me.hutcwp.app.dynamic.other.SpacesItemDecoration;
import me.hutcwp.app.dynamic.ui.PublishDynamicActivity;


public class HomeActivity extends BaseActivity implements IHomeView {

    private static final String TAG = "HomeActivity";

    private ActivityMainBinding mBinding;

    private HomePresent homePresent;
    private TopicAdapter topicAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBinding = (ActivityMainBinding) getBinding();
        homePresent = new HomePresent(this);
        mBinding.rvTopic.addItemDecoration(new SpacesItemDecoration(14));
        mBinding.btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                initData();
                gotoActivity(PublishDynamicActivity.class);
            }
        });
        //设置下拉刷新状态栏的颜色
        mBinding.swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary));
        mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBinding.swipeRefreshLayout.setRefreshing(true);
                homePresent.getTopics();
            }
        });
    }

    @Override
    protected void loadData() {


    }

    /**
     * 初始化话题列表数据
     */
    private void initData() {
        try {
            List<Topic> topicList = TopicModelImp.getInstance().getTopics();
            Log.d(TAG, "topicList.size = " + topicList.size());
            topicAdapter = new TopicAdapter(HomeActivity.this, topicList);
            mBinding.rvTopic.setAdapter(topicAdapter);
            mBinding.swipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            Log.e(TAG, "error = " + e.getMessage());
        }
    }

    @Override
    public void showTopics() {
        initData();
    }

    @Override
    public void showComment() {

    }

    @Override
    public void showReply() {

    }

    @Override
    public void showlikes() {

    }

    @Override
    public void updateTopics() {

    }

    @Override
    public void updateComment() {

    }

    @Override
    public void updateReply() {

    }

    @Override
    public void updatelikes() {

    }

    @Override
    public void showNullLayout() {

    }
}
