package com.example.panyunyi.growingup.ui.custom;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;

import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by panyu on 2017/5/26.
 */

public class MyRoundVIew extends Drawable{
    /*
    * 边框颜色
    * */
    private static final int BORDER_COLOR=0x00000000;
    /*
    * 边框宽度
    *
    * */

    private static final int BORDER_WIDTH=0;

    private final Paint mPaint;
    private final Paint  mBorderPaint;

    private final RectF mRectF;
    private final RectF mBorderRectF;


    private int mBitmapWidth;
    private int mBitmapHeight;

    private final int mBorderWidth;
    private final boolean mIsCircle;

    public MyRoundVIew(Bitmap bitmap){

        this(bitmap,BORDER_COLOR,BORDER_WIDTH,false);
    }
    public MyRoundVIew(Bitmap bitmap,int borderColor,int borderWidth,boolean isCircle){

        if(bitmap==null){
            throw new IllegalArgumentException("bitmap cannot be null");

        }
        //bitmap
        mBitmapHeight =bitmap.getHeight();
        mBitmapWidth=bitmap.getWidth();
        mIsCircle=isCircle;
        mBorderWidth=borderWidth;
        mRectF=new RectF();
        mBorderRectF=new RectF();
        Bitmap bm=null;
        /*
        * 如果图片需要是圆形时，需要先将其转化成为一个正方形。
        * */
        if(mIsCircle){
            bm=getSquareBitmap(bitmap);

        }


        //初始化paint

        mPaint=new Paint();
        mPaint.setAntiAlias(true);//使用抗锯齿
        mPaint.setDither(true);//使用抖动处理
        BitmapShader shader=new BitmapShader(bm==null?bitmap:bm, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        mBorderPaint=new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStrokeWidth(borderWidth);
    }

    private Bitmap getSquareBitmap(Bitmap bitmap) {
        Bitmap bm;
        if (mBitmapWidth > mBitmapHeight) {
            /*
            *
            * createBitmap

                    added in API level 1
                    Bitmap createBitmap (Bitmap source,
                                    int x,
                                    int y,
                                    int width,
                                    int height)
                    Returns an immutable bitmap from the specified subset of the source bitmap. The new bitmap may be the same object as source, or a copy may have been made. It is initialized with the same density and color space as the original bitmap.

                    Parameters
                    source	Bitmap: The bitmap we are subsetting
                    x	int: The x coordinate of the first pixel in source
                    y	int: The y coordinate of the first pixel in source
                    width	int: The number of pixels in each row
                    height	int: The number of rows
            *
            * */


            bm = Bitmap.createBitmap(bitmap, (mBitmapWidth - mBitmapHeight) / 2, 0, mBitmapHeight, mBitmapHeight);
            mBitmapWidth= mBitmapHeight;

        }else if(mBitmapWidth< mBitmapHeight){
            bm=Bitmap.createBitmap(bitmap,0,(mBitmapHeight -mBitmapWidth)/2,mBitmapWidth, mBitmapWidth);
            mBitmapHeight =mBitmapWidth;

        }else{
            bm=bitmap;

        }
        return bm;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        if(mIsCircle){
            /*
            * void drawCircle (float cx,
                float cy,
                float radius,
                Paint paint)
            Draw the specified circle using the specified paint. If radius is <= 0, then nothing will be drawn. The circle will be filled or framed based on the Style in the paint.

                Parameters
                cx	float: The x-coordinate of the center of the cirle to be drawn
                cy	float: The y-coordinate of the center of the cirle to be drawn
                radius	float: The radius of the cirle to be drawn
                paint	Paint: The paint used to draw the circle
            *
            * */

            canvas.drawCircle(mRectF.centerX(),mRectF.centerY(),mRectF.centerX(),mPaint);
            canvas.drawCircle(mBorderRectF.centerX(),mBorderRectF.centerY(),mBorderRectF.centerX()-mBorderWidth/2.0f,mBorderPaint);

        }else{

            /*
            * void drawOval (float left,
                float top,
                float right,
                float bottom,
                Paint paint)

            Draw the specified oval using the specified paint. The oval will be filled or framed based on the Style in the paint.

                Parameters
                left	float
                top	float
                right	float
                bottom	float
                paint	Paint
            *
            * */
            canvas.drawOval(mRectF,mPaint);
            canvas.drawOval(mBorderRectF,mBorderPaint);

        }
    }
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRectF.set(bounds);
        bounds.inset(mBorderWidth / 2, mBorderWidth / 2);   // FIXME fine tuning, [bounds.inset(mBorderWidth,
        // mBorderWidth);]
        mBorderRectF.set(bounds);
    }
    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        if(mPaint.getAlpha()!=alpha){
            mPaint.setAlpha(alpha);
            invalidateSelf();

        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }

    public void setAntiAlias(boolean aa) {
        mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    @Override
    public void setFilterBitmap(boolean filter) {
        mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    @Override
    public void setDither(boolean dither) {
        mPaint.setDither(dither);
        invalidateSelf();
    }
}
