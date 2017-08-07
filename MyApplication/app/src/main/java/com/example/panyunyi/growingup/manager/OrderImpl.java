package com.example.panyunyi.growingup.manager;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.serializer.SerializeWriter;
import com.example.panyunyi.growingup.entity.remote.GTimeEntity;
import com.example.panyunyi.growingup.util.ACache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by panyu on 2017/8/5.
 */

public class OrderImpl  implements OrderManager{
    GTimeEntity timeEntity;
    private static  String sId;
    Context context;
    public OrderImpl(GTimeEntity gTimeEntity,Context mContext){
        timeEntity=gTimeEntity;
        context=mContext;
    }



    @Override
    public boolean setOrder() {
        ACache aCache=ACache.get(context,"session");
        OrderInfoSession.getOrderInfoSession().setsOrderedSession(timeEntity);
/*        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(CacheDir + "cache.txt"));
            out.writeObject(user);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        aCache.put("sessionDetail", (Serializable) timeEntity);
        return false;
    }


}
