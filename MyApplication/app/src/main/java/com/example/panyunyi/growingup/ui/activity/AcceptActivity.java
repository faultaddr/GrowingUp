package com.example.panyunyi.growingup.ui.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.remote.GFeedbackEntity;
import com.example.panyunyi.growingup.manager.LoginSession;
import com.example.panyunyi.growingup.service.MsgService;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AcceptActivity extends AppCompatActivity {
    @BindView(R.id.accept_activity_title)
    MaterialEditText title;
    @BindView(R.id.accept_edit_text)
    MaterialEditText content;
    @BindView(R.id.accept_submit)
    ImageView submit;
    @BindView(R.id.container)
    CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText=title.getText().toString();
                String contentText=content.getText().toString();
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=format.format(date).toString();
                GFeedbackEntity g=new GFeedbackEntity();
                g.setUserId(LoginSession.getLoginSession().getLoginedUser().getUserId());
                g.setFeedbackTime(time);
                g.setFeedbackTitle(titleText);
                g.setFeedbackContent(contentText);
                ExecutorService exs = Executors.newCachedThreadPool();
                MsgService.Post post=new MsgService.Post(Constant.API_URL+"/addFeedBack", JSON.toJSONString(g));
                Future<Object> future = exs.submit(post);//使用线程池对象执行任务并获取返回对象
                String result = null;
                try {
                    result=future.get().toString();
                    Log.i("result",result);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(result.equals("true")){
                    Snackbar.make(container,"提交成功",Snackbar.LENGTH_LONG).show();

                }else{
                    Snackbar.make(container,"提交失败请检查网络或必填项",Snackbar.LENGTH_LONG).show();
                }
            }

        });

    }
}
