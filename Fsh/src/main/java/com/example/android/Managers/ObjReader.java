package com.example.android.Managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class ObjReader {
	// a static class to read in obj files outputted from http://en.jeffprod.com/obj2opengl.php
	
	public static int GOLDFISH = 0;
	public static int VERTICES = 0;
	public static int UVS = 1;
	public static int NORMALS = 2;

	/*Description: A function to read a model file
	 * Input: - int file: type of model stored in the file
	 * 		  - int array: type of data to return
	 * 		  - Context mContext: the current context
	 * Output:- float[] returnArray: coordinate data to be returned	 * 
	 */
	public static float[] readFile(int file, int filetype, int array, Context mContext){
		float[] returnArray;
		String test = readFromFile(file, mContext);//this contains all the text in the file
		//split it into 3: vert, uv, norml
		String delims = "[{}]+"; // use + to treat consecutive delims as one;
        // omit to treat consecutive delims separately
		String[] tokens = test.split(delims);
		String vert1 = tokens[1];
		String UV = tokens[3];
		String norm = tokens[5];
		delims = "[,]+";
		String[] vert2 = vert1.split(delims);//array of floats in string form
		String[] UV2 = UV.split(delims);
		String[] norm2 = norm.split(delims);
		float[] fVert = new float[vert2.length];//to put into a float array
		float[] fUV = new float[UV2.length];
		float[] fNorm = new float[norm2.length];
		for(int iii=0;iii<vert2.length;iii++){//put them in
			fVert[iii]=Float.parseFloat(vert2[iii]);
			
		}
		for(int iii=0;iii<UV2.length;iii++){
		fUV[iii]=Float.parseFloat(UV2[iii]);
		}
		for(int iii=0;iii<norm2.length;iii++){
		fNorm[iii]=Float.parseFloat(norm2[iii]);
		}
		// we will store the file in the res/raw folder
		if(filetype == ObjReader.GOLDFISH){
			if(array == ObjReader.VERTICES){
				returnArray = fVert;
				return returnArray;
			}
			else if(array == ObjReader.UVS){
				returnArray = fUV;
				return returnArray;
			}
			else if(array == ObjReader.NORMALS){
				returnArray = fNorm;
				return returnArray;
			}
			else{
				return null;
			}
		}
		else return null;
		
	}
	/*
	 * A function to read a string from a stored file
	 */
	private static String readFromFile(int id, Context mContext) {

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

}
