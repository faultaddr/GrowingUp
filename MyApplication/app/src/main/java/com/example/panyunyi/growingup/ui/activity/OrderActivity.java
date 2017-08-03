package com.example.panyunyi.growingup.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.remote.GTimeEntity;
import com.example.panyunyi.growingup.service.MsgService;
import com.example.panyunyi.growingup.ui.adapter.TimeSpinnerAdapter;
import com.example.panyunyi.growingup.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity {
    private static String tableId;
    @BindView(R.id.order_time_spinner)
    Spinner timeSpinner;
    @BindView(R.id.send_order)
    ImageView sendOrder;
    ArrayList<GTimeEntity> list = null;
    private String teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            teacherId = getIntent().getStringExtra("teacherId");
        }
        //TODO-LIST: 这里要做网络请求来获取提交预约的信息和数据

        initData("/showTeacherTime?teacherId=" + teacherId);
        initView();
    }

    private void initView() {
        timeSpinner.setAdapter(new TimeSpinnerAdapter(OrderActivity.this, list));

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableId = view.getTag().toString();
                Log.i(">>>", tableId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initData("/addOrder?id=" + tableId))
                    Snackbar.make(v, "提交预约成功，请前往个人中心查看进度", Snackbar.LENGTH_SHORT).show();

                else {
                    Snackbar.make(v, "预约失败请重新预约或检查网络", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean initData(@Nullable String url) {

        ExecutorService exs = Executors.newCachedThreadPool();
        MsgService.getMethod ct = null;
        if (url != null) {

            ct = new MsgService.getMethod(Constant.API_URL + url);//添加预定
        }

        //大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
        Future<Object> future = exs.submit(ct);//使用线程池对象执行任务并获取返回对象

        try {
            String result = future.get().toString();
            if (result.equals("true")) {
                return true;
            }
            if (result.equals("false")) return false;
            Log.i("result", result);

            list = (ArrayList<GTimeEntity>) JSON.parseArray(result, GTimeEntity.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }
}
