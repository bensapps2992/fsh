package com.example.android.screens.mainmenu;

import com.example.android.Managers.Background;
import com.example.android.Managers.FishManager;
import com.example.android.Models.Title;
import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;
import com.example.android.screens.loading.LoadingRenderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class MenuRenderer {
	
	private static float angle;
	private static float vangle;
	private static int titleRot = 0;
	private static float VDECAY = 1.123503479f;//calculated to get a 90 degree rotation
	private  static float[] mProjectionMatrix = new float[16];
	private  static float[] mViewMatrix = new float[16];
	private  static float[] mMVPMatrix = new float[16];
	
	private static Background bg;
	private static Title mTitle;

    private static final int objectsToLoad = 2;//no objects to load
    private static int currentObject = 0;
	
	public static final int OPTIONS = 1;
	public static final int DESIGNER = 2;
	public static final int MORE_APPS = 0;
	public static final int PLAY_GAME = 3;
	
	
	private static Context mContext;
	
	public static void setTitleRot(int no)
	{
		titleRot = no;
	}
	
	public static int getTitleRot()
	{
		return titleRot;
	}
	
	public static void setvAngle(float mAngle)
	{
		vangle = mAngle;
	}
	public static float getvAngle()
	{
		return vangle;
	}
	
	public static void draw(Context context)
	{
		if(MainActivity.getmGLView().loadingCounter==-1)
		{
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			mProjectionMatrix = MyGLRenderer.getProjMat();
			mViewMatrix = MyGLRenderer.getViewMat();
			Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
			mContext = context;
			angle = 0;//set the initial angle such that the play is presented first
			vangle = 0;
            if(currentObject<objectsToLoad)
            {
                if(currentObject==0)
                {
                    bg = new Background(mContext);
                    currentObject = 1;

                }
                else if(currentObject==1)
                {
                    mTitle = new Title(mContext);
                    currentObject = 2;
                }
                LoadingRenderer.draw((float)(currentObject)/(float)(objectsToLoad));
            }
			//load all the resources and get ready to do the draw loop
		    if(currentObject==objectsToLoad)
            {
                MainActivity.getmGLView().loadingCounter = 0;
            }
		}
		else if(MainActivity.getmGLView().loadingCounter==0)
		{
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		mProjectionMatrix = MyGLRenderer.getProjMat();
		mViewMatrix = MyGLRenderer.getViewMat();
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		bg.draw(mMVPMatrix);
		if(vangle>0.1 || vangle < -0.1){
			angle = angle + vangle;
			vangle = vangle/VDECAY;
		}
		else vangle = 0;
		Matrix.translateM(mMVPMatrix, 0, 0, -0.7f, -1f);
		Matrix.rotateM(mMVPMatrix, 0, angle, 0, 1, 0);
		Matrix.scaleM(mMVPMatrix, 0, 0.8f, 0.8f, 0.8f);
		mTitle.draw(mMVPMatrix);
	}
	}
}
