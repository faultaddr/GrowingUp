package com.example.panyunyi.growingup.manager;

import com.example.panyunyi.growingup.entity.remote.User;
import com.example.panyunyi.growingup.entity.remote.UserInfo;

/**
 * Created by panyu on 2017/6/9.
 */

public class LoginSession {
    static LoginSession sLoginSession =null;
    private UserInfo loginedUser;
    private LoginSession(){}
    public static LoginSession getLoginSession(){
        if(sLoginSession==null){
            sLoginSession=new LoginSession();
        }
        return sLoginSession;


    }
    public void exit(){
        sLoginSession=null;
    }
    void setsLoginSession(UserInfo user){
        loginedUser=user;
    }
    public UserInfo getLoginedUser(){
        return loginedUser;
    }
}
