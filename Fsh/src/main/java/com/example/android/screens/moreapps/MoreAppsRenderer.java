package com.example.android.screens.moreapps;

import com.example.android.main.MyGLRenderer;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class MoreAppsRenderer {
	
	private  static float[] mProjectionMatrix = new float[16];
	private  static float[] mViewMatrix = new float[16];
	private  static float[] mMVPMatrix = new float[16];
	
	public static void draw()
	{
		GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1);

		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
		mProjectionMatrix = MyGLRenderer.getProjMat();
		mViewMatrix = MyGLRenderer.getViewMat();
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
	}

}
