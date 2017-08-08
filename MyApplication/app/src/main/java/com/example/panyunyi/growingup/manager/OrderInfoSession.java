package com.example.panyunyi.growingup.manager;

import com.example.panyunyi.growingup.entity.remote.GTimeEntity;
import com.example.panyunyi.growingup.entity.remote.UserInfo;

/**
 * Created by panyu on 2017/8/4.
 */

public class OrderInfoSession {
    static OrderInfoSession orderInfoSession =null;
    private GTimeEntity gTimeEntity=null;
    private OrderInfoSession(){}
    public static OrderInfoSession getOrderInfoSession(){
        if(orderInfoSession==null){
            orderInfoSession=new OrderInfoSession();
        }
        return orderInfoSession;


    }
    public void exit(){
        orderInfoSession=null;
    }
    void setsOrderedSession(GTimeEntity timeEntity){
        gTimeEntity=timeEntity;
    }
    public GTimeEntity getOrderedSession(){
        return gTimeEntity;
    }
}
