package com.example.panyunyi.growingup.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class QuickScrollView extends ScrollView {
    
        public QuickScrollView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }
    
        public QuickScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    
        public QuickScrollView(Context context) {
            super(context);
        }
    
        /**
         * 滑动事件
         */
        @Override
        public void fling(int velocityY) {
            super.fling(velocityY*2);
        }
    }