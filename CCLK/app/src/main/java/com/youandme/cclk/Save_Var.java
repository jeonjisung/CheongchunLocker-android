package com.youandme.cclk;


public class Save_Var {

    String now = "";
    String memo_now = "";
    int pos = 0;
    Class aClass;

    private static Save_Var instance = null;

    public static synchronized Save_Var getInstance(){
        if(null == instance){
            instance = new Save_Var();
        }
        return instance;
    }
}