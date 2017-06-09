package com.example.panyunyi.growingup.manager;

import com.example.panyunyi.growingup.entity.remote.User;

/**
 * Created by panyu on 2017/6/9.
 */

public class LoginSession {
    static LoginSession sLoginSession =null;
    private User loginedUser;
    private LoginSession(){}
    public static LoginSession getLoginSession(){
        if(sLoginSession==null){
            sLoginSession=new LoginSession();
        }
        return sLoginSession;


    }
    void setsLoginSession(User user){
        loginedUser=user;
    }
    public User getLoginedUser(){
        return loginedUser;
    }
}
