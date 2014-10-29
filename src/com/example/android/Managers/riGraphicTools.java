package com.example.android.Managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.android.opengl.R;
import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class riGraphicTools {

	// Program variables
	public static int sp_ImageText;
	public static int sp_Image;
	public static int sp_Image_Light;
	private static Context mContext;
	

	public static int loadShader(int type, String shaderCode){


	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = GLES20.glCreateShader(type);

	    // add the source code to the shader and compile it
	    GLES20.glShaderSource(shader, shaderCode);
	    GLES20.glCompileShader(shader);
	    
	    // return the shader
	    return shader;
	}
	
	private static String readFromFile(int id) {

	    String ret = "";

	    try {
	        InputStream inputStream = mContext.getResources().openRawResource(id);

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}
	
	public static void doShaders(Context context){
		
		mContext=context;
		/* SHADER Solid
		 * 
		 * This shader is for rendering a colored primitive.
		 * 
		 */
		String vs_Image_Text=readFromFile(R.raw.vs_image_text);
		String fs_Image_Text=readFromFile(R.raw.fs_image_text);
		
		String vs_Image=readFromFile(R.raw.vs_image);
		String fs_Image=readFromFile(R.raw.fs_image);
		
		String vs_Image_Light=readFromFile(R.raw.vs_image_light);
		String fs_Image_Light=readFromFile(R.raw.fs_image_light);
		
	    int vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER,vs_Image_Text);
	    int fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER,fs_Image_Text);

	    riGraphicTools.sp_ImageText = GLES20.glCreateProgram();             // create empty OpenGL ES Program
	    GLES20.glAttachShader(riGraphicTools.sp_ImageText, vertexShader);   // add the vertex shader to program
	    GLES20.glAttachShader(riGraphicTools.sp_ImageText, fragmentShader); // add the fragment shader to program
	    GLES20.glLinkProgram(riGraphicTools.sp_ImageText);                  // creates OpenGL ES program executables

	    vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER,vs_Image);
	    fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER,fs_Image);

	    riGraphicTools.sp_Image = GLES20.glCreateProgram();             // create empty OpenGL ES Program
	    GLES20.glAttachShader(riGraphicTools.sp_Image, vertexShader);   // add the vertex shader to program
	    GLES20.glAttachShader(riGraphicTools.sp_Image, fragmentShader); // add the fragment shader to program
	    GLES20.glLinkProgram(riGraphicTools.sp_Image);                  // creates OpenGL ES program executables

	    vertexShader = riGraphicTools.loadShader(GLES20.GL_VERTEX_SHADER,vs_Image_Light);
	    fragmentShader = riGraphicTools.loadShader(GLES20.GL_FRAGMENT_SHADER,fs_Image_Light);

	    riGraphicTools.sp_Image_Light = GLES20.glCreateProgram();             // create empty OpenGL ES Program
	    GLES20.glAttachShader(riGraphicTools.sp_Image_Light, vertexShader);   // add the vertex shader to program
	    GLES20.glAttachShader(riGraphicTools.sp_Image_Light, fragmentShader); // add the fragment shader to program
	    GLES20.glLinkProgram(riGraphicTools.sp_Image_Light);                  // creates OpenGL ES program executables

	    
	    // Set our shader programm
		GLES20.glUseProgram(riGraphicTools.sp_Image);
	}
}
