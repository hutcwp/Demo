package com.hutcwp.main.db.repos;

import android.util.Log;

import com.hutcwp.main.db.AppDatabase;
import com.hutcwp.main.db.dao.AccountDao;
import com.hutcwp.main.db.entitys.AccountEntity;
import com.hutcwp.main.ui.util.Account;
import com.hutcwp.main.util.SingToast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hutcwp on 2018/8/26 23:25
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class AccountRepos {

    private static final String TAG = "AccountRepos";

    private static AccountRepos mInstance = new AccountRepos();

    public static AccountRepos getmInstance() {
        return mInstance;
    }

    public void addAccount(Account account) {
        Log.d(TAG, "addAccount: ");
        getAccountDao().insert(Transform.toEntity(account));
    }

    public List<Account> getAccounts() {
        List<AccountEntity> entityList = getAccountDao().getAllAccount();
        List<Account> beanList = new ArrayList<>();
        for (AccountEntity entity : entityList) {
            beanList.add(Transform.toBean(entity));
        }
        return beanList;
    }

    private AccountDao getAccountDao() {
        return AppDatabase.getInstance().getAccountDao();
    }


    public void deleteAccount(final Account bean) {
        SingToast.toast("删除");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAccountDao().deleteById(Transform.toEntity(bean).getId());

            }
        }).start();

    }


    public static class Transform {

        public static AccountEntity toEntity(Account bean) {
            AccountEntity entity = new AccountEntity();
            if (bean.getId() != 0) {
                entity.setId(bean.getId());
            }
            entity.setPassword(bean.getPassword());
            entity.setType(bean.getType());
            entity.setUsername(bean.getUsername());
            return entity;
        }

        public static Account toBean(AccountEntity entity) {
            Account bean = new Account();
            bean.setId(entity.getId());
            bean.setUsername(entity.getUsername());
            bean.setPassword(entity.getPassword());
            bean.setType(entity.getType());
            return bean;
        }
    }
}
