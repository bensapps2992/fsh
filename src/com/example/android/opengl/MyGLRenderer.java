package com.example.android.opengl;


import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.android.opengl.Managers.Background;
import com.example.android.opengl.Managers.FishManager;
import com.example.android.opengl.Managers.TextManager;
import com.example.android.opengl.Managers.riGraphicTools;
import com.example.android.opengl.Models.Title;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {

	// Our matrices
	private  float[] mProjectionMatrix = new float[16];
	private  float[] mViewMatrix = new float[16];
	private  float[] mMVPMatrix = new float[16];
	private static float ratio;	
	private static float mWidth;
	private static float mHeight;
	// Misc
	Context mContext;
	
	private TextManager mTextManager;
	private FishManager mFishManager;
	private Title mTitle;
	private Background bg;
	
	private int currentLoop;
	public int MAINMENU = 0;
	public int PLAYGAME = 1;
	
	private float angle = 0;
	
	public void setCurrentLoop(int loop)
	{
		currentLoop = loop;
	}
	
	public void setAngle(float mAngle)
	{
		angle = mAngle;
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
		//z axis comes out of screen +ve
		if(currentLoop == PLAYGAME){
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
		mProjectionMatrix = getProjMat();
		mViewMatrix = getViewMat();
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
		bg.draw(mMVPMatrix);
		mFishManager.draw();
		}
		
		else if (currentLoop == MAINMENU){
			
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			mProjectionMatrix = getProjMat();
			mViewMatrix = getViewMat();
			Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
			bg.draw(mMVPMatrix);
			Matrix.translateM(mMVPMatrix, 0, 0, -0.5f, -1f);
			Matrix.rotateM(mMVPMatrix, 0, angle, 0, 1, 0);
			mTitle.draw(mMVPMatrix);
		}
		else
		{
		
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
				Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);	
				GLES20.glEnable(GL10.GL_BLEND);
			    GLES20.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
			    bg = new Background(mContext);//background on layer 1
			    mFishManager = new FishManager(mContext); //text on layer 0, fish on layer 2
			    mTitle = new Title(mContext);//title on layer 2
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	    
	}	
	
	public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
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
	        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
	            Log.e("test", glOperation + ": glError " + error);
	            throw new RuntimeException(glOperation + ": glError " + error);
	        }
	    }
}
