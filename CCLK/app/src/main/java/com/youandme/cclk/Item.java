package com.youandme.cclk;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

    private String user;
    private String memocontents;
    private String date;
    private String email;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getEmail(){return this.email;}

    public void setEmail(String email){this.email = email;}

    public String getMemocontents()
    {
        return memocontents;
    }

    public void setMemocontents(String memocontents)
    {
        this.memocontents = memocontents;
    }

}
