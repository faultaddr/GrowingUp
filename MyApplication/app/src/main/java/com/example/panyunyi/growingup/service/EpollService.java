package com.example.panyunyi.growingup.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.entity.remote.GTeacherEntity;
import com.example.panyunyi.growingup.entity.remote.GTimeEntity;
import com.example.panyunyi.growingup.manager.OrderImpl;
import com.example.panyunyi.growingup.manager.OrderInfoSession;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EpollService extends Service {
    private static String sId;
    private static String teacherId;
    public EpollService(){

    }

    private MyThread myThread;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub  
        return new EpollBinder();
    }
    public class EpollBinder extends Binder {
        /**
         * 获取当前Service的实例
         * @return
         */
        public EpollService getService(){
            return EpollService.this;
        }
    }
    @Override
    public void onCreate() {

        this.myThread = new MyThread();
        this.myThread.start();

        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Log.i(">>>","epolling");
                try {
                    // 每个10秒向服务器发送一次请求
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //下面写请求服务器的代码
                ExecutorService exs = Executors.newCachedThreadPool();


                MsgService.getMethod ct = new MsgService.getMethod(Constant.API_URL + "/showOrderDetail?id=" + sId);//实例化任务对象
                //大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
                Future<Object> future = exs.submit(ct);//使用线程池对象执行任务并获取返回对象
                List<GTimeEntity> list = null;
                try {
                    String result = future.get().toString();
                    Log.i("result", result);
                    list = JSON.parseArray(result, GTimeEntity.class);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                OrderImpl order = new OrderImpl(list.get(0),getApplicationContext());
                order.setOrder();
            }
        }
    }

    public void setArgs(int id,String tid){
        sId=Integer.toString(id);
        teacherId=tid;
    }
}