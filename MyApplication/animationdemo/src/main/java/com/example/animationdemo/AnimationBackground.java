package com.example.animationdemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by panyu on 2017/6/16.
 */

public class AnimationBackground extends GLSurfaceView {
    public AnimationBackground(Context context) {
        super(context);
    }

    public AnimationBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
