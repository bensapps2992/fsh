package com.example.android.screens.loading;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.android.Models.Rect;
import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;

public class LoadingRenderer {
	
	private static Context mContext;
	
	private  static float[] mProjectionMatrix = new float[16];
	private  static float[] mViewMatrix = new float[16];
	private  static float[] mMVPMatrix = new float[16];
	
	public static int screenToLoad;
	
	public static final int MAINMENU = MainActivity.getmGLView().mRenderer.MAINMENU;
	public static final int PLAYGAME = MainActivity.getmGLView().mRenderer.PLAYGAME;
	public static final int OPTIONS = MainActivity.getmGLView().mRenderer.OPTIONS;
	public static final int MOREAPPS = MainActivity.getmGLView().mRenderer.MOREAPPS;
	public static final int DESIGNER = MainActivity.getmGLView().mRenderer.DESIGNER;
	
	public static Rect loader;
	
	public static void draw(double percentage)
	{
		//use percentage to draw a rectangle of the right size on the screen
		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
		mProjectionMatrix = MyGLRenderer.getProjMat();
		mViewMatrix = MyGLRenderer.getViewMat();
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		float[] scratch = new float[16];
        Matrix.scaleM(scratch, 0, mMVPMatrix, 0, (float)(percentage), 0.2f, 1);
        float color[] = { 0, 0, 0, 1.0f };
		loader.draw(scratch,color,MainActivity.getmGLView().mRenderer.mWidth,MainActivity.getmGLView().mRenderer.mHeight);
	}
}
