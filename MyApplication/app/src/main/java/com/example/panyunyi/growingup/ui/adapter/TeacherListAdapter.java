package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.local.TeacherList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by panyunyi on 2017/4/7.
 * MailBox cufer@foxmail.com
 */

public class TeacherListAdapter extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener{
    private Context mContext;
    private List<TeacherList> datas;//数据

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public TeacherListAdapter(Context context,List<TeacherList> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public int getItemViewType(int position) {
        //判断item类别，是图还是显示页数（图片有URL）
        if (!TextUtils.isEmpty(datas.get(position).getUrl())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //根据item类别加载不同ViewHolder
        if(viewType==0){
            View view = LayoutInflater.from(mContext
            ).inflate(R.layout.main_activity_teacher_list_item, parent,
                    false);//这个布局就是一个imageview用来显示图片
            MyViewHolder holder = new MyViewHolder(view);

            //给布局设置点击和长点击监听
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            return holder;
        }else{
            MyViewHolder2 holder2=new MyViewHolder2(LayoutInflater.from(
                    mContext).inflate(R.layout.page_item, parent,
                    false));//这个布局就是一个textview用来显示页数
            return holder2;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if(holder instanceof MyViewHolder){
            Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder) holder).iv);//加载网络图片
        }else if(holder instanceof MyViewHolder2){
            ((MyViewHolder2) holder).tv.setText(datas.get(position).getPage()+"页");
        }

    }

    @Override
    public int getItemCount()
    {
        return datas.size();//获取数据的个数
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

    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageButton iv;

        public MyViewHolder(View view)
        {
            super(view);
            iv = (ImageButton) view.findViewById(R.id.iv);
        }
    }
    //自定义ViewHolder，用于显示页数
    class MyViewHolder2 extends RecyclerView.ViewHolder
    {
        private TextView tv;

        public MyViewHolder2(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
        }
    }

}
