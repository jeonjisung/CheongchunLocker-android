package com.youandme.cclk;


public class Save_Var {

    private static Save_Var instance = null;

    public static synchronized Save_Var getInstance(){
        if(null == instance){
            instance = new Save_Var();
        }
        return instance;
    }
}