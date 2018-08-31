package me.hutcwp.app.dynamic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.hutcwp.app.dynamic.util.LogUtil;


/**
 * Created by Administrator on 2018/1/19.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBUtil";

    public DBHelper(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
        LogUtil.D(TAG, "new dbutil");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstants.CREATE_TABLE_USER);
        db.execSQL(DBConstants.CREATE_TABLE_TOPIC);
        db.execSQL(DBConstants.CREATE_USER_COMMENT);
        LogUtil.D(TAG, "on create 创建表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_TOPIC);
        db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_COMMENT);
        LogUtil.D(TAG, "onUpgrade删除表");
        this.onCreate(db);
    }

}
