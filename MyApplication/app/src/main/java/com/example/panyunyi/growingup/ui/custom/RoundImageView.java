package com.example.panyunyi.growingup.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.panyunyi.growingup.R;

/**
 * 圆角ImageView
 *
 * @author venshine
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {

    /**
     * 边框颜色
     */
    private int mBorderColor;

    /**
     * 边框宽度
     */
    private int mBorderWidth;

    /**
     * 是否圆形，默认如果图片宽高不相等即为椭圆
     */
    private boolean mIsCircle = false;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }



    /**
     * 初始化属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        try {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.round_imageview);
            mBorderColor = ta.getColor(R.styleable.round_imageview_border_color, 0x00000000);
            mBorderWidth = (int) ta.getDimension(R.styleable.round_imageview_border_width, 0);
            mIsCircle = ta.getBoolean(R.styleable.round_imageview_circle, false);
            ta.recycle();
            int resId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", -1);
            if (resId != -1) {
                setImageResource(resId, mBorderColor, mBorderWidth, mIsCircle);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取边框颜色
     *
     * @return
     */
    public int getBorderColor() {
        return mBorderColor;
    }

    /**
     * 设置边框颜色，形如'#aarrggbb'
     *
     * @param borderColor
     */
    public void setBorderColor(String borderColor) {
        this.mBorderColor = Color.parseColor(borderColor);
    }

    /**
     * 设置边框颜色，形如{@link android.graphics.Color}
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        this.mBorderColor = borderColor;
    }

    /**
     * 获取边框宽度
     *
     * @return
     */
    public int getBorderWidth() {
        return mBorderWidth;
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        this.mBorderWidth = borderWidth;
    }

    /**
     * 是否设置圆形处理
     *
     * @return
     */
    public boolean isCircle() {
        return mIsCircle;
    }

    /**
     * 设置圆形处理方式，默认按椭圆处理
     *
     * @param isCircle
     */
    public void setCircle(boolean isCircle) {
        this.mIsCircle = isCircle;
    }

    /**
     * 设置图片资源
     *
     * @param resId
     */
    @Override
    public void setImageResource(int resId) {
        setImageResource(resId, mBorderColor, mBorderWidth, mIsCircle);
    }

    /**
     * 设置图片资源，包括边框颜色、边框宽度、是否圆形处理
     *
     * @param resId
     * @param borderColor
     * @param borderWidth
     * @param isCircle
     */
    public void setImageResource(int resId, int borderColor, int borderWidth, boolean isCircle) {
        setImageDrawable(getResources().getDrawable(resId), borderColor, borderWidth, isCircle);
    }

    /**
     * 设置图片Drawable
     *
     * @param drawable
     */
    @Override
    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, mBorderColor, mBorderWidth, mIsCircle);
    }

    /**
     * 设置图片Drawable，包括边框颜色、边框宽度、是否圆形处理
     *
     * @param drawable
     * @param borderColor
     * @param borderWidth
     * @param isCircle
     */
    public void setImageDrawable(Drawable drawable, int borderColor, int borderWidth, boolean isCircle) {
        Bitmap bm = drawableToBitmap(drawable);
        setImageBitmap(bm, borderColor, borderWidth, isCircle);
    }

    /**
     * 设置图片bitmap
     *
     * @param bm
     */
    @Override
    public void setImageBitmap(Bitmap bm) {
        setImageBitmap(bm, mBorderColor, mBorderWidth, mIsCircle);
    }

    /**
     * 设置图片bitmap，包括边框颜色、边框宽度、是否圆形处理
     *最最核心的方法
     * @param bm
     * @param borderColor
     * @param borderWidth
     * @param isCircle
     */
    public void setImageBitmap(Bitmap bm, int borderColor, int borderWidth, boolean isCircle) {
         super.setImageDrawable(new MyRoundVIew(bm, borderColor, borderWidth, isCircle));
    }

    /**
     * drawable 转化 bitmap
     *
     * @param d
     * @return
     */
    private static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }
}