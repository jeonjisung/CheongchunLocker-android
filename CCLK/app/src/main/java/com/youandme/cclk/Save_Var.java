package com.youandme.cclk;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.BaseRequestOptions;

import java.util.ArrayList;
import java.util.List;

public class Save_Var {

    String[] string = new String[9];

    private static Save_Var instance = null;

    public static synchronized Save_Var getInstance(){
        if(null == instance){
            instance = new Save_Var();
        }
        return instance;
    }
}