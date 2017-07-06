package com.example.panyunyi.growingup.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.manager.LoginSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    @BindView(R.id.userInfo_id)
    TextView userInfoId;
    @BindView(R.id.userInfo_class)
    TextView userInfoClass;
    @BindView(R.id.userInfo_name)
    TextView userInfoName;
    @BindView(R.id.userInfo_wechat)
    TextView userInfoWeChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        userInfoId.setText(LoginSession.getLoginSession().getLoginedUser().getUserId());
        userInfoName.setText(LoginSession.getLoginSession().getLoginedUser().getUserName());
        userInfoClass.setText(LoginSession.getLoginSession().getLoginedUser().getUserClass());
        userInfoWeChat.setText(LoginSession.getLoginSession().getLoginedUser().getUserWechat());

    }
}
