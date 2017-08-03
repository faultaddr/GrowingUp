package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.local.KnowledgeNewsList;
import com.example.panyunyi.growingup.entity.local.TeacherList;
import com.example.panyunyi.growingup.entity.remote.GTeacherEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by panyunyi on 2017/4/7.
 * MailBox cufer@foxmail.com
 */

public class TeacherListAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<GTeacherEntity> datas;//数据

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,int pos);
        void onItemLongClick(View view,int pos);
    }
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public TeacherListAdapter(Context context,List<GTeacherEntity> datas) {
        mContext=context;
        this.datas=datas;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View view = LayoutInflater.from(mContext
            ).inflate(R.layout.main_activity_teacher_list_item, parent,
                    false);//这个布局就是一个imageview用来显示图片
            final MyViewHolder holder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemLongClick(v, holder.getAdapterPosition());
                }
                return false;
            }
        });

            return holder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据与item视图进行绑定，如果是MyViewHolder就加载网络图片，如果是MyViewHolder2就显示页数
        if(holder instanceof MyViewHolder){
            //Picasso.with(mContext).load(datas.get(position).getUrl()).into(((MyViewHolder) holder).iv);//加载网络图片
            ((MyViewHolder) holder).teacherId.setText(datas.get(position).getTeacherId());
            ((MyViewHolder)holder).teacherName.setText(datas.get(position).getTeacherName());
            ((MyViewHolder)holder).teacherMajor.setText(datas.get(position).getTeacherMajor());

        }

    }

    @Override
    public int getItemCount()
    {
        return datas.size();//获取数据的个数
    }



    //自定义ViewHolder，用于加载图片
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView teacherId;
        private TextView teacherName;
        private TextView teacherMajor;

        public MyViewHolder(View view)
        {
            super(view);
            teacherId=(TextView)view.findViewById(R.id.teacher_id);
            teacherName=(TextView)view.findViewById(R.id.teacher_name);
            teacherMajor=(TextView)view.findViewById(R.id.teacher_major);
        }
    }

}
