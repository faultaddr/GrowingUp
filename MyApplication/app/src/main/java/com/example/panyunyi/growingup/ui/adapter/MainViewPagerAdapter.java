package com.example.panyunyi.growingup.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    private int []id=new int[]{R.drawable.main_activity_list_1, R.drawable.main_activity_list_2, R.drawable.main_activity_list_3, R.drawable.main_activity_list_4};

    public MainViewPagerAdapter(Context mcontext) {
        context=mcontext;


    }

    @Override
    public int getCount() {
        return MAX_VALUE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        position=position%4;
        InputStream is = context.getResources().openRawResource(id[position]);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10; //width，hight设为原来的十分之一
        Bitmap btp =BitmapFactory.decodeStream(is,null,options);

        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(btp);
        container.addView(imageView);
        return imageView;
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
