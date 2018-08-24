package com.hutcwp.main.db.entitys;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 打卡记录实体类
 */
@Entity(tableName = "sign_records")
public class SignRecordEntity {

    @PrimaryKey(autoGenerate = true) //主键是否自动增长，默认为false
    private int id;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "startTime")
    private String startTime;
    @ColumnInfo(name = "endTime")
    private String endTime;

    public SignRecordEntity(){

    }

    public SignRecordEntity(String date, String startTime, String endTime, String status) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "SignRecordEntity{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
