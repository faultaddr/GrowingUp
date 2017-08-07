package com.example.panyunyi.growingup;

import android.content.Context;

import com.example.panyunyi.growingup.util.MyProperUtil;

import okhttp3.MediaType;

/**
 * Created by panyu on 2017/7/16.
 */

public class Constant {
    public static Context mContext;
    public static String API_URL;
    public static final MediaType JSONs
            = MediaType.parse("application/json; charset=utf-8");

    public Constant(Context context) {
        mContext=context;
    }
    public void setConstants(){
        API_URL=MyProperUtil.getProperties(mContext).getProperty("base_url");
    }
}
