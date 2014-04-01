package com.android.BookProject2;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * Created by aschey on 1/12/14.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;
    private String mTime;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        Calendar calendar = Calendar.getInstance();
        mDate = DateFormat.format("MMM dd yyyy", calendar).toString();
        mTime = DateFormat.format("kk:mm", calendar).toString();
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
