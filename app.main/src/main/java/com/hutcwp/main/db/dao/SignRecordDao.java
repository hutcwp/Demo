package com.hutcwp.main.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hutcwp.main.db.entitys.SignRecordEntity;

import java.util.List;

@Dao
public interface SignRecordDao {

    @Query("SELECT * FROM sign_records")
    List<SignRecordEntity> getAllRecords();

    @Insert
    void insert(SignRecordEntity... records);

    @Update
    void update(SignRecordEntity... records);

    @Delete
    void delete(SignRecordEntity... records);

    @Query("select * from sign_records where date = :date")
    SignRecordEntity queryCurRecord(String date);


}
