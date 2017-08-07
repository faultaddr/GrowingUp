package com.example.panyunyi.growingup.app;

import com.example.panyunyi.growingup.Constant;

/**
 * Created by panyunyi on 2017/4/7.
 */

public class Application extends android.app.Application{


    @Override
    public void onCreate() {
        Constant constant=new Constant(Application.this);
        constant.setConstants();
        super.onCreate();
    }
}
