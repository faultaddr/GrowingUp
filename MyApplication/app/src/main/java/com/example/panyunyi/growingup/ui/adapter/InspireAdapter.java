package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
/**
 * Created by panyunyi on 2017/4/20.
 * MailBox cufer@foxmail.com
 */

public class InspireAdapter  extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener{
    private Context context;
    String []text=new String[]{"互动小组","小作业"};
    int count=0;
    public InspireAdapter(Context mcontext){
        context=mcontext;



    }

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    private InspireAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(InspireAdapter.OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.activity_inspire_recycleview_items,parent,false);
        MyViewHolder holder = new MyViewHolder(view);


        //给布局设置点击和长点击监听
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
                ((MyViewHolder) holder).textViewInfo.setText(text[position]);
                ((MyViewHolder) holder).simplifiedLogo.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    //点击事件回调
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v);
        }
    }
    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener!= null) {
            mOnItemClickListener.onItemLongClick(v);
        }
        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView simplifiedLogo;
        TextView textViewInfo;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setTag(count++);
            simplifiedLogo=(ImageView)itemView.findViewById(R.id.imageview);
            textViewInfo=(TextView)itemView.findViewById(R.id.textIntroduction);

        }
    }

}
