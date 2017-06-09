package com.example.panyunyi.growingup.ui.activity;

import android.os.Bundle;

import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.R;

public class OrderActivity extends BaseActivity {
    private  int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        if(getIntent().getExtras()!=null){
            pos=getIntent().getIntExtra("className",-1);
        }
        //TODO-LIST: 这里要做网络请求来获取提交预约的信息和数据
    }
}
