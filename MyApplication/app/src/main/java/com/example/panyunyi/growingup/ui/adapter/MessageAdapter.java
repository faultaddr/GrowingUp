package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by panyu on 2017/6/23.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private String status;
    private ArrayList<String> timeList=new ArrayList<>();
    private String []statusContent=new String[]{"提交申请","老师确认","预约成功"};
    public MessageAdapter(Context context, Bundle bundle){
        mContext=context;
        status=bundle.getString("status");
        timeList=bundle.getStringArrayList("time");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.activity_trace_item,parent,false);
        RecyclerView.ViewHolder viewHolder=new MyViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("message->position->",position+"");
        if(holder instanceof MyViewHolder){
            if(Integer.parseInt(status)!=position)
                ((MyViewHolder)holder).dotView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.timelline_dot_normal));

            ((MyViewHolder)holder).statusView.setText(statusContent[position]);
            ((MyViewHolder)holder).timeView.setText(timeList.get(position));
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) ((MyViewHolder)holder).main.getLayoutParams();
            params.width=width/3;
            ((MyViewHolder)holder).main.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dotView;
        TextView timeView;
        TextView statusView;
        LinearLayout main;
        public MyViewHolder(View itemView) {
            super(itemView);
            //TODO 有时间把这个timeline的res 变量名改一改
            dotView=(TextView)itemView.findViewById(R.id.tvDot);
            timeView=(TextView)itemView.findViewById(R.id.tvAcceptTime);
            statusView=(TextView)itemView.findViewById(R.id.tvAcceptStation);
            main=(LinearLayout)itemView.findViewById(R.id.rlTimeline);

        }
    }
}
