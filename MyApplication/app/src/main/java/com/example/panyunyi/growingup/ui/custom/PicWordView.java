package com.example.panyunyi.growingup.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.panyunyi.growingup.R;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: document your custom view class.
 */
public class PicWordView extends View {
    private SpannableStringBuilder mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private CharSequence mData = null;

    private LinearLayout mContentView = null;
    ArrayList<Bitmap> bitmapList = new ArrayList<>();
    ArrayList<Pos>posList=new ArrayList<>();
    public PicWordView(Context context) {
        super(context);
        init(null, 0);
    }

    public PicWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PicWordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PicWordView, defStyle, 0);

        mExampleString = SpannableStringBuilder.valueOf(a.getString(
                R.styleable.PicWordView_exampleString));
        mExampleColor = a.getColor(
                R.styleable.PicWordView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.PicWordView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.PicWordView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.PicWordView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString.toString());

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString.toString(),
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);
        canvas.drawBitmap(bitmapList.get(0),200,300,mTextPaint);
        // Draw the example drawable on top of the text.

    }

    public void setText(CharSequence sequence) {
        ArrayList<URI> urlImage = new ArrayList<>();
        SpannableStringBuilder spanBuilder = null;

        try {
            if (TextUtils.isEmpty(sequence)) return;
            else if (sequence.equals(mData)) return;
            mData = sequence;
            Pattern pattern = Pattern.compile("<img(\\d+)/>");
            spanBuilder = new SpannableStringBuilder(mData);
            Matcher matcher = pattern.matcher(mData);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();

                //mData=mData.toString().replaceAll("<img(.+)/>","");
                Log.i(">>mData", matcher.group(1));
                if (matcher.group(1).matches("\\d+")) {
                    Drawable drawable = getResources().getDrawable(Integer.parseInt(matcher.group(1)));
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    spanBuilder.setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spanBuilder.append("\n");
                } else {
                    urlImage.add(URI.create(matcher.group()));
                }
            }

/*
            for(URI img :urlImage){

                //这里做自己的异步加载操作；
                //注意要保证线程程同步
            }
            */

        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            mExampleString=spanBuilder;
            imageLoad();
        }

    }

    private void imageLoad() {
        ImageSpan[] s = mExampleString.getSpans(0, 16, ImageSpan.class);
        for (int i = 0; i < s.length; i++) {
            Drawable drawables = s[i].getDrawable();
            bitmapList.add(drawable2Bitmap(drawables));
        }

    }

    public static Bitmap drawable2Bitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap() ;
        }else if(drawable instanceof NinePatchDrawable){
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }else{
            return null ;
        }
    }
    public void setPos(int x,int y){
        Pos p=new Pos();
        p.posX=x;
        p.posY=y;
        posList.add(p);
    }
    private class Pos {
        int posX;
        int posY;
    }
    private void addText(String s){



    }
}
