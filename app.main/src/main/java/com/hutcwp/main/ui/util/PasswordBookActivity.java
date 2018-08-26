package com.hutcwp.main.ui.util;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hutcwp.main.R;
import com.hutcwp.main.db.repos.AccountRepos;
import com.hutcwp.main.util.SingToast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PasswordBookActivity extends AppCompatActivity {

    private static final String TAG = "PasswordBookActivity";

    Button btnAdd;
    RecyclerView mRvPassword;

    PasswordAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_book);

        initView();
        initListener();
        initData();
    }

    private void initListener() {
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setUsername("hutcwp");
        accountList.add(account);
        accountList.add(account);
        accountList.add(account);

        mAdapter = new PasswordAdapter(accountList);
        mRvPassword.setAdapter(mAdapter);
        mRvPassword.addItemDecoration(new DividerItemDecoration(PasswordBookActivity.this, DividerItemDecoration.VERTICAL));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                SingToast.toast("添加");
            }

        });
    }


    public void showDialog() {
        AddDialogFragment dialogFragment = new AddDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "DialogFragment");

    }


    private void initView() {
        btnAdd = (Button) findViewById(R.id.btn_add);
        mRvPassword = (RecyclerView) findViewById(R.id.rv_pwd);
    }


    @SuppressLint("CheckResult")
    public void initData() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<Account>>() {
                    @Override
                    public List<Account> apply(String s) throws Exception {
                        return AccountRepos.getmInstance().getAccounts();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Account>>() {
                    @Override
                    public void accept(List<Account> accounts) throws Exception {
                        mAdapter.setNewData(accounts);
                    }
                });
    }


}
