package com.example.panyunyi.growingup.ui.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.remote.GTeacherEntity;
import com.example.panyunyi.growingup.entity.remote.GTimeEntity;
import com.example.panyunyi.growingup.manager.OrderInfoSession;
import com.example.panyunyi.growingup.service.EpollService;
import com.example.panyunyi.growingup.service.MsgService;
import com.example.panyunyi.growingup.ui.adapter.MessageAdapter;
import com.example.panyunyi.growingup.util.ACache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    @BindView(R.id.order_teacher)
    public TextView orderTeacher;
    private Context context;
    private MessageAdapter messageAdapter;
    private EpollService epollService = new EpollService();
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            epollService = ((EpollService.EpollBinder) service).getService();
            if (OrderInfoSession.getOrderInfoSession().getOrderedSession() != null) {
                try {
                    epollService.setArgs(OrderInfoSession.getOrderInfoSession().getOrderedSession().getId(), OrderInfoSession.getOrderInfoSession().getOrderedSession().getTeacherId());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "暂无相关预定", Toast.LENGTH_LONG).show();
                }
            } else {
                try {
                    ACache aCache = ACache.get(getApplicationContext(), "session");
                    GTimeEntity g = ((GTimeEntity) aCache.getAsObject("sessionDetail"));
                    epollService.setArgs(g.getId(), g.getTeacherId());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "暂无相关预定", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;


        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
/*        Intent intent = new Intent(this, EpollService.class);
        try {
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Bundle bundle = new Bundle();
        boolean isNull = true;
        ArrayList<String> arrayList = new ArrayList<>();
        if (OrderInfoSession.getOrderInfoSession().getOrderedSession() != null) {
            try {
                isNull = false;
                arrayList.add(OrderInfoSession.getOrderInfoSession().getOrderedSession().getTimeDetail());
                arrayList.add(OrderInfoSession.getOrderInfoSession().getOrderedSession().getTimeDetail());
                arrayList.add(OrderInfoSession.getOrderInfoSession().getOrderedSession().getTimeDetail());
                bundle.putStringArrayList("time", arrayList);
                bundle.putString("status", OrderInfoSession.getOrderInfoSession().getOrderedSession().getTimeStatus());
                GTimeEntity timeEntity = OrderInfoSession.getOrderInfoSession().getOrderedSession();

                MsgService.getMethod getMethod = new MsgService.getMethod(Constant.API_URL + "/showTeacher?teacherId=" + timeEntity.getTeacherId());
                ExecutorService exs = Executors.newCachedThreadPool();

                Future<Object> future = exs.submit(getMethod);//使用线程池对象执行任务并获取返回对象
                String result;
                try {
                    result = future.get().toString();//当调用了future的get方法获取返回的值得时候
                    //如果线程没有计算完成，那么这里就会一直阻塞等待线程执行完成拿到返回值
                    Log.i(">>>result", result);
                    List<GTeacherEntity> list = JSON.parseArray(result, GTeacherEntity.class);
                    orderTeacher.setText(list.get(0).getTeacherName() + " 老师" + "   " + timeEntity.getTimeDetail() + "  " + list.get(0).getTeacherMobileNumber());
                    ACache aCache = ACache.get(getApplicationContext(), "session");
                    aCache.put("teacherInfo", (Serializable) list.get(0));

                    exs.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                ACache aCache = ACache.get(getApplicationContext(), "session");
                isNull = false;
                GTimeEntity g = (GTimeEntity) aCache.getAsObject("sessionDetail");
                GTeacherEntity gTeacherEntity = (GTeacherEntity) aCache.getAsObject("teacherInfo");
                arrayList.add(g.getTimeDetail());
                arrayList.add(g.getTimeDetail());
                arrayList.add(g.getTimeDetail());
                bundle.putStringArrayList("time", arrayList);
                bundle.putString("status", g.getTimeStatus());
                orderTeacher.setText(gTeacherEntity.getTeacherName() + " 老师" + "   " + g.getTimeDetail() + "   " + gTeacherEntity.getTeacherMobileNumber());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplication(), "暂无相关预定", Toast.LENGTH_LONG).show();
            }
        }


            messageAdapter = new MessageAdapter(this, bundle);
        LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(this);
        linearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        messageRecycler.setLayoutManager(linearLayoutManagerHorizontal);
        messageRecycler.setAdapter(messageAdapter);


        messagePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                Uri data = Uri.parse("tel:" + orderTeacher.getText().subSequence(orderTeacher.getText().length() - 10, orderTeacher.length()));

                intent.setData(data);
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MessageActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            100);
                } else {
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

    @Override
    protected void onStop() {

        super.onStop();
    }
}
