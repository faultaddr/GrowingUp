package com.example.panyunyi.growingup;


import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.panyunyi.growingup.service.MsgService;
import com.example.panyunyi.growingup.ui.activity.InspireActivity;
import com.example.panyunyi.growingup.ui.activity.ThinkingActivity;
import com.example.panyunyi.growingup.ui.adapter.KnowledgeNewsAdapter;
import com.example.panyunyi.growingup.ui.adapter.MainViewPagerAdapter;
import com.example.panyunyi.growingup.ui.adapter.TeacherListAdapter;
import com.example.panyunyi.growingup.ui.custom.ZoomOutPageTransformer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    //LinearLayout 声明
    @BindView(R.id.main_viewpager)
    ViewPager viewPager;
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
    @BindView(R.id.main)
    PercentRelativeLayout main;



    private MsgService msgService=new MsgService();
    int count=0;
    ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> beeperHandle =
            scheduler.scheduleAtFixedRate(new ViewTask(), 4, 3, TimeUnit.SECONDS);

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    boolean TAG_MOTION=false;
    private boolean TAG_DATA;

    int []id=new int[]{R.drawable.main_activity_list_1, R.drawable.main_activity_list_2, R.drawable.main_activity_list_3, R.drawable.main_activity_list_4};
    ArrayList<ImageView>imageViews=new ArrayList<>();
    public Handler mHandler = new Handler() {


        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            ;
            switch (msg.what) {
                case 1:
                    Log.i(">>count", count + "");
                    count=viewPager.getCurrentItem();
                    if(TAG_MOTION==false) count++;
                    else{
                        count--;
                        TAG_MOTION=false;
                    }

                    viewPager.setCurrentItem(count);
                    viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                    break;
                case 2:
                    viewPager.setAdapter(new MainViewPagerAdapter(MainActivity.this,imageViews));
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //5.0以上的service要显性声明
        Intent intent = new Intent(this, MsgService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        ButterKnife.bind(this);
        initData();




        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        initAdapter();//初始化适配器
        //每隔2秒钟切换一张图片



    }

    private void initData() {
        new Thread() {
            public void run() {
                for (int i = 0; i < 4; i++) {
                    InputStream is = MainActivity.this.getResources().openRawResource(id[i]);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 10; //width，hight设为原来的十分之一
                    Bitmap btp = BitmapFactory.decodeStream(is, null, options);

                    ImageView imageView = new ImageView(MainActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(btp);
                    imageViews.add(imageView);

                }
                Message msg=new Message();
                msg.what=2;
                mHandler.sendMessage(msg);

            }
        }.start();
    }


    @Override
    protected void onStart(){
        super.onStart();

    }

    private void initAdapter() {
        //初始化 RecycleViewAdapter：
        msgService.startDownLoad();
        msgService.startGetInfo();
        teacherList.setHasFixedSize(true);
        knowledgeNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTeacher=new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManagerKnowledge=new LinearLayoutManager(this);
        teacherList.setLayoutManager(layoutManagerTeacher);
        knowledgeNews.setLayoutManager(layoutManagerKnowledge);

        teacherList.setAdapter(new TeacherListAdapter(this,msgService.getTeacher()));
        knowledgeNews.setAdapter(new KnowledgeNewsAdapter(this,msgService.getKnowledge()));





        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    scheduler.shutdown();
                    x1 = motionEvent.getX();
                    y1 = motionEvent.getY();
                    return true;
                }
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();


                    if(x1 - x2 > 20) {

                    } else if(x2 - x1 > 20) {
                        TAG_MOTION=true;
                    }
                    scheduler=Executors.newSingleThreadScheduledExecutor();
                    ScheduledFuture<?> beeperHandle =
                            scheduler.scheduleAtFixedRate(new ViewTask(), 0, 4, TimeUnit.SECONDS);
                    return true;
                }


            return false;}
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(">>onPageScrolled","   Start");
                //beeperHandle.cancel(true);


            }

            @Override
            public void onPageSelected(int position) {
                Log.i(">>onPageSelected","   Start");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(">>ScrollStateChanged","   Start");

            }
        });
    }
    @OnClick({R.id.thinking, R.id.inspire, R.id.accept,R.id.youth,R.id.expert,R.id.communist,R.id.mine})
    void butterknifeOnItemClick(View view){
        Intent intent=new Intent();
        switch (view.getId()){
            case R.id.thinking:
                /*
                * 有所思页面
                * */

                intent.setClass(this, ThinkingActivity.class);
                startActivity(intent);

                break;
            case R.id.inspire:
                /*
                * 有所感页面
                * */
                intent.setClass(this, InspireActivity.class);
                startActivity(intent);

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
        }
    };

    private class ViewTask implements Runnable {
        @Override
        public void run() {

            Message msg=new Message();
            msg.what=1;
            mHandler.sendMessage(msg);
        }
    }
}
