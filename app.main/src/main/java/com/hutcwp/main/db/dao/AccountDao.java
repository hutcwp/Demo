package com.hutcwp.main.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hutcwp.main.db.entitys.AccountEntity;

import java.util.List;

/**
 * Created by hutcwp on 2018/8/26 23:21
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/

@Dao
public interface  AccountDao {

    @Query("SELECT * FROM account")
    List<AccountEntity> getAllAccount();

    @Insert
    void insert(AccountEntity... account);

    @Update
    void update(AccountEntity... account);

    @Delete
    void delete(AccountEntity... account);


}
