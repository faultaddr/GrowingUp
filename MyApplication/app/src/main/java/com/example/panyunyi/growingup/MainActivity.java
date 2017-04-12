package com.example.panyunyi.growingup;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.panyunyi.growingup.service.MsgService;
import com.example.panyunyi.growingup.ui.adapter.KnowledgeNewsAdapter;
import com.example.panyunyi.growingup.ui.adapter.TeacherListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    //LinearLayout 声明

    @BindView(R.id.thinking)
    LinearLayout thinking;//有所思
    @BindView(R.id.inspire)
    LinearLayout inspire;//有所悟
    @BindView(R.id.accept)
    LinearLayout accept;//有所得
    @BindView(R.id.youth)
    LinearLayout youth;//青春相伴
    @BindView(R.id.expert)
    LinearLayout expert;//专家咨询
    @BindView(R.id.communist)
    LinearLayout communism;//党员
    @BindView(R.id.mine)
    LinearLayout mine;//我

    //widget 声明

    @BindView(R.id.haveAChange)
    TextView haveAChange;//换一换
    @BindView(R.id.showAll)
    TextView showAll;//查看全部
    @BindView(R.id.teachers_list)
    RecyclerView teacherList;
    @BindView(R.id.knowlwdge_news_list)
    RecyclerView knowledgeNews;

    private MsgService msgService=new MsgService();
    class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    initAdapter();
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //5.0以上的service要显性声明
        Intent intent=new Intent(this,MsgService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        ButterKnife.bind(this);

        //initAdapter();//初始化适配器

    }


    private void initAdapter() {
        msgService.startDownLoad();
        msgService.startGetInfo();
        teacherList.setAdapter(new TeacherListAdapter(this,msgService.getTeacher()));
        knowledgeNews.setAdapter(new KnowledgeNewsAdapter(this,msgService.getKnowledge()));

    }
    @OnClick({R.id.thinking, R.id.inspire, R.id.accept,R.id.youth,R.id.expert,R.id.communist,R.id.mine})
    void butterknifeOnItemClick(View view){
        switch (view.getId()){
            case R.id.thinking:
                /*
                * 有所思页面
                * */
                break;
            case R.id.inspire:
                /*
                * 有所感页面
                * */
                break;
            case R.id.accept:
                /*
                * 有所得页面
                * */
                break;
            case R.id.showAll:
                /*
                * 显示全部
                * */
                break;
            case R.id.haveAChange:
                /*
                * 换一换
                * */
                break;
            case R.id.youth:
                /*
                * 青春相伴
                * */
                break;
            case R.id.expert:
                /*
                * 专家咨询
                * */
                break;
            case R.id.communist:
                /*
                * 党
                * */
                break;
            case R.id.mine:
                /*
                * 我的
                * */
        }

    }

    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(">>onServiceConnnected","  failed");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            Log.i(">>onServiceConnnected","  start");
            msgService = ((MsgService.MsgBinder)service).getService();
            Handler handler=new Handler();
            Message msg=new Message();
            msg.what=1;
            handler.sendMessage(msg);
        }
    };
}
