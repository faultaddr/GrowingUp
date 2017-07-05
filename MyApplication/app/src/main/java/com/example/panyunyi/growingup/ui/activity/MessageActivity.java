package com.example.panyunyi.growingup.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.adapter.MessageAdapter;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.ui.dialog.ContentDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 用来管理预约情况
* */
public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.message_phone)
    public ImageView messagePhone;
    @BindView(R.id.message_recycler)
    public RecyclerView messageRecycler;
    @BindView(R.id.message_quit)
    public ImageView messageQuit;

    private Context context;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
context=this;
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        Bundle bundle=new Bundle();
        ArrayList<String>arrayList=new ArrayList<>();
        arrayList.add("2017/06/09 12:00");
        arrayList.add("2017/06/10 12:00");
        arrayList.add("2017/06/11 12:00");
        bundle.putStringArrayList("time",arrayList);
        bundle.putString("status","1");
        messageAdapter=new MessageAdapter(this,bundle);
        LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(this);
        linearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        messageRecycler.setLayoutManager(linearLayoutManagerHorizontal);
        messageRecycler.setAdapter(messageAdapter);


        messagePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                Uri data = Uri.parse("tel:" + "17888823635");

                intent.setData(data);
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MessageActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            100);
                }else{
                }
                startActivity(intent);
            }
        });

        messageQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
