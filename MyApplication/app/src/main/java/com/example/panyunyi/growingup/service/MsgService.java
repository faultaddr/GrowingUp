package com.example.panyunyi.growingup.service;

import android.app.Service;
import android.content.Intent;  
import android.os.Binder;  
import android.os.IBinder;

import com.example.panyunyi.growingup.entity.local.KnowledgeNewsList;
import com.example.panyunyi.growingup.entity.local.TeacherList;

import java.util.ArrayList;
import java.util.List;

public class MsgService extends Service {
    private List<String>title=new ArrayList<>();
    private List<String>content=new ArrayList<>();
    private List<KnowledgeNewsList>knowledgeNewsLists=new ArrayList<>();
    private List<String>url=new ArrayList<>();
    private List<String>page=new ArrayList<>();
    private List<TeacherList>teacherLists=new ArrayList<>();
    private boolean TAG_Knowledge=false;
    private boolean TAG_Teacher=false;

    /**
     * 增加get()方法，供Activity调用 
     * @return 下载进度 
     */
    public List<KnowledgeNewsList> getKnowledge() {

        while(TAG_Knowledge==false){
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

    public List<TeacherList>getTeacher(){
        while(TAG_Teacher==false){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<page.size();i++){
            TeacherList teacherList=new TeacherList();
            teacherList.setUrl(url.get(i));
            teacherList.setPage(Integer.parseInt(page.get(i)));
            teacherLists.add(teacherList);
        }
        return teacherLists;
    }
    /** 
     * 获取后台数据
     */  
    public void startGetInfo(){
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                for(int i=0;i<10;i++){
                    title.add("标题");
                    content.add("内容");

                }
                TAG_Knowledge=true;
            }  
        }).start();  
    }

    public void startDownLoad(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    page.add(""+i);
                    url.add("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&step_word=&hs=0&pn=15&spn=0&di=122012152460&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=undefined&cs=521468216%2C203255165&os=1228933433%2C1957590975&simid=4200592012%2C360737047&adpicid=0&lpn=0&ln=1992&fr=&fmq=1492013318359_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fv1.qzone.cc%2Fskin%2F201511%2F29%2F09%2F55%2F565a5b0d64410783.jpg!600x600.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bqz5gj_z%26e3BvvAzdH3FfhtgAzdH3Fhwp5g2AzdH3Fcmc8cd_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0");
                }
                TAG_Teacher=true;
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