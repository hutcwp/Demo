package com.hutcwp.main.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hutcwp.main.db.dao.SignRecordDao;
import com.hutcwp.main.db.entitys.SignRecordEntity;

@Database(entities = {SignRecordEntity.class},version = 1,exportSchema = false)
public abstract class SignRecordDatabase extends RoomDatabase {

    private static final String DB_NAME = "SignRecordDatabase.db";
    private static volatile SignRecordDatabase instance;

    public static synchronized SignRecordDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static SignRecordDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                SignRecordDatabase.class,
                DB_NAME).build();
    }

    public abstract SignRecordDao getRecordDao();

}
