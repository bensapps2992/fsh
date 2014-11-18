package com.example.android.Models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.example.android.Managers.ObjReader;
import com.example.android.Managers.riGraphicTools;
import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;
import com.example.android.opengl.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;

public class Goldfish {
	
	private float vertices[];
	private float uvs[];
	private float normals[];
	private FloatBuffer vertexBuffer;
	private FloatBuffer uvBuffer;
	private FloatBuffer normalBuffer;
	private Context mContext;
	private float[] mLightPos = new float[4];
	
	public Goldfish(Context c){
		mContext = c;
		setup(c);
	}
	
	private float[] getVertices(){
		float[] vertices;
		vertices = ObjReader.readFile(R.raw.goldfish,ObjReader.GOLDFISH,ObjReader.VERTICES, mContext);
		return vertices;
	}
	private float[] getUVS(){
		float[] uvs;
		uvs = ObjReader.readFile(R.raw.goldfish,ObjReader.GOLDFISH, ObjReader.UVS, mContext);
		return uvs;
	}
	private float[] getNormals(){
		float[] normals;
		normals = ObjReader.readFile(R.raw.goldfish,ObjReader.GOLDFISH, ObjReader.NORMALS, mContext);
		return normals;
	}
	
	private void setup(Context c){
		vertices = getVertices();
		uvs = getUVS();
		normals = getNormals();
		//image needs to be inverted to wrap correctly
				for(int iii=0;iii<uvs.length/2;iii++){
					uvs[2*iii+1]=1-uvs[2*iii+1];
				}
				
				ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
				bb.order(ByteOrder.nativeOrder());
				vertexBuffer = bb.asFloatBuffer();
				vertexBuffer.put(vertices);
				vertexBuffer.position(0);
				
				// The texture buffer
				ByteBuffer ubb = ByteBuffer.allocateDirect(uvs.length * 4);
				ubb.order(ByteOrder.nativeOrder());
				uvBuffer = ubb.asFloatBuffer();
				uvBuffer.put(uvs);
				uvBuffer.position(0);
				
				//The normal buffer
				ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);
				nbb.order(ByteOrder.nativeOrder());
				normalBuffer = nbb.asFloatBuffer();
				normalBuffer.put(normals);
				normalBuffer.position(0);
				
				// Generate Textures, if more needed, alter these numbers.
				int[] texturenames = new int[1];
				GLES20.glGenTextures(1, texturenames, 0);
							
				// Retrieve our image from resources.
				int id = mContext.getResources().getIdentifier("drawable/fishuvs", null, mContext.getPackageName());
							
				// Temporary create a bitmap
				Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
				//bmp = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
				//bmp.eraseColor(Color.BLACK);
				
							
				// Bind texture to texturename
				GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
				GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[0]);
							
				// Set filtering
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
				GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
					        
				// Load the bitmap into the bound texture.
				GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
				bmp.recycle();
	}
	
	public void draw(float[] m, float angle){//m is model matrix
		float[] mViewMatrix = MyGLRenderer.getViewMat();//the view matrix
		float[] mProjectionMatrix = MyGLRenderer.getProjMat();//the projection matrix
		float[] mMVMatrix = new float[16];
		float[] mMVPMatrix = new float[16];
		Matrix.multiplyMM(mMVMatrix, 0, mViewMatrix, 0, m, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVMatrix, 0);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GL10.GL_LEQUAL);
        int cp = riGraphicTools.sp_Image_Light;
		GLES20.glUseProgram(cp);
		// get handle to vertex shader's vPosition member
        int mPositionHandle = GLES20.glGetAttribLocation(cp, "a_Position");
        int mNormalHandle = GLES20.glGetAttribLocation(cp, "a_Normal");
        int mTexCoordLoc = GLES20.glGetAttribLocation(cp, "a_texCoord" );
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mNormalHandle);
        GLES20.glEnableVertexAttribArray ( mTexCoordLoc );
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, 3,
                GLES20.GL_FLOAT, false,
                12, vertexBuffer);
        GLES20.glVertexAttribPointer(mNormalHandle, 3,
                GLES20.GL_FLOAT, false,
                0, normalBuffer);
        GLES20.glVertexAttribPointer ( mTexCoordLoc, 2, GLES20.GL_FLOAT,
                false, 
                0, uvBuffer);

        // get handle to shape's transformation matrix
        int uMVHandle = GLES20.glGetUniformLocation(cp, "uMVMatrix");
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(cp, "uMVPMatrix");
        int mtMatrixHandle = GLES20.glGetUniformLocation(cp, "tMatrix");
        int mAngleHandle = GLES20.glGetUniformLocation(cp, "mAngle");
        int uLightPosHandle = GLES20.glGetUniformLocation(cp, "u_LightPos");
        int mSamplerLoc = GLES20.glGetUniformLocation (cp, "s_texture" );

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(uMVHandle, 1, false, mMVMatrix, 0);  
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        float[] test = new float[16];
        Matrix.setIdentityM(test, 0);
        Matrix.rotateM(test, 0, 90, 0, 1, 0);
        GLES20.glUniformMatrix4fv(mtMatrixHandle, 1, false, test,0);
        GLES20.glUniform1f(mAngleHandle, (float)(Math.toRadians(angle)));
        mViewMatrix = MyGLRenderer.getViewMat();
        Matrix.multiplyMV(mLightPos,0, mViewMatrix, 0, new float[]{0,1,1,1}, 0);
        GLES20.glUniform3f(uLightPosHandle, mLightPos[0], mLightPos[1], mLightPos[2]);
        GLES20.glUniform1i ( mSamplerLoc, 2);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length/3);
        
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mNormalHandle);
        GLES20.glDisableVertexAttribArray(mTexCoordLoc);
        
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
	}

}
