package com.example.panyunyi.growingup.manager;

import android.support.v7.widget.LinearLayoutCompat;

import com.example.panyunyi.growingup.entity.remote.User;

/**
 * Created by panyu on 2017/6/9.
 */

public class LoginImpl implements LoginManager {
    private User muser;
    private LoginImpl(User user){

        muser=user;

    }
    @Override
    public void login(){
        User loginedUser =new User();
        loginedUser.userId=muser.userId;
        loginedUser.userAlipay=muser.userAlipay;
        loginedUser.userClass=muser.userClass;
        loginedUser.userGrade=muser.userGrade;
        loginedUser.userInstitution=muser.userInstitution;
        loginedUser.userName=muser.userName;
        loginedUser.userPassword=muser.userPassword;
        loginedUser.userRole=muser.userRole;
        loginedUser.userTelephoneNumber=muser.userTelephoneNumber;
        loginedUser.userWechat=muser.userWechat;
        //TODO 这里做访问网络的相关操作，进行登录验证，如果验证失败，吧上述属性都换成 游客 如果成功，存进LoginSession  这样可以随时存取。


        LoginSession.sLoginSession.setsLoginSession(loginedUser);
    }
}
