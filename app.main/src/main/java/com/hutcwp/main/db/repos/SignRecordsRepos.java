package com.hutcwp.main.db.repos;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hutcwp.main.db.AppDatabase;
import com.hutcwp.main.db.dao.SignRecordDao;
import com.hutcwp.main.db.entitys.SignRecordEntity;
import com.hutcwp.main.model.SignRecord;
import com.hutcwp.main.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SignRecordsRepos {

    private static final String TAG = "SignRecordsRepos";

    private static SignRecordsRepos mInstance = new SignRecordsRepos();

    public static SignRecordsRepos getInstance() {
        return mInstance;
    }

    @SuppressLint("CheckResult")
    public void signRecord() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        SignRecordEntity entity = getRecordDao().queryCurRecord(DateUtil.getNowYYYYMMDD());
                        if (entity == null) {
                            SignRecordEntity newEntity = new SignRecordEntity();
                            newEntity.setDate(DateUtil.getNowYYYYMMDD());
                            newEntity.setStartTime(DateUtil.getNowHHmmss());
                            newEntity.setEndTime(DateUtil.getNowHHmmss());
                            Log.d(TAG, "insert " + newEntity.toString());
                            getRecordDao().insert(newEntity);
                        } else if (DateUtil.isInTime("01:00:00", "04:00:00")) {
                            entity.setEndTime(DateUtil.getNowHHmmss());
                            Log.d(TAG, "update " + entity.toString());
                            getRecordDao().update(entity);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.d(TAG, "error : " + throwable);
                    }
                });
    }

    public List<SignRecord> getSignRecords() {
        List<SignRecordEntity> entries = getRecordDao().getAllRecords();
        List<SignRecord> records = new ArrayList<>();
        for (SignRecordEntity entity : entries) {
            records.add(Transform.RecordEntity2Record(entity));
        }
        Log.d("TAG", "size = " + entries.size());
        return records;
    }

    private SignRecordDao getRecordDao() {
        return AppDatabase.getInstance().getRecordDao();
    }


    public static class Transform {

        public static SignRecord RecordEntity2Record(SignRecordEntity entity) {
            SignRecord record = new SignRecord();
            record.setDate(entity.getDate());
            record.setEndTime(entity.getEndTime());
            record.setStartTime(entity.getStartTime());
            return record;
        }

        public static SignRecordEntity Record2RecordEntity(SignRecord record) {
            SignRecordEntity entity = new SignRecordEntity();
            entity.setDate(record.getDate());
            entity.setEndTime(record.getEndTime());
            entity.setStartTime(record.getStartTime());
            return entity;
        }
    }

}
