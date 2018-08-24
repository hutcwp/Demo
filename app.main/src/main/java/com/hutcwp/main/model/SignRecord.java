package com.hutcwp.main.model;

/**
 * 打卡记录实体类
 */
public class SignRecord {

    private String date;
    private String startTime;
    private String endTime;

    public SignRecord(){

    }

    public SignRecord(String date, String startTime, String endTime, String status) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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

}
