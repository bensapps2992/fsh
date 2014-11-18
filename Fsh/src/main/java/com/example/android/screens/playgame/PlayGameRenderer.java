package com.example.android.screens.playgame;

import com.example.android.Managers.Background;
import com.example.android.Managers.FishManager;
import com.example.android.Models.Title;
import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;
import com.example.android.screens.loading.LoadingRenderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class PlayGameRenderer {
	
	private static Context mContext;
	
	private  static float[] mProjectionMatrix = new float[16];
	private  static float[] mViewMatrix = new float[16];
	private  static float[] mMVPMatrix = new float[16];
	
	//Objects
	private static Background bg;
	private static FishManager fish;

    private static final int objectsToLoad = 2;//no objects to load
    private static int currentObject = 0;

	public static void draw(Context con)
	{
		if(MainActivity.getmGLView().loadingCounter==-1)
		{
			mContext = con;

            if(currentObject<objectsToLoad)
            {
                if(currentObject==0)
                {
                    bg = new Background(mContext);
                    currentObject = 1;

                }
                else if(currentObject==1)
                {
                    fish = new FishManager(mContext);
                    currentObject = 2;
                }
                LoadingRenderer.draw((float) (currentObject) / (float) (objectsToLoad));
            }
            //load all the resources and get ready to do the draw loop
            if(currentObject==objectsToLoad)
            {
                MainActivity.getmGLView().loadingCounter = 0;
            }
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
