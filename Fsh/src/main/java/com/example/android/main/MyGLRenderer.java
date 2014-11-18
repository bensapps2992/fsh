package com.example.android.main;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.android.Managers.Background;
import com.example.android.Managers.FishManager;
import com.example.android.Managers.riGraphicTools;
import com.example.android.Models.Rect;
import com.example.android.Models.Title;
import com.example.android.screens.designer.DesignerRenderer;
import com.example.android.screens.loading.LoadingRenderer;
import com.example.android.screens.mainmenu.MenuRenderer;
import com.example.android.screens.moreapps.MoreAppsRenderer;
import com.example.android.screens.options.OptionsRenderer;
import com.example.android.screens.playgame.PlayGameRenderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {

	// Screen Dimensions
	private static float ratio;	
	public static float mWidth;
	public static float mHeight;
	// Misc
	Context mContext;
	
	private FishManager mFishManager;
	
	//Game loop stuff
	private int currentLoop;
	public static final int MAINMENU = 0;
	public static final int PLAYGAME = 1;
	public static final int OPTIONS = 2;
	public static final int MOREAPPS = 3;
	public static final int DESIGNER = 4;
	
	public void setCurrentLoop(int loop)
	{
		MainActivity.getmGLView().loadingCounter = -1;//set the counter to -1 ... we need to load the resources
		currentLoop = loop;
	}
	
	public int getCurrentLoop()
	{
		return currentLoop;
	}
	
	
	public static float getRatio()
	{
		return ratio;
	}
	
    public static float[] getProjMat(){
    	float[] ProjectionMatrix = new float[16];
    	Matrix.frustumM(ProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    	return ProjectionMatrix;
    }
    
    public static float[] getViewMat(){
    	float[] ViewMatrix = new float[16];
    	Matrix.setLookAtM(ViewMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    	return ViewMatrix;
    }
	
	public MyGLRenderer(Context c)
	{
		mContext = c;
	}
	
	public void onPause()
	{
	}
	
	public void onResume()
	{
	}
	
	@Override
	public void onDrawFrame(GL10 unused) {
		switch(currentLoop){
		case PLAYGAME:
			PlayGameRenderer.draw(mContext);
			//bg.draw(mMVPMatrix);
			//mFishManager.draw();
			break;
		case MAINMENU:
			MenuRenderer.draw(mContext);
			break;
		case OPTIONS:
			OptionsRenderer.draw();
			break;
		case MOREAPPS:
			MoreAppsRenderer.draw();
			break;
		case DESIGNER:
			DesignerRenderer.draw();
			break;
			default:
		}
	}
	

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {	
				riGraphicTools.doShaders(mContext);
				GLES20.glClearColor(1.0f, 0.0f, 1.0f, 1);
				GLES20.glViewport(0, 0, width, height);
				MyGLRenderer.mWidth=width;
		        MyGLRenderer.mHeight=height;
		        MyGLRenderer.ratio = (float) width / height;
				GLES20.glEnable(GL10.GL_BLEND);
			    GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
			    //initiate the menu renderer
			    LoadingRenderer.loader = new Rect();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	    
	}	
	
	 /**
	    * Utility method for debugging OpenGL calls. Provide the name of the call
	    * just after making it:
	    *
	    * <pre>
	    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
	    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
	    *
	    * If the operation is not successful, the check throws an error.
	    *
	    * @param glOperation - Name of the OpenGL call to check.
	    */
	    public static void checkGlError(String glOperation) {
	        int error;
            //noinspection LoopStatementThatDoesntLoop
            while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
	            Log.e("test", glOperation + ": glError " + error);
	            throw new RuntimeException(glOperation + ": glError " + error);
	        }
	    }
}
