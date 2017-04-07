package com.example.panyunyi.growingup;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.panyunyi.growingup.ui.adapter.KnowledgeNewsAdapter;
import com.example.panyunyi.growingup.ui.adapter.TeacherListAdapter;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //LinearLayout 声明

    @BindView(R.id.thinking)
    public LinearLayout thinking;//有所思
    @BindView(R.id.inspire)
    public LinearLayout inspire;//有所悟
    @BindView(R.id.accept)
    public LinearLayout accept;//有所得
    @BindView(R.id.youth)
    public LinearLayout youth;//青春相伴
    @BindView(R.id.expert)
    public LinearLayout expert;//专家咨询
    @BindView(R.id.communist)
    public LinearLayout communism;//党员
    @BindView(R.id.mine)
    public LinearLayout mine;//我

    //widget 声明

    @BindView(R.id.haveAChange)
    public TextView haveAChange;//换一换
    @BindView(R.id.showAll)
    public TextView showAll;//查看全部
    @BindView(R.id.teachers_list)
    public RecyclerView teacherList;
    @BindView(R.id.knowledge_news)
    public RecyclerView knowledgeNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListener();//初始化监听器
        initAdapter();//初始化适配器

    }

    private void initAdapter() {
        teacherList.setAdapter(new TeacherListAdapter(this,));
        knowledgeNews.setAdapter(new KnowledgeNewsAdapter());
    }

    private void initListener() {

        thinking.setOnClickListener(this);
        inspire.setOnClickListener(this);
        accept.setOnClickListener(this);
        youth.setOnClickListener(this);
        expert.setOnClickListener(this);
        communism.setOnClickListener(this);
        mine.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
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
}
