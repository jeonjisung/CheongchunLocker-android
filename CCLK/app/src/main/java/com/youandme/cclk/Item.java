package com.youandme.cclk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

    private String user;
    private String memocontents;
    private String date;

    public String getUser()
    {
        return user;
    }

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getMemocontents()
    {
        return memocontents;
    }

    public void setMemocontents(String memocontents)
    {
        this.memocontents = memocontents;
    }

    public String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        String getTime = mFormat.format(mDate);

        return getTime;
    }
}
