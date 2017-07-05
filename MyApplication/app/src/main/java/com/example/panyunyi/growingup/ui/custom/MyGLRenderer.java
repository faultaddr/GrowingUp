package com.example.panyunyi.growingup.ui.custom;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Triangle triangle;
    private Square square;


    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        unused.glClearColor(0.0f,0.0f,0.0f,0.5f);
        unused.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup. 深度 缓冲
        unused.glClearDepthf(1.0f);// OpenGL docs.
// Enables depth testing. 深度测试
        unused.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
// The type of depth testing to do. 深度测试类型
        unused.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
// Really nice perspective calculations.
        unused.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);

    }
@Override
    public void onDrawFrame(GL10 unused) {
        // Redraw background color
    unused.glClear(GL10.GL_COLOR_BUFFER_BIT | // OpenGL docs.
            GL10.GL_DEPTH_BUFFER_BIT);
}
@Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
    unused.glViewport(0, 0, width, height);// OpenGL docs.
// Select the projection matrix
    unused.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
// Reset the projection matrix
    unused.glLoadIdentity();// OpenGL docs.
// Calculate the aspect ratio of the window
    GLU.gluPerspective(unused, 45.0f,
            (float) width / (float) height,
            0.1f, 100.0f);
// Select the modelview matrix
    unused.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
// Reset the modelview matrix
    unused.glLoadIdentity();// OpenGL docs.
    }
}