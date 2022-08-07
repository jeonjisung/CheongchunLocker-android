package com.youandme.cclk;

public class Item {

    private String user;
    private String memocontents;
    private String date;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser()
    {
        return user;
    }


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
}
