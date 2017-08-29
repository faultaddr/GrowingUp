package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.remote.GTimeEntity;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by panyu on 2017/8/4.
 */

public class TimeSpinnerAdapter implements SpinnerAdapter {
    private ArrayList<GTimeEntity> timeList = new ArrayList<>();
    private Context mContext;

    public TimeSpinnerAdapter(Context context, ArrayList<GTimeEntity> list) {
        timeList = list;
        mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.time_spinner_item,null);
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.time_spinner_item);
            if (timeList.get(position).getTimeStatus().equals("2") || timeList.get(position).getTimeStatus().equals("1")) {

            } else {
                textView.setText(timeList.get(position).getTimeDetail());
            }
        }
        convertView.setTag(timeList.get(position).getId());
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.time_spinner_item,null);
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.time_spinner_item);
            if (timeList.get(position).getTimeStatus().equals("-1") || timeList.get(position).getTimeStatus().equals("1")) {

            } else {
                textView.setText(timeList.get(position).getTimeDetail());
            }
        }
        convertView.setTag(timeList.get(position).getId());
        return convertView;

    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
