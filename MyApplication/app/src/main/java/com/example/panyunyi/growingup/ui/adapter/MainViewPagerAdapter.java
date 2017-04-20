package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.panyunyi.growingup.R;

import java.io.InputStream;
import java.util.ArrayList;

import static java.lang.Integer.MAX_VALUE;

/**
 * Created by panyunyi on 2017/4/13.
 * MailBox cufer@foxmail.com
 */

public class MainViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ImageView>imageViews=new ArrayList<>();

    public MainViewPagerAdapter(Context mcontext,ArrayList<ImageView>mimageViews) {
        context=mcontext;
        imageViews=mimageViews;
        Log.i(">>imageviews",""+imageViews.size());
    }

    @Override
    public int getCount() {
        return MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        position=position%4;
        container.addView(imageViews.get(position));
        return (ImageView)imageViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
