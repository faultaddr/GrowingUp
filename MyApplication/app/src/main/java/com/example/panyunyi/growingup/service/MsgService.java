package com.example.panyunyi.growingup.service;

import android.app.Service;
import android.content.Intent;  
import android.os.Binder;  
import android.os.IBinder;

import com.example.panyunyi.growingup.entity.local.KnowledgeNewsList;

import java.util.List;

public class MsgService extends Service {
    private List<String>title;
    private List<String>content;
    private List<KnowledgeNewsList>knowledgeNewsLists;
    private boolean TAG=false;
    /** 
     * 增加get()方法，供Activity调用 
     * @return 下载进度 
     */
    public List<KnowledgeNewsList> getKnowledge() {
        while(TAG==false){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<title.size();i++){
            KnowledgeNewsList knowledgeNewsList=new KnowledgeNewsList();
            knowledgeNewsList.setKnowledgeNewsContent(content.get(i));
            knowledgeNewsList.setKnowledgeNewsTitle(title.get(i));
            knowledgeNewsLists.add(knowledgeNewsList);

        }
        return knowledgeNewsLists;
    }


    /** 
     * 获取后台数据
     */  
    public void startDownLoad(){  
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                for(int i=0;i<10;i++){
                    title.add("标题");
                    content.add("内容");

                }
                TAG=true;
            }  
        }).start();  
    }  
  
  
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
  
}