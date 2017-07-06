package com.example.panyunyi.growingup.service;

import android.app.Service;
import android.content.Intent;  
import android.os.Binder;  
import android.os.IBinder;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.entity.local.KnowledgeNewsList;
import com.example.panyunyi.growingup.entity.local.TeacherList;
import com.example.panyunyi.growingup.entity.remote.GArticleEntity;
import com.example.panyunyi.growingup.manager.LoginImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.panyunyi.growingup.Constant.JSONs;

public class MsgService extends Service {

    private List<KnowledgeNewsList>knowledgeNewsLists=new ArrayList<>();

    private List<TeacherList>teacherLists=new ArrayList<>();


    OkHttpClient client = new OkHttpClient();


    public List<GArticleEntity> getKnowledge() {

        ExecutorService exs = Executors.newCachedThreadPool();


        getArticle ct = new getArticle(Constant.API_URL+"/showArticle");//实例化任务对象
        //大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
        Future<Object> future = exs.submit(ct);//使用线程池对象执行任务并获取返回对象
        List<GArticleEntity>list = null;
        try {
            String result=future.get().toString();
            Log.i("result",result);
            list= JSON.parseArray(result,GArticleEntity.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }
/*
    public List<TeacherList>getTeacher(){

    }*/

    /** 
     * 返回一个Binder对象 
     */  
    @Override  
    public IBinder onBind(Intent intent) {  
        return new MsgBinder();  
    }



    public class MsgBinder extends Binder{  
        /** 
         * 获取当前Service的实例 
         * @return 
         */  
        public MsgService getService(){  
            return MsgService.this;  
        }  
    }
    class getArticle implements Callable<Object> {
        String url;

        public getArticle(String url) {
            this.url = url;
        }

        String getURL() throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        public Object call() throws Exception {
            return getURL();
        }
    }
}