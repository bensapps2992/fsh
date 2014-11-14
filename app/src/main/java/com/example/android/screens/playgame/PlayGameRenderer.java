package com.example.android.screens.playgame;

import com.example.android.Managers.Background;
import com.example.android.Managers.FishManager;
import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class PlayGameRenderer {
	
	private static Context mContext;
	
	private  static float[] mProjectionMatrix = new float[16];
	private  static float[] mViewMatrix = new float[16];
	private  static float[] mMVPMatrix = new float[16];
	
	//Objects
	static Background bg;
	static FishManager fish;

	public static void draw(Context con)
	{
		if(MainActivity.getmGLView().loadingCounter==-1)
		{
			mContext = con;
			bg = new Background(mContext);
			fish = new FishManager(mContext);
			MainActivity.getmGLView().loadingCounter = 0;
		}
		else if(MainActivity.getmGLView().loadingCounter==0)
		{
		GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
		mProjectionMatrix = MyGLRenderer.getProjMat();
		mViewMatrix = MyGLRenderer.getViewMat();
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		bg.draw(mMVPMatrix);
		fish.draw();
		}
	}
}
