package com.hutcwp.main.ui.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutcwp.main.R;
import com.hutcwp.main.model.ArticleCategory;
import com.hutcwp.main.ui.read.adapter.ArticleCategoryAdapter;
import com.hutcwp.main.ui.view.LoadView;
import com.hutcwp.main.util.SingToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ReadFragment extends Fragment {

    private static final String TAG = "ReadFragment";
    private static final String host = "http://gank.io/xiandu";

    private LoadView mLoadView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArticleCategoryAdapter mAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_read, container, false);
        initView(rootView);
        initListener();
        initialize();
        return rootView;
    }

    private void initListener() {
        mLoadView.setListener(new LoadView.ClickListener() {
            @Override
            public void click() {
                mLoadView.showInflate();
                getDataFormServer();
            }
        });
    }

    private void initView(View rootView) {
        mViewPager = rootView.findViewById(R.id.viewpager);
        mTabLayout = rootView.findViewById(R.id.tablayout);
        mLoadView = rootView.findViewById(R.id.loadview);
    }

    private void initTabLayout() {
        mViewPager.setOffscreenPageLimit(mViewPager.getAdapter().getCount());
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initialize() {
        mAdapter = new ArticleCategoryAdapter(getChildFragmentManager(), getContext());
        mViewPager.setAdapter(mAdapter);
        // startCheckFail();
        getDataFormServer();
    }

    private void startCheckFail() {
        mCompositeDisposable.add(Flowable
                .timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mAdapter.getCount() == 0) {
                            mLoadView.showInflateFail();
                        }
                    }
                }));
    }

    public void loadCategoryFromData(List<ArticleCategory> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }

        Log.d(TAG, "loadCategoryFromData#list = " + datas.size());
        for (ArticleCategory category : datas) {
            ArticleCategoryFragment fragment = new ArticleCategoryFragment();
            HashMap<String, String> args = new HashMap<>();
            args.put("url", category.getUrl());
            fragment.putArgs(args);
            mFragmentList.add(fragment);
        }
        mAdapter.setNewData(mFragmentList);
        mAdapter.setTitles(datas);

        initTabLayout();
        mLoadView.dismiss();
    }

    private void getDataFormServer() {
        SingToast.checkNetToast();
        Disposable disposable = Observable.just(host)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<ArticleCategory>>() {
                    @Override
                    public List<ArticleCategory> apply(String url) throws IOException {
                        return parseDataWithJsoup(url);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ArticleCategory>>() {
                    @Override
                    public void accept(List<ArticleCategory> data) {
                        loadCategoryFromData(data);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, " error = " + throwable.getMessage());
                        mLoadView.showInflateFail();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private List<ArticleCategory> parseDataWithJsoup(String url) throws IOException {
        List<ArticleCategory> list = new ArrayList<>();
        Document doc = Jsoup.connect(url).timeout(5000).get();
        Element cate = doc.select("div#xiandu_cat").first();
        Elements links = cate.select("a[href]");
        for (Element element : links) {
            ArticleCategory category = new ArticleCategory();
            category.setName(element.text());
            category.setUrl(element.attr("abs:href"));
            list.add(category);
            Log.d(TAG, "Item ---> name: " + category.getName());
        }

        if (list.size() > 0) {
            list.get(0).setUrl(host + "/wow");
        }
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.dispose();
    }
}
