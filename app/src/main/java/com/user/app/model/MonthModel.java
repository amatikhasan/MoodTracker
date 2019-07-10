package com.user.app.model;

public class MonthModel {
    private String month;
    private String monthShort;
    private int bgColor;
    private int entryCount;

    public MonthModel(String month, String monthShort, int bgColor, int entryCount) {
        this.month = month;
        this.monthShort = monthShort;
        this.bgColor = bgColor;
        this.entryCount=entryCount;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String getMonth() {
        return month;
    }
    public String getMonthShort() {
        return monthShort;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(int entryCount) {
        this.entryCount = entryCount;
    }
}