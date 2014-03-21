package com.android.BookProject2;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by aschey on 1/12/14.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        Calendar calendar = Calendar.getInstance();
        mDate = DateFormat.format("E MMM dd kk:mm:ss zz yyyy", calendar).toString();
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
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
