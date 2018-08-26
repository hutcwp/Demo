package com.hutcwp.main.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.hutcwp.main.db.dao.AccountDao;
import com.hutcwp.main.db.dao.SignRecordDao;
import com.hutcwp.main.db.entitys.AccountEntity;
import com.hutcwp.main.db.entitys.SignRecordEntity;
import com.hutcwp.main.util.BasicConfig;

@Database(entities = {SignRecordEntity.class, AccountEntity.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "AppDatabase.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    public static synchronized AppDatabase getInstance( ) {
        if (instance == null) {
            instance = create(BasicConfig.getInstance().getAppContext());
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).build();
    }

    public abstract SignRecordDao getRecordDao();

    public abstract AccountDao getAccountDao();

}
