package com.example.panyunyi.growingup;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.panyunyi.growingup.ui.custom.MyGLRenderer;
import com.example.panyunyi.growingup.ui.custom.PicWordView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivty extends AppCompatActivity {

    private MyGLSurfaceView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLView = new MyGLSurfaceView(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        mGLView.setRenderer(new MyGLRenderer());
        setContentView(mGLView);

    }
    class MyGLSurfaceView extends GLSurfaceView {

        private final MyGLRenderer mRenderer;

        public MyGLSurfaceView(Context context){
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            mRenderer = new MyGLRenderer();

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(mRenderer);
        }
    }
}
