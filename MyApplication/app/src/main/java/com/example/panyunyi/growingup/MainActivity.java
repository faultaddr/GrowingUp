package com.example.panyunyi.growingup;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.entity.remote.GArticleEntity;
import com.example.panyunyi.growingup.entity.remote.GTeacherEntity;
import com.example.panyunyi.growingup.manager.LoginSession;
import com.example.panyunyi.growingup.service.MsgService;
import com.example.panyunyi.growingup.ui.activity.AcceptActivity;
import com.example.panyunyi.growingup.ui.activity.InspireActivity;
import com.example.panyunyi.growingup.ui.activity.MineActivity;
import com.example.panyunyi.growingup.ui.activity.OrderActivity;
import com.example.panyunyi.growingup.ui.activity.PartyActivity;
import com.example.panyunyi.growingup.ui.activity.ProfileActivity;
import com.example.panyunyi.growingup.ui.activity.ThinkingActivity;
import com.example.panyunyi.growingup.ui.adapter.KnowledgeNewsAdapter;
import com.example.panyunyi.growingup.ui.adapter.MainViewPagerAdapter;
import com.example.panyunyi.growingup.ui.adapter.TeacherListAdapter;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.ui.custom.ZoomOutPageTransformer;
import com.example.panyunyi.growingup.ui.dialog.WebViewDialog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    public static final MediaType JSONs
            = MediaType.parse("application/json; charset=utf-8");
    public static List list = new ArrayList();
    public static List<GTeacherEntity> teacherListInfo = new ArrayList<>();
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

    //widget 声明
    @BindView(R.id.expert)
    LinearLayout expert;//专家咨询
    @BindView(R.id.communist)
    LinearLayout communism;//党员
    @BindView(R.id.mine)
    LinearLayout mine;//我
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
    int count = 0;
    ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> beeperHandle =
            scheduler.scheduleAtFixedRate(new ViewTask(), 4, 3, TimeUnit.SECONDS);
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    boolean TAG_MOTION = false;
    //网络请求相关
    String result;
    OkHttpClient client = new OkHttpClient();
    //int []id=new int[]{R.drawable.main_activity_list_1, R.drawable.main_activity_list_2, R.drawable.main_activity_list_3, R.drawable.main_activity_list_4};
    ArrayList<ImageView> imageViews = new ArrayList<>();
    //轮播图片 URL 数组
    private Context mContext;
    private MsgService msgService=new MsgService();
    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(">>onServiceConnnected", "  failed");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            Log.i(">>onServiceConnnected", "  start");
            msgService = ((MsgService.MsgBinder) service).getService();
        }
    };
    private boolean TAG_DATA;
    //adapter 声明
    private KnowledgeNewsAdapter knowledgeNewsAdapter;
    private TeacherListAdapter teacherAdapter;
    public Handler mHandler = new Handler() {


        // 子类必须重写此方法，接受数据
        @Override
        public void handleMessage(Message msg) {
            ;
            switch (msg.what) {
                case 1:
                    Log.i(">>count", count + "");
                    count = viewPager.getCurrentItem();
                    if (TAG_MOTION == false) count++;
                    else {
                        count--;
                        TAG_MOTION = false;
                    }

                    viewPager.setCurrentItem(count);
                    viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                    break;
                case 2:
                    viewPager.setAdapter(new MainViewPagerAdapter(mContext, imageViews));
                    break;
                case 3:
                    knowledgeNewsAdapter.notifyDataSetChanged();

                    break;
                case 4:
                    teacherAdapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * Logger init and config
        *
        * */
        Logger.addLogAdapter(new AndroidLogAdapter());
        Logger.d("message");
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("My custom tag")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        Logger.addLogAdapter(new DiskLogAdapter());

        Logger.clearLogAdapters();


        //Log.i(">>>login", LoginSession.getLoginSession().getLoginedUser().getUserId() + "");
        mContext = this;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        } else {
        }

        setContentView(R.layout.activity_main);

        //5.0以上的service要显性声明
        Intent intent = new Intent(this, MsgService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        ButterKnife.bind(this);
        initData();


        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        initAdapter();//初始化适配器
        //每隔2秒钟切换一张图片


    }

    private void initData() {
        for (int i = 0; i < 4; i++) {
/*                    InputStream is = MainActivity.this.getResources().openRawResource(id[i]);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 10; //width，hight设为原来的十分之一
                    Bitmap btp = BitmapFactory.decodeStream(is, null, options);*/
            String urlList[] = new String[]{"http://i2.bvimg.com/598503/4c60dfb9171f5576.jpg", "http://i2.bvimg.com/598503/1d8d270306c1f3a7.jpg", "http://i2.bvimg.com/598503/050850a730e449ff.jpg", "http://i2.bvimg.com/598503/e4fb964755e5df12.jpg"};
            ImageView imageView = new ImageView(MainActivity.this);
/*                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(btp);*/
            Picasso.with(this).load(urlList[i]).resize(getWindow().getDecorView().getWidth(), 200)
                    .into(imageView);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);

        }
        Message msg = new Message();
        msg.what = 2;
        mHandler.sendMessage(msg);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initAdapter() {
        //初始化 RecycleViewAdapter：
        teacherList.setHasFixedSize(true);
        knowledgeNews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerTeacher = new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManagerKnowledge = new LinearLayoutManager(this);
        teacherList.setLayoutManager(layoutManagerTeacher);
        knowledgeNews.setLayoutManager(layoutManagerKnowledge);
        //TODO: 获取老师信息的后端API还没有做好
        GTeacherEntity gTeacherEntity = new GTeacherEntity();
        gTeacherEntity.setTeacherId("1");
        gTeacherEntity.setTeacherMajor("sb");
        gTeacherEntity.setTeacherName("sb");
        teacherListInfo.add(gTeacherEntity);
        teacherAdapter = new TeacherListAdapter(this, teacherListInfo);

        teacherAdapter.setOnItemClickListener(new TeacherListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Intent intent = new Intent();
                intent.putExtra("teacherId", teacherListInfo.get(pos).getTeacherId());
                intent.setClass(mContext, OrderActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int pos) {

            }
        });
        teacherList.setAdapter(teacherAdapter);
        GArticleEntity g = new GArticleEntity();
        g.setArticleContent("正在加载请稍后");
        list.add(g);
        knowledgeNewsAdapter = new KnowledgeNewsAdapter(this, list);
        new Thread() {
            public void run() {
                list.clear();
                list.addAll(msgService.getKnowledge());
                Message msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        }.start();
        new Thread() {
            public void run() {
                teacherListInfo.clear();
                teacherListInfo.addAll(msgService.getTeacherInfo());
                Message msg = new Message();
                msg.what = 4;
                mHandler.sendMessage(msg);

            }
        }.start();
        knowledgeNewsAdapter.setOnItemClickListener(new KnowledgeNewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                //TODO 这里是内建一个webview做查看 到时候还可以嵌入广告
/*                View rootView = View.inflate(mContext,R.layout.fragment_thinking,null);
                Log.i(">>>",pos+"");
                WebView webView = (WebView) rootView.findViewById(R.id.thinking_activity_webview);*/
/*                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");*/
                Log.i(">>>", pos + "");

                WebViewDialog dialog = WebViewDialog.newInstance(Constant.API_URL + "/showArticle?num=" + (pos + 1));

                dialog.show(getSupportFragmentManager(), "dialog");
/*                intent.setData(content_url);
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                //TODO 这里是做预览
            }
        });
        knowledgeNews.setAdapter(knowledgeNewsAdapter);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    scheduler.shutdown();
                    x1 = motionEvent.getX();
                    y1 = motionEvent.getY();
                    return true;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();


                    if (x1 - x2 > 20) {

                    } else if (x2 - x1 > 20) {
                        TAG_MOTION = true;
                    }
                    scheduler = Executors.newSingleThreadScheduledExecutor();
                    ScheduledFuture<?> beeperHandle =
                            scheduler.scheduleAtFixedRate(new ViewTask(), 0, 4, TimeUnit.SECONDS);
                    return true;
                }


                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(">>onPageScrolled", "   Start");
                //beeperHandle.cancel(true);


            }

            @Override
            public void onPageSelected(int position) {
                Log.i(">>onPageSelected", "   Start");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(">>ScrollStateChanged", "   Start");

            }
        });
    }

    @OnClick({R.id.thinking, R.id.inspire, R.id.accept, R.id.showAll, R.id.youth, R.id.expert, R.id.communist, R.id.mine})
    void butterknifeOnItemClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
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
                intent.setClass(this, AcceptActivity.class);
                startActivity(intent);
                break;
            case R.id.showAll:
                /*
                * 显示全部
                * */
                try {
                    ExecutorService exs = Executors.newCachedThreadPool();
                    Post p = new Post(Constant.API_URL + "/showArticle", "aaa");
                    Future<Object> future = exs.submit(p);//使用线程池对象执行任务并获取返回对象
                    try {
                        result = future.get().toString();//当调用了future的get方法获取返回的值得时候
                        //如果线程没有计算完成，那么这里就会一直阻塞等待线程执行完成拿到返回值
                        Log.i(">>>result", result);
                        List<GArticleEntity> list = JSON.parseArray(result, GArticleEntity.class);
                        KnowledgeNewsAdapter knowledgeNewsAdapte = new KnowledgeNewsAdapter(this, list);

                        exs.shutdown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                intent.setClass(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.communist:
                /*
                * 党
                * */
                intent.setClass(this, PartyActivity.class);
                startActivity(intent);
                onPause();
                break;
            case R.id.mine:
                /*
                * 我的
                * */
                intent.setClass(this, MineActivity.class);
                startActivity(intent);
                onPause();

        }

    }

    private class ViewTask implements Runnable {
        @Override
        public void run() {

            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }

    public class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }

    class Post implements Callable {
        String url;
        String json;

        public Post(String url, String json) {
            this.url = url;

            this.json = json;

        }

        String post() throws IOException {
            RequestBody body = RequestBody.create(JSONs, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            Log.i(">>>", json);
            String r = response.body().string();
            return r;
        }

        @Override
        public Object call() throws Exception {
            return post();
        }
    }
}
