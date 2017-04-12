package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.local.KnowledgeNewsList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by panyunyi on 2017/4/7.
 * MailBox cufer@foxmail.com
 */

public class KnowledgeNewsAdapter extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener{
    private Context mContext;
    private List<KnowledgeNewsList> datas;//数据

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
    public KnowledgeNewsAdapter(Context context,List<KnowledgeNewsList> datas) {
        mContext=context;
        this.datas=datas;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //根据item类别加载不同ViewHolder

            View view = LayoutInflater.from(mContext
            ).inflate(R.layout.main_activity_knowledge_list, parent,
                    false);//这个布局是两个textView
            MyViewHolder holder = new MyViewHolder(view);

            //给布局设置点击和长点击监听
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            return holder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).titleView.setText(datas.get(position).getKnowledgeNewsTitle());
            ((MyViewHolder) holder).contentView.setText(datas.get(position).getKnowledgeNewsContent());
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
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleView;
        private TextView contentView;

        public MyViewHolder(View view)
        {
            super(view);
            contentView=(TextView) view.findViewById(R.id.knowledge_news_list_text_content);
            titleView=(TextView)view.findViewById(R.id.knowledge_news_list_text_title);
        }
    }

}
    //自定义ViewHolder，用于加载图片


