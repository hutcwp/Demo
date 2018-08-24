package com.hutcwp.main.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hutcwp.main.R;
import com.hutcwp.main.adapter.SignRecordAdapter;
import com.hutcwp.main.db.repos.RecordsRepos;
import com.hutcwp.main.model.SignRecord;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SignRecordActivity extends AppCompatActivity {

    private static final String TAG = "SignRecordActivity";

    private RecyclerView mRvRecords;
    private List<SignRecord> datas = new ArrayList<>();
    private SignRecordAdapter mAdapter = new SignRecordAdapter(datas);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_record);

        mRvRecords = (RecyclerView) findViewById(R.id.rv_records);

        mRvRecords.setAdapter(mAdapter);
        mRvRecords.setLayoutManager(new LinearLayoutManager(SignRecordActivity.this));

        loadDatas();
    }

    @SuppressLint("CheckResult")
    private void loadDatas() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<List<SignRecord>>>() {
                    @Override
                    public ObservableSource<List<SignRecord>> apply(String s) {
                        List<SignRecord> records = RecordsRepos.getInstance().getSignRecords();
                        return Observable.just(records);
                    }
                })
                .subscribe(new Consumer<List<SignRecord>>() {
                    @Override
                    public void accept(List<SignRecord> signRecords) {
                        if (signRecords != null) {
                            mAdapter.setNewData(signRecords);
                        } else {
                            //设置空集合
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, "error : " + throwable);
                    }
                });
    }

}
