package com.hutcwp.main.ui.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hutcwp.main.R;
import com.hutcwp.main.model.Article;
import com.hutcwp.main.model.ArticleCategory;
import com.hutcwp.main.ui.read.adapter.ArticleAdapter;
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
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ArticleCategoryFragment extends Fragment {

    private static final String TAG = "ArticleCategoryFragment";
    private int cutPage = 1;
    private String baseUrl = "";
    private RecyclerView recyclerView;
    private ArticleAdapter adapter;
    private LoadView mLoadView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public void putArgs(HashMap<String, String> args) {
        Bundle data = new Bundle();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            data.putString(entry.getKey(), entry.getValue());
        }
        setArguments(data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_item_home, container, false);
        initView(root);
        initialize();
        loadData();
        return root;
    }

    private void initView(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        mLoadView = root.findViewById(R.id.loadview);
        mSwipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
    }

    public void initialize() {
        baseUrl = getArguments().getString("url");
        adapter = new ArticleAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cutPage = 1;
                getDataFromServer(cutPage);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    getDataFromServer(cutPage++);
                }
            }
        });
    }

    private void loadData() {
        mLoadView.showInflate();
        getDataFromServer(cutPage);
    }

    public void getDataFromServer(final int page) {
        SingToast.checkNetToast();
        String url = baseUrl + "/page/" + page;
        Disposable disposable = Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<Article>>() {
                    @Override
                    public List<Article> apply(String url) throws Exception {
                        return parseArticleWithJsoup(url);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Article>>() {
                    @Override
                    public void accept(List<Article> readItems) {
                        if (page == cutPage) {
                            cutPage = page;
                            requestSuccess(readItems, true);
                        } else {
                            requestSuccess(readItems, false);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, "error = " + throwable.getMessage());
                        requestFail();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void requestFail() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadView.showInflateFail();
    }

    private void requestSuccess(List<Article> readItems, boolean isNewData) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (readItems == null) {
            mLoadView.showInflateFail();
            return;
        }

        if (isNewData) {
            adapter.setNewData(readItems);
        } else {
            adapter.addData(readItems);
        }
        mLoadView.dismiss();
    }

    private List<Article> parseArticleWithJsoup(String url) throws IOException {
        Log.d(TAG, "url = " + url);
        List<Article> articleList = new ArrayList<>();
        Document doc = Jsoup.connect(url).timeout(5000).get();
        Element element = doc.select("div.xiandu_items").first();
        Elements items = element.select("div.xiandu_item");
        for (Element ele : items) {
            Article item = new Article();
            Element left = ele.select("div.xiandu_left").first();
            Element right = ele.select("div.xiandu_right").first();
            item.setName(left.select("a[href]").text());
            item.setFrom(right.select("a").attr("title"));
            item.setUpdateTime(left.select("span").select("small").text());
            item.setUrl(left.select("a[href]").attr("href"));
            item.setIcon(right.select("img").first().attr("src"));
            articleList.add(item);
        }
        return articleList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
